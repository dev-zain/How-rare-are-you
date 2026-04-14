package com.devzain.howrareareyou.data

import kotlin.math.roundToInt

/**
 * Handles the rarity score math.
 *
 * The core idea is simple: multiply all the probabilities together
 * to get the combined probability of someone having ALL those traits.
 * Then invert it to get the "1 in X" number.
 *
 * For example if you're left-handed (10%) with green eyes (2%):
 *   combined = 0.10 * 0.02 = 0.002
 *   oneInX = 1 / 0.002 = 500
 *   percentile = (1 - 0.002) * 100 = 99.8%
 *   So you'd be "1 in 500" and "rarer than 99.8% of people"
 */
object RarityCalculator {

    data class RarityResult(
        val combinedProbability: Double,
        val oneInX: Long,               // "1 in 2,380,952"
        val percentile: Double,         // 99.97
        val tier: RarityTier,
        val rarestTraitIndex: Int,      // which question had the rarest answer
    )

    enum class RarityTier(val label: String) {
        COMMON("Common"),              // <50th percentile
        UNCOMMON("Uncommon"),          // 50-80th
        RARE("Rare"),                  // 80-95th
        EPIC("Epic"),                  // 95-99th
        LEGENDARY("Legendary"),        // 99-99.9th
        MYTHIC("One of a Kind"),       // 99.9%+
    }

    fun calculate(answers: List<UserAnswer>): RarityResult {
        if (answers.isEmpty()) {
            return RarityResult(1.0, 1, 0.0, RarityTier.COMMON, 0)
        }

        // multiply all probabilities together
        var combined = 1.0
        var rarestProb = 1.0
        var rarestIdx = 0

        for (answer in answers) {
            combined *= answer.probability

            // track which single trait was the rarest
            if (answer.probability < rarestProb) {
                rarestProb = answer.probability
                rarestIdx = answers.indexOf(answer)
            }
        }

        // "1 in X" — just the inverse of the combined probability
        val oneInX = if (combined > 0) (1.0 / combined).toLong() else 1L

        // percentile — how rare you are compared to everyone
        val percentile = ((1.0 - combined) * 100)
            .coerceIn(0.0, 99.99999)

        // round to 2 decimal places for display
        val roundedPercentile = (percentile * 100).roundToInt() / 100.0

        val tier = when {
            roundedPercentile >= 99.9 -> RarityTier.MYTHIC
            roundedPercentile >= 99.0 -> RarityTier.LEGENDARY
            roundedPercentile >= 95.0 -> RarityTier.EPIC
            roundedPercentile >= 80.0 -> RarityTier.RARE
            roundedPercentile >= 50.0 -> RarityTier.UNCOMMON
            else -> RarityTier.COMMON
        }

        return RarityResult(
            combinedProbability = combined,
            oneInX = oneInX,
            percentile = roundedPercentile,
            tier = tier,
            rarestTraitIndex = rarestIdx,
        )
    }

    /**
     * Formats the "1 in X" number with commas for readability.
     * e.g. 2380952 becomes "2,380,952"
     */
    fun formatOneInX(value: Long): String {
        return String.format("%,d", value)
    }
}
