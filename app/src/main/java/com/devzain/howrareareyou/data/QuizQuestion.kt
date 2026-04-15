package com.devzain.howrareareyou.data

data class QuizQuestion(
    val id: Int,
    val category: QuizCategory,
    val text: String,
    val explanation: String = "",   // extra context shown below the question if the term is unfamiliar
    val options: List<AnswerOption>,
    val funFact: String,
    val source: String,             // where the probability data comes from
)

data class AnswerOption(
    val text: String,
    val probability: Double,
    val label: String = "",
    val traitLabel: String = "",  // short name for result card, e.g. "Left-handed" or "Natural red hair"
)

enum class QuizCategory(val displayName: String) {
    PHYSICAL("Physical Traits"),
    SKILLS("Abilities"),
    LIFE("Life & Background"),
    QUIRKS("Quirks"),
}

data class UserAnswer(
    val questionId: Int,
    val selectedIndex: Int,
    val probability: Double,
    val traitName: String = "",     // short name like "Green eyes" for the result screen
)
