package com.devzain.howrareareyou.data

import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.roundToInt

/**
 * The rarity math engine.
 *
 * Uses log-based scoring so we get meaningful differentiation
 * between users. The "1 in X" number is derived from the percentile
 * so both numbers tell a consistent story. Previously it was
 * calculated from raw probability multiplication which gave
 * absurdly large numbers (58 billion for a 96% user — nonsensical).
 */
object RarityCalculator {

    data class RarityResult(
        val combinedProbability: Double,
        val oneInX: Long,
        val percentile: Double,
        val tier: RarityTier,
        val rarestTraitIndex: Int,
        val rarityScore: Double,
    )

    enum class RarityTier(val label: String) {
        COMMON("Common"),
        UNCOMMON("Uncommon"),
        RARE("Rare"),
        EPIC("Epic"),
        LEGENDARY("Legendary"),
        MYTHIC("One of a Kind"),
    }

    // baseline: what the "most common" person would answer
    private val maxProbabilities = listOf(
        0.89, 0.79, 0.38, 0.75, 0.70,
        0.78, 0.43, 0.85, 0.9899, 0.70,
        0.99932, 0.966, 0.50, 0.50, 0.50,
        0.80, 0.70, 0.75, 0.70, 0.935,
    )

    fun calculate(answers: List<UserAnswer>): RarityResult {
        if (answers.isEmpty()) {
            return RarityResult(1.0, 1, 0.0, RarityTier.COMMON, 0, 0.0)
        }

        // use log-space to avoid floating point underflow
        var logCombined = 0.0
        var rarestProb = 1.0
        var rarestIdx = 0
        var logScore = 0.0

        for ((i, answer) in answers.withIndex()) {
            // skip neutral answers like "I don't know"
            if (answer.probability >= 1.0) continue

            val prob = answer.probability.coerceAtLeast(0.000001)
            logCombined += ln(prob)

            if (answer.probability < rarestProb) {
                rarestProb = answer.probability
                rarestIdx = i
            }

            val maxProb = if (i < maxProbabilities.size) maxProbabilities[i] else 0.5
            val ratio = maxProb / prob
            logScore += ln(ratio.coerceAtLeast(1.0))
        }

        val combined = exp(logCombined)

        // sigmoid curve to map log-score to percentile
        val midpoint = 8.0
        val steepness = 0.30
        val rawPercentile = 100.0 / (1.0 + exp(-steepness * (logScore - midpoint)))
        val percentile = (rawPercentile * 100).roundToInt() / 100.0

        // derive "1 in X" from the percentile so both numbers are consistent
        // 96% → 1 in 25, 99% → 1 in 100, 99.9% → 1 in 1,000
        val oneInX = when {
            rawPercentile >= 99.99999 -> 8_100_000_000L
            rawPercentile <= 1.0 -> 1L
            else -> (100.0 / (100.0 - rawPercentile)).toLong().coerceIn(1L, 8_100_000_000L)
        }

        val tier = when {
            percentile >= 99.0 -> RarityTier.MYTHIC
            percentile >= 95.0 -> RarityTier.LEGENDARY
            percentile >= 80.0 -> RarityTier.EPIC
            percentile >= 55.0 -> RarityTier.RARE
            percentile >= 30.0 -> RarityTier.UNCOMMON
            else -> RarityTier.COMMON
        }

        return RarityResult(combined, oneInX, percentile, tier, rarestIdx, logScore)
    }

    /**
     * Formats the "1 in X" number for display.
     * Keeps it human-readable so it fits nicely in the ring.
     */
    fun formatOneInX(value: Long): String {
        return when {
            value >= 1_000_000_000 -> {
                val billions = value / 1_000_000_000.0
                if (billions >= 10) "%.0f Billion".format(billions)
                else "%.1f Billion".format(billions)
            }
            value >= 1_000_000 -> {
                val millions = value / 1_000_000.0
                if (millions >= 10) "%.0f Million".format(millions)
                else "%.1f Million".format(millions)
            }
            value >= 100_000 -> {
                "%.0fK".format(value / 1_000.0)
            }
            value >= 10_000 -> {
                "%.1fK".format(value / 1_000.0)
            }
            else -> String.format("%,d", value)
        }
    }
}
