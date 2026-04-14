package com.devzain.howrareareyou.data

/**
 * Represents a single quiz question with its answer options.
 * Each option has a statistical probability attached to it which
 * we use later to calculate the overall rarity score.
 */
data class QuizQuestion(
    val id: Int,
    val category: QuizCategory,
    val text: String,
    val options: List<AnswerOption>,
    val funFact: String,        // shown after the user picks an answer
)

data class AnswerOption(
    val text: String,
    val probability: Double,    // what % of world population has this trait (0.0 to 1.0)
    val label: String = "",     // optional short label like "Very rare" or "Common"
)

enum class QuizCategory(val displayName: String) {
    PHYSICAL("Physical Traits"),
    SKILLS("Abilities"),
    LIFE("Life & Background"),
    QUIRKS("Quirks"),
}

/**
 * Holds the user's selected answer for a specific question.
 * We store both the index and the probability so we don't
 * have to look it up again during score calculation.
 */
data class UserAnswer(
    val questionId: Int,
    val selectedIndex: Int,
    val probability: Double,
)
