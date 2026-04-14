package com.devzain.howrareareyou.data

/**
 * The full question bank for the rarity quiz.
 *
 * All probabilities are based on real-world population data from
 * WHO, various census bureaus, and published scientific studies.
 * They don't need to be 100% precise — they just need to be
 * in the right ballpark to make the rarity score feel meaningful.
 *
 * The questions are ordered to start easy and personal (handedness,
 * eye color) before moving to more unusual ones (perfect pitch,
 * lightning strikes). This keeps people engaged through the flow.
 */
object QuestionBank {

    fun getAllQuestions(): List<QuizQuestion> = allQuestions

    private val allQuestions = listOf(

        // ============ PHYSICAL TRAITS (Q1-Q5) ============

        QuizQuestion(
            id = 1,
            category = QuizCategory.PHYSICAL,
            text = "Which hand do you write with?",
            options = listOf(
                AnswerOption("Right hand", 0.89, "Most common"),
                AnswerOption("Left hand", 0.10, "Uncommon"),
                AnswerOption("Both hands", 0.01, "Very rare"),
            ),
            funFact = "Only about 10% of people are left-handed. Famous lefties include Leonardo da Vinci, Albert Einstein, and Barack Obama!"
        ),

        QuizQuestion(
            id = 2,
            category = QuizCategory.PHYSICAL,
            text = "What is your natural eye color?",
            options = listOf(
                AnswerOption("Brown", 0.79, "Most common"),
                AnswerOption("Blue", 0.08, "Uncommon"),
                AnswerOption("Hazel / Amber", 0.08, "Uncommon"),
                AnswerOption("Green", 0.02, "Very rare"),
                AnswerOption("Gray", 0.03, "Rare"),
            ),
            funFact = "Green is the rarest natural eye color — only about 2% of the world's population has it."
        ),

        QuizQuestion(
            id = 3,
            category = QuizCategory.PHYSICAL,
            text = "What is your blood type?",
            options = listOf(
                AnswerOption("O+", 0.38, "Most common"),
                AnswerOption("A+", 0.27, "Common"),
                AnswerOption("B+", 0.22, "Common"),
                AnswerOption("AB+", 0.05, "Uncommon"),
                AnswerOption("O-", 0.04, "Uncommon"),
                AnswerOption("A-", 0.02, "Rare"),
                AnswerOption("B-", 0.01, "Very rare"),
                AnswerOption("AB-", 0.006, "Extremely rare"),
            ),
            funFact = "AB- is the rarest blood type, found in less than 1% of people. If you have it, blood banks really want to hear from you!"
        ),

        QuizQuestion(
            id = 4,
            category = QuizCategory.PHYSICAL,
            text = "What is your natural hair color?",
            options = listOf(
                AnswerOption("Black", 0.75, "Most common"),
                AnswerOption("Brown", 0.11, "Common"),
                AnswerOption("Blonde", 0.02, "Uncommon"),
                AnswerOption("Red / Auburn", 0.013, "Very rare"),
                AnswerOption("Other / Mixed", 0.107, "Varies"),
            ),
            funFact = "Only 1-2% of people worldwide are natural redheads. Scotland has the highest concentration at about 13%."
        ),

        QuizQuestion(
            id = 5,
            category = QuizCategory.PHYSICAL,
            text = "Can you roll your tongue into a tube shape?",
            options = listOf(
                AnswerOption("Yes, easily", 0.70, "Common"),
                AnswerOption("No, I can't", 0.30, "Uncommon"),
            ),
            funFact = "About 70% of people can roll their tongue. Scientists used to think it was purely genetic, but studies show it might be partially learned!"
        ),

        // ============ ABILITIES & SKILLS (Q6-Q10) ============

        QuizQuestion(
            id = 6,
            category = QuizCategory.SKILLS,
            text = "Can you wiggle your ears without touching them?",
            options = listOf(
                AnswerOption("Yes", 0.22, "Uncommon"),
                AnswerOption("Only one ear", 0.08, "Rare"),
                AnswerOption("No", 0.70, "Common"),
            ),
            funFact = "Ear wiggling uses muscles that most humans have lost the ability to control. About 22% of people can still do it."
        ),

        QuizQuestion(
            id = 7,
            category = QuizCategory.SKILLS,
            text = "How many languages do you speak fluently?",
            options = listOf(
                AnswerOption("1 language", 0.40, "Common"),
                AnswerOption("2 languages", 0.43, "Common"),
                AnswerOption("3 languages", 0.13, "Uncommon"),
                AnswerOption("4 or more", 0.03, "Very rare"),
            ),
            funFact = "Only about 3% of the world speaks 4+ languages fluently. The most multilingual person ever documented spoke 65 languages!"
        ),

        QuizQuestion(
            id = 8,
            category = QuizCategory.SKILLS,
            text = "Can you do a backflip (on ground, no trampoline)?",
            options = listOf(
                AnswerOption("Yes", 0.05, "Rare"),
                AnswerOption("No", 0.95, "Common"),
            ),
            funFact = "Roughly 1 in 20 people can do a standing backflip. It requires both physical ability and the courage to actually try it."
        ),

        QuizQuestion(
            id = 9,
            category = QuizCategory.SKILLS,
            text = "Do you have perfect pitch (identify musical notes by ear)?",
            options = listOf(
                AnswerOption("Yes, confirmed", 0.0001, "Extremely rare"),
                AnswerOption("I think so, not tested", 0.01, "Rare"),
                AnswerOption("No", 0.9899, "Common"),
            ),
            funFact = "True perfect pitch occurs in roughly 1 in 10,000 people. Mozart, Beethoven, and Mariah Carey are all believed to have had it."
        ),

        QuizQuestion(
            id = 10,
            category = QuizCategory.SKILLS,
            text = "Can you raise just one eyebrow independently?",
            options = listOf(
                AnswerOption("Yes, either one", 0.15, "Uncommon"),
                AnswerOption("Yes, but only one side", 0.15, "Uncommon"),
                AnswerOption("No", 0.70, "Common"),
            ),
            funFact = "About 30% of people can raise a single eyebrow. The Rock made it famous, but most people genuinely can't do it no matter how hard they try!"
        ),

        // ============ LIFE & BACKGROUND (Q11-Q15) ============

        QuizQuestion(
            id = 11,
            category = QuizCategory.LIFE,
            text = "What month were you born in?",
            options = listOf(
                AnswerOption("January", 0.083, ""),
                AnswerOption("February", 0.076, "Slightly less common"),
                AnswerOption("March", 0.084, ""),
                AnswerOption("April", 0.082, ""),
                AnswerOption("May", 0.084, ""),
                AnswerOption("June", 0.083, ""),
                AnswerOption("July", 0.087, "Slightly more common"),
                AnswerOption("August", 0.088, "Slightly more common"),
                AnswerOption("September", 0.087, "Slightly more common"),
                AnswerOption("October", 0.084, ""),
                AnswerOption("November", 0.081, ""),
                AnswerOption("December", 0.082, ""),
            ),
            funFact = "September is statistically the most common birth month worldwide. February is the least common — partly because it has fewer days!"
        ),

        QuizQuestion(
            id = 12,
            category = QuizCategory.LIFE,
            text = "Were you born on a leap day (February 29)?",
            options = listOf(
                AnswerOption("Yes!", 0.00068, "Extremely rare"),
                AnswerOption("No", 0.99932, "Common"),
            ),
            funFact = "Only about 5 million people alive today were born on Feb 29th. That's roughly 1 in 1,461 people — a true calendar rarity!"
        ),

        QuizQuestion(
            id = 13,
            category = QuizCategory.LIFE,
            text = "Are you a twin, triplet, or more?",
            options = listOf(
                AnswerOption("No, single birth", 0.966, "Common"),
                AnswerOption("Yes, I'm a twin", 0.033, "Uncommon"),
                AnswerOption("Triplet or more", 0.001, "Very rare"),
            ),
            funFact = "The twin birth rate has been rising globally — it's now about 3.3%. Nigeria has the highest rate of twins in the world!"
        ),

        QuizQuestion(
            id = 14,
            category = QuizCategory.LIFE,
            text = "How many countries have you visited?",
            options = listOf(
                AnswerOption("0-5 countries", 0.65, "Most common"),
                AnswerOption("6-15 countries", 0.25, "Above average"),
                AnswerOption("16-30 countries", 0.07, "Uncommon"),
                AnswerOption("31+ countries", 0.03, "Very rare"),
            ),
            funFact = "The average person visits about 7 countries in their lifetime. Only 3% of people have been to more than 30!"
        ),

        QuizQuestion(
            id = 15,
            category = QuizCategory.LIFE,
            text = "Have you ever broken a bone?",
            options = listOf(
                AnswerOption("Never", 0.50, "Common"),
                AnswerOption("Yes, once", 0.30, "Common"),
                AnswerOption("Yes, 2 or more times", 0.20, "Uncommon"),
            ),
            funFact = "About half of all people go through life without ever breaking a bone. The most commonly broken bone? The collarbone."
        ),

        // ============ QUIRKS (Q16-Q20) ============

        QuizQuestion(
            id = 16,
            category = QuizCategory.QUIRKS,
            text = "Do you have a gap between your front teeth (diastema)?",
            options = listOf(
                AnswerOption("Yes", 0.25, "Uncommon"),
                AnswerOption("No", 0.75, "Common"),
            ),
            funFact = "A diastema is considered a sign of beauty and good luck in many West African cultures. Models like Lauren Hutton made it iconic."
        ),

        QuizQuestion(
            id = 17,
            category = QuizCategory.QUIRKS,
            text = "Are any of your joints double-jointed (hypermobile)?",
            options = listOf(
                AnswerOption("Yes, noticeably", 0.15, "Uncommon"),
                AnswerOption("Maybe slightly", 0.10, "Uncommon"),
                AnswerOption("No", 0.75, "Common"),
            ),
            funFact = "About 10-25% of people have some degree of joint hypermobility. It's more common in women and tends to decrease with age."
        ),

        QuizQuestion(
            id = 18,
            category = QuizCategory.QUIRKS,
            text = "Can you snap your fingers on both hands?",
            options = listOf(
                AnswerOption("Yes, both hands", 0.60, "Common"),
                AnswerOption("Only one hand", 0.25, "Somewhat common"),
                AnswerOption("I can't snap at all", 0.15, "Uncommon"),
            ),
            funFact = "Snapping produces sound at about 7,800 degrees per second of angular velocity — faster than the swing of a pro baseball pitcher's arm!"
        ),

        QuizQuestion(
            id = 19,
            category = QuizCategory.QUIRKS,
            text = "Do you have a hitchhiker's thumb (bends backward >45°)?",
            options = listOf(
                AnswerOption("Yes", 0.25, "Uncommon"),
                AnswerOption("Slightly", 0.35, "Common"),
                AnswerOption("No, it stays straight", 0.40, "Common"),
            ),
            funFact = "The hitchhiker's thumb is a classic genetics textbook example, but it's actually controlled by multiple genes — not just one simple switch."
        ),

        QuizQuestion(
            id = 20,
            category = QuizCategory.QUIRKS,
            text = "Have you ever been struck by lightning?",
            options = listOf(
                AnswerOption("Yes (seriously)", 0.00003, "Astronomically rare"),
                AnswerOption("No", 0.99997, "Common"),
            ),
            funFact = "The odds of being struck by lightning in your lifetime are about 1 in 15,300. Roy Sullivan holds the record at 7 strikes — and survived them all!"
        ),
    )
}
