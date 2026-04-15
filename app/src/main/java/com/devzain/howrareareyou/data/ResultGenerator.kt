package com.devzain.howrareareyou.data

/**
 * Generates personalized rarity descriptions without needing an AI API.
 *
 * Uses the user's top 2-3 rarest traits to pick a fitting template.
 * The key insight: we don't need infinite variety. We need maybe 15
 * templates that feel specific enough to surprise the user. Nobody
 * takes the quiz more than 2-3 times, so they won't see repeats.
 */
object ResultGenerator {

    /**
     * Builds a short, punchy description line from the user's rarest traits.
     * Returns something like: "A green-eyed, left-handed polyglot?
     * The universe clearly put extra thought into you."
     */
    fun generateDescription(
        answers: List<UserAnswer>,
        result: RarityCalculator.RarityResult,
    ): String {
        if (answers.isEmpty()) return "Take the quiz to discover your rarity!"

        // grab the top 3 rarest traits (lowest probability = most rare)
        val sortedByRarity = answers
            .filter { it.probability < 0.5 }  // skip super common answers
            .sortedBy { it.probability }
            .take(3)

        // build a trait list like "green-eyed, left-handed, trilingual"
        val traitNames = sortedByRarity.map { it.traitName }.filter { it.isNotEmpty() }

        val traitString = when (traitNames.size) {
            0 -> return pickGenericLine(result.tier)
            1 -> traitNames[0]
            2 -> "${traitNames[0]} and ${traitNames[1]}"
            else -> "${traitNames[0]}, ${traitNames[1]}, and ${traitNames[2]}"
        }

        // pick a closing line based on the tier
        val closer = when (result.tier) {
            RarityCalculator.RarityTier.MYTHIC ->
                "You're literally one of a kind. The odds of someone matching your exact profile are astronomically low."
            RarityCalculator.RarityTier.LEGENDARY ->
                "The universe clearly had something special in mind when it made you."
            RarityCalculator.RarityTier.EPIC ->
                "Your combination of traits is genuinely unusual — you stand out from the crowd."
            RarityCalculator.RarityTier.RARE ->
                "That's a pretty unique combination. Not many people share your exact profile."
            RarityCalculator.RarityTier.UNCOMMON ->
                "You've got some interesting traits that set you apart from the average person."
            RarityCalculator.RarityTier.COMMON ->
                "Your traits are on the common side individually, but your exact combination is still uniquely yours."
        }

        return "$traitString? $closer"
    }

    private fun pickGenericLine(tier: RarityCalculator.RarityTier): String {
        return when (tier) {
            RarityCalculator.RarityTier.MYTHIC ->
                "Your combination of traits is statistically extraordinary. There may be no one else quite like you on the planet."
            RarityCalculator.RarityTier.LEGENDARY ->
                "You are remarkably unique. Your specific combination of traits is shared by almost nobody on Earth."
            RarityCalculator.RarityTier.EPIC ->
                "Your trait profile puts you in truly rare territory. Most people you meet will never share your combination."
            RarityCalculator.RarityTier.RARE ->
                "You're rarer than you probably realized. Your specific combination of traits is genuinely uncommon."
            RarityCalculator.RarityTier.UNCOMMON ->
                "You've got a unique mix of traits. While some are common individually, your combination tells a different story."
            RarityCalculator.RarityTier.COMMON ->
                "Your traits may be common individually, but remember — no two people have the exact same combination. You're still one of a kind."
        }
    }

    /**
     * Returns the top 3 rarest traits for display on the result card.
     * Each pair contains the trait name and its percentage.
     */
    fun getTopRarestTraits(answers: List<UserAnswer>): List<Pair<String, Double>> {
        return answers
            .filter { it.traitName.isNotEmpty() && it.probability < 0.5 }
            .sortedBy { it.probability }
            .take(3)
            .map { Pair(it.traitName, it.probability * 100) }
    }
}
