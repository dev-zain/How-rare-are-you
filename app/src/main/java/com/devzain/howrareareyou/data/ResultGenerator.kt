package com.devzain.howrareareyou.data

/**
 * Generates personalized rarity descriptions and explanations.
 * No AI API needed — uses templates that feel specific and personal.
 */
object ResultGenerator {

    /**
     * Represents a single rare trait with full context for display.
     */
    data class RareTrait(
        val category: String,     // "Eye Color", "Perfect Pitch", etc.
        val traitName: String,    // "Green eyes", "Confirmed perfect pitch"
        val percentage: Double,   // 2.0, 0.01, etc.
    )

    fun generateDescription(
        answers: List<UserAnswer>,
        result: RarityCalculator.RarityResult,
    ): String {
        if (answers.isEmpty()) return "Take the quiz to discover your rarity!"

        // get the top 3 rarest traits using their descriptive labels
        val rarestTraits = answers
            .filter { it.probability < 0.5 && it.traitName.isNotEmpty() }
            .sortedBy { it.probability }
            .take(3)

        val traitNames = rarestTraits.map { it.traitName }

        val traitString = when (traitNames.size) {
            0 -> return pickGenericLine(result.tier)
            1 -> traitNames[0]
            2 -> "${traitNames[0]} and ${traitNames[1]}"
            else -> "${traitNames[0]}, ${traitNames[1]}, and ${traitNames[2]}"
        }

        val closer = when (result.tier) {
            RarityCalculator.RarityTier.MYTHIC ->
                "You're literally one of a kind. The odds of someone matching your exact profile are astronomically low."
            RarityCalculator.RarityTier.LEGENDARY ->
                "The universe clearly had something special in mind when it made you."
            RarityCalculator.RarityTier.EPIC ->
                "Your combination of traits is genuinely unusual \u2014 you stand out from the crowd."
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
                "Your traits may be common individually, but remember \u2014 no two people have the exact same combination."
        }
    }

    /**
     * Returns the top 3 rarest traits with their category labels and percentages.
     * These are shown on the result card and share postcard.
     *
     * Example: RareTrait("Perfect Pitch", "Confirmed perfect pitch", 0.01)
     */
    fun getTopRarestTraits(answers: List<UserAnswer>): List<RareTrait> {
        return answers
            .filter { it.traitName.isNotEmpty() && it.probability < 0.5 }
            .sortedBy { it.probability }
            .take(3)
            .map { RareTrait(it.questionShortName, it.traitName, it.probability * 100) }
    }

    /**
     * Generates a plain-language explanation of what the score actually means.
     * Shown in the "What Your Score Means" card on the result screen.
     */
    fun generateScoreExplanation(result: RarityCalculator.RarityResult): String {
        val formattedOneInX = RarityCalculator.formatOneInX(result.oneInX)
        val percentStr = "%.2f".format(result.percentile)
        val wholePercent = percentStr.split(".")[0]

        val tierLine = when (result.tier) {
            RarityCalculator.RarityTier.MYTHIC ->
                "\uD83C\uDF1F One of a Kind \u2014 You're in the top 1% of humanity!"
            RarityCalculator.RarityTier.LEGENDARY ->
                "\uD83D\uDD25 Legendary \u2014 You're in the top 5% of all humans!"
            RarityCalculator.RarityTier.EPIC ->
                "\uD83D\uDC8E Epic \u2014 You're rarer than 80%+ of people on Earth!"
            RarityCalculator.RarityTier.RARE ->
                "\u2728 Rare \u2014 You genuinely stand out from the majority!"
            RarityCalculator.RarityTier.UNCOMMON ->
                "\uD83D\uDCA0 Uncommon \u2014 You've got some unique traits in the mix!"
            RarityCalculator.RarityTier.COMMON ->
                "\uD83D\uDD35 Your traits may be common, but your exact combination is still one of a kind!"
        }

        return buildString {
            append("Your rarity score of $percentStr% means your specific combination of traits is rarer than about $wholePercent out of every 100 people on Earth.\n\n")
            append("\"1 in $formattedOneInX\" means if you gathered $formattedOneInX random people, statistically only 1 of them would share your same trait combination.\n\n")
            append(tierLine)
        }
    }
}
