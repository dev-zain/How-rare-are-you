package com.devzain.howrareareyou.data

data class QuizQuestion(
    val id: Int,
    val category: QuizCategory,
    val shortName: String,           // e.g. "Eye Color" — used on result/share cards for context
    val text: String,
    val explanation: String = "",    // extra context shown below the question if the term is unfamiliar
    val options: List<AnswerOption>,
    val funFact: String,
    val source: String,             // where the probability data comes from
)

data class AnswerOption(
    val text: String,
    val probability: Double,
    val label: String = "",
    val traitLabel: String = "",   // short name for result card, e.g. "Left-handed" or "Natural red hair"
    val resultNote: String = "",   // brief explanation shown in the result breakdown
)

enum class QuizCategory(val displayName: String, val emoji: String) {
    PHYSICAL("Physical Traits", "\uD83E\uDDEC"),      // 🧬
    SKILLS("Abilities", "\u26A1"),                      // ⚡
    LIFE("Life & Background", "\uD83C\uDF0D"),          // 🌍
    QUIRKS("Quirks", "\uD83C\uDFAD"),                   // 🎭
}

data class UserAnswer(
    val questionId: Int,
    val selectedIndex: Int,
    val probability: Double,
    val traitName: String = "",            // short name like "Green eyes" for the result screen
    val questionShortName: String = "",    // "Eye Color", "Perfect Pitch", etc.
)
