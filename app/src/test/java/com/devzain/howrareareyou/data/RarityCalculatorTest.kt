package com.devzain.howrareareyou.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RarityCalculatorTest {

    @Test
    fun `test empty answers list returns baseline default`() {
        val result = RarityCalculator.calculate(emptyList())
        assertEquals(0.0, result.percentile, 0.01)
        assertEquals(1L, result.oneInX)
        assertEquals(RarityCalculator.RarityTier.COMMON, result.tier)
        assertEquals(1.0, result.combinedProbability, 0.00001)
    }

    @Test
    fun `test highly common answers yield low percentile and common tier`() {
        // Mock answers that match the baseline exactly (very common traits)
        val commonAnswers = listOf(
            UserAnswer(questionId = 1, selectedIndex = 0, probability = 0.89),
            UserAnswer(questionId = 2, selectedIndex = 0, probability = 0.79),
            UserAnswer(questionId = 3, selectedIndex = 0, probability = 0.38)
        )
        val result = RarityCalculator.calculate(commonAnswers)
        // Ratio is 1 for each, logScore is 0
        // formula: rawPercentile = 100 / (1 + exp(-0.3 * (0 - 8.0))) = 8.31%
        assertTrue("Common user should have low percentile", result.percentile < 10.0)
        assertEquals(RarityCalculator.RarityTier.COMMON, result.tier)
        assertEquals(1L, result.oneInX) // 1 in 1
    }

    @Test
    fun `test neutral answers are skipped and do not skew results`() {
        val mixedAnswers = listOf(
            UserAnswer(questionId = 1, selectedIndex = 0, probability = 0.01), // rare
            UserAnswer(questionId = 2, selectedIndex = 1, probability = 1.0)  // neutral / don't know
        )
        val rareOnlyAnswer = listOf(
            UserAnswer(questionId = 1, selectedIndex = 0, probability = 0.01)
        )
        
        val resultMixed = RarityCalculator.calculate(mixedAnswers)
        val resultRareOnly = RarityCalculator.calculate(rareOnlyAnswer)

        // The neutral answer should have been skipped completely, making both scores identical
        assertEquals(resultRareOnly.percentile, resultMixed.percentile, 0.01)
        assertEquals(resultRareOnly.rarityScore, resultMixed.rarityScore, 0.01)
        assertEquals(resultRareOnly.oneInX, resultMixed.oneInX)
    }

    @Test
    fun `test extremely rare answers correctly yield mythic tier and high percentile`() {
        // Add enough rare traits to comfortably mathematically cross the 99.0% logic threshold
        val rareAnswers = listOf(
            UserAnswer(questionId = 1, selectedIndex = 0, probability = 0.001), 
            UserAnswer(questionId = 2, selectedIndex = 0, probability = 0.0001),
            UserAnswer(questionId = 3, selectedIndex = 0, probability = 0.005),
            UserAnswer(questionId = 4, selectedIndex = 0, probability = 0.01),
            UserAnswer(questionId = 5, selectedIndex = 0, probability = 0.002)
        )
        val result = RarityCalculator.calculate(rareAnswers)
        
        assertTrue("Rare combination should score > 99%", result.percentile >= 99.0)
        assertEquals(RarityCalculator.RarityTier.MYTHIC, result.tier)
        assertTrue("One in X should be very large", result.oneInX > 100)
    }

    @Test
    fun `test oneInX capping for perfectly unique theoretical scenarios`() {
        val impossibleAnswers = List(20) {
            UserAnswer(questionId = it, selectedIndex = 0, probability = 0.000001)
        }
        val result = RarityCalculator.calculate(impossibleAnswers)
        
        assertEquals(100.0, result.percentile, 0.01)
        // Cannot exceed human population of 8.1 Billion
        assertEquals(8_100_000_000L, result.oneInX)
        assertEquals(RarityCalculator.RarityTier.MYTHIC, result.tier)
    }

    @Test
    fun `test formatting of oneInX for display`() {
        assertEquals("999", RarityCalculator.formatOneInX(999L))
        assertEquals("1,000", RarityCalculator.formatOneInX(1_000L))
        assertEquals("9,999", RarityCalculator.formatOneInX(9_999L))
        
        // Ks
        assertEquals("10.0K", RarityCalculator.formatOneInX(10_000L))
        assertEquals("15.5K", RarityCalculator.formatOneInX(15_500L))
        assertEquals("100K", RarityCalculator.formatOneInX(100_000L))
        assertEquals("999K", RarityCalculator.formatOneInX(999_000L))
        
        // Millions
        assertEquals("1.0 Million", RarityCalculator.formatOneInX(1_000_000L))
        assertEquals("1.5 Million", RarityCalculator.formatOneInX(1_500_000L))
        assertEquals("10 Million", RarityCalculator.formatOneInX(10_000_000L))
        assertEquals("150 Million", RarityCalculator.formatOneInX(150_000_000L))
        
        // Billions
        assertEquals("1.0 Billion", RarityCalculator.formatOneInX(1_000_000_000L))
        assertEquals("8.1 Billion", RarityCalculator.formatOneInX(8_100_000_000L))
        assertEquals("10 Billion", RarityCalculator.formatOneInX(10_000_000_000L))
    }
}
