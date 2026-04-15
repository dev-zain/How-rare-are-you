package com.devzain.howrareareyou.data

import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.roundToInt

/**
 * The rarity math engine.
 *
 * Uses two scoring approaches:
 * 1. Raw "1 in X" — multiplies all probabilities. This is the
 *    impressive number for the share card ("1 in 2,380,952")
 * 2. Relative rarity score — compares your answers against
 *    the most common baseline using log-probabilities.
 *    This creates actual differentiation between users.
 *
 * The raw multiplication approach gives everyone 99.9%+
 * because even common traits have sub-1.0 probabilities.
 * The log-based approach solves this by measuring HOW MUCH
 * rarer you are compared to the most common person.
 */
object RarityCalculator {

    data class RarityResult(
        val combinedProbability: Double,
        val oneInX: Long,
        val percentile: Double,
        val tier: RarityTier,
        val rarestTraitIndex: Int,
        val rarityScore: Double,    // raw log-score for internal use
    )

    enum class RarityTier(val label: String) {
        COMMON("Common"),
        UNCOMMON("Uncommon"),
        RARE("Rare"),
        EPIC("Epic"),
        LEGENDARY("Legendary"),
        MYTHIC("One of a Kind"),
    }

    /**
     * The max probability for each question — i.e. what the "most common"
     * person would pick. We need this so we can compare against a baseline.
     * Pulled from QuestionBank's most common answer per question.
     */
    private val maxProbabilities = listOf(
        0.89,    // Q1: right-handed
        0.79,    // Q2: brown eyes
        0.38,    // Q3: O+ blood (or 1.0 for "don't know")
        0.75,    // Q4: black hair
        0.70,    // Q5: tongue rolling yes
        0.78,    // Q6: can't wiggle ears
        0.43,    // Q7: 2 languages
        0.85,    // Q8: can't do a backflip
        0.9899,  // Q9: no perfect pitch
        0.70,    // Q10: both eyebrows move together
        0.99932, // Q11: not leap day
        0.966,   // Q12: not a twin
        0.50,    // Q13: 1-3 countries
        0.50,    // Q14: never broken bone
        0.50,    // Q15: middle chronotype
        0.80,    // Q16: no dimples
        0.70,    // Q17: can cross eyes
        0.75,    // Q18: normal flexibility
        0.70,    // Q19: no tooth gap
        0.935,   // Q20: not colorblind
    )

    fun calculate(answers: List<UserAnswer>): RarityResult {
        if (answers.isEmpty()) {
            return RarityResult(1.0, 1, 0.0, RarityTier.COMMON, 0, 0.0)
        }

        var combined = 1.0
        var rarestProb = 1.0
        var rarestIdx = 0
        var logScore = 0.0

        for ((i, answer) in answers.withIndex()) {
            combined *= answer.probability

            if (answer.probability < rarestProb) {
                rarestProb = answer.probability
                rarestIdx = i
            }

            // skip neutral answers (probability >= 1.0, like "I don't know")
            if (answer.probability >= 1.0) continue

            // how much rarer is this answer compared to the most common option?
            val maxProb = if (i < maxProbabilities.size) maxProbabilities[i] else 0.5
            val ratio = maxProb / answer.probability.coerceAtLeast(0.0001)
            logScore += ln(ratio.coerceAtLeast(1.0))
        }

        val oneInX = if (combined > 0) (1.0 / combined).toLong() else 1L

        // map the log-score to a percentile using a sigmoid-like curve
        // calibrated so that:
        //   - all common answers ≈ 0 log-score → ~10-15% percentile
        //   - a few uncommon picks → ~40-60%
        //   - several rare picks → ~80-95%
        //   - many very rare picks → ~97%+
        //
        // the midpoint (8.0) and steepness (0.30) were tuned by running
        // the 20 questions with various answer profiles
        val midpoint = 8.0
        val steepness = 0.30
        val rawPercentile = 100.0 / (1.0 + exp(-steepness * (logScore - midpoint)))
        val percentile = (rawPercentile * 100).roundToInt() / 100.0

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
     * Uses short human-readable forms so it fits inside the ring:
     *   1,234 → "1,234"
     *   52,300 → "52.3K"
     *   2,380,952 → "2.4 Million"
     *   1,000,000,000 → "1 Billion"
     */
    fun formatOneInX(value: Long): String {
        return when {
            value >= 1_000_000_000 -> {
                val billions = value / 1_000_000_000.0
                if (billions == billions.toLong().toDouble()) "${billions.toLong()} Billion"
                else "%.1f Billion".format(billions)
            }
            value >= 1_000_000 -> {
                val millions = value / 1_000_000.0
                if (millions == millions.toLong().toDouble()) "${millions.toLong()} Million"
                else "%.1f Million".format(millions)
            }
            value >= 100_000 -> {
                val thousands = value / 1_000.0
                "%.0fK".format(thousands)
            }
            value >= 10_000 -> {
                val thousands = value / 1_000.0
                "%.1fK".format(thousands)
            }
            else -> String.format("%,d", value)
        }
    }
}
