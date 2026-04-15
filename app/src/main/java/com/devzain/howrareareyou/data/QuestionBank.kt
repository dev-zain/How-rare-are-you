package com.devzain.howrareareyou.data

/**
 * All 20 quiz questions with real-world probabilities.
 *
 * Each answer option now has a `traitLabel` — a short, descriptive
 * name that shows on the result card and share postcard. This way
 * users (and their friends on social media) immediately understand
 * what the trait is, instead of seeing the raw quiz answer text.
 *
 * Data sources are cited per question.
 */
object QuestionBank {

    fun getAllQuestions(): List<QuizQuestion> = questions

    private val questions = listOf(

        // ===== PHYSICAL TRAITS (Q1-Q5) =====

        QuizQuestion(
            id = 1,
            category = QuizCategory.PHYSICAL,
            text = "Which hand do you write with?",
            options = listOf(
                AnswerOption("Right hand", 0.89, "Most common", traitLabel = "Right-handed"),
                AnswerOption("Left hand", 0.10, "Uncommon", traitLabel = "Left-handed"),
                AnswerOption("I use both hands equally", 0.01, "Very rare", traitLabel = "Ambidextrous"),
            ),
            funFact = "Only about 10% of people are left-handed. Famous lefties include Leonardo da Vinci, Albert Einstein, and Barack Obama!",
            source = "Papadatou-Pastou et al., 2020 — Psychological Bulletin"
        ),

        QuizQuestion(
            id = 2,
            category = QuizCategory.PHYSICAL,
            text = "What is your natural eye color?",
            explanation = "Pick the closest match. If your eyes change color in different lighting, pick what they look like most of the time.",
            options = listOf(
                AnswerOption("Brown or dark brown", 0.79, "Most common worldwide", traitLabel = "Brown eyes"),
                AnswerOption("Blue", 0.08, "Uncommon globally", traitLabel = "Blue eyes"),
                AnswerOption("Hazel (brown-green mix)", 0.05, "Uncommon", traitLabel = "Hazel eyes"),
                AnswerOption("Amber (golden/yellowish)", 0.03, "Rare", traitLabel = "Amber eyes"),
                AnswerOption("Gray", 0.03, "Rare", traitLabel = "Gray eyes"),
                AnswerOption("Green", 0.02, "Very rare", traitLabel = "Green eyes"),
            ),
            funFact = "Green is the rarest natural eye color — only about 2% of the world has it. All blue-eyed people share a single common ancestor from around 10,000 years ago!",
            source = "World Atlas, American Academy of Ophthalmology"
        ),

        QuizQuestion(
            id = 3,
            category = QuizCategory.PHYSICAL,
            text = "What is your blood type?",
            explanation = "You can find this on medical records, blood donation cards, or some health apps. If you don't know, pick \"I don't know\" — we won't count it against your rarity.",
            options = listOf(
                AnswerOption("O+", 0.38, "Most common", traitLabel = "O+ blood type"),
                AnswerOption("A+", 0.27, "Common", traitLabel = "A+ blood type"),
                AnswerOption("B+", 0.22, "Common", traitLabel = "B+ blood type"),
                AnswerOption("AB+", 0.05, "Uncommon", traitLabel = "AB+ blood type"),
                AnswerOption("O\u2212", 0.04, "Uncommon", traitLabel = "O\u2212 blood type"),
                AnswerOption("A\u2212", 0.02, "Rare", traitLabel = "A\u2212 blood type"),
                AnswerOption("B\u2212", 0.01, "Very rare", traitLabel = "B\u2212 blood type"),
                AnswerOption("AB\u2212", 0.006, "Extremely rare", traitLabel = "AB\u2212 blood type"),
                // probability = 1.0 means neutral — doesn't affect the rarity score
                AnswerOption("I don't know my blood type", 1.0, "", traitLabel = ""),
            ),
            funFact = "AB\u2212 is the rarest blood type — less than 1% of people have it. Meanwhile, O\u2212 is the universal donor that hospitals always need!",
            source = "American Red Cross, Stanford Blood Center"
        ),

        QuizQuestion(
            id = 4,
            category = QuizCategory.PHYSICAL,
            text = "What is your natural hair color?",
            explanation = "Your hair color before any dye or coloring. The color you were born with or had as a teenager.",
            options = listOf(
                AnswerOption("Black", 0.75, "Most common", traitLabel = "Black hair"),
                AnswerOption("Brown (any shade)", 0.11, "Common", traitLabel = "Brown hair"),
                AnswerOption("Blonde / Light", 0.02, "Uncommon", traitLabel = "Natural blonde hair"),
                AnswerOption("Red or Auburn", 0.013, "Very rare", traitLabel = "Natural red hair"),
                AnswerOption("Gray or White (premature)", 0.007, "Very rare", traitLabel = "Premature gray hair"),
            ),
            funFact = "Only 1-2% of people worldwide are natural redheads. Scotland has the highest percentage at about 13% of its population.",
            source = "National Institutes of Health (NIH), ScotlandsDNA Project"
        ),

        QuizQuestion(
            id = 5,
            category = QuizCategory.PHYSICAL,
            text = "Can you roll your tongue into a tube shape?",
            explanation = "Stick out your tongue and try to curl the sides upward to make a U-shape or tube. Some people can do it easily, others can't at all.",
            options = listOf(
                AnswerOption("Yes, I can do it easily", 0.70, "Common", traitLabel = "Can roll tongue"),
                AnswerOption("No, I've tried and I can't", 0.30, "Uncommon", traitLabel = "Can't roll tongue"),
            ),
            funFact = "About 70% of people can roll their tongue. Scientists used to think it was purely genetic, but twin studies showed environment plays a role too!",
            source = "Sturtevant 1940, McDonald 2010 — Journal of Heredity"
        ),

        // ===== ABILITIES (Q6-Q10) =====

        QuizQuestion(
            id = 6,
            category = QuizCategory.SKILLS,
            text = "Can you wiggle your ears without using your hands?",
            explanation = "Try to move your ears up and down or back and forth using just your ear muscles — no touching allowed!",
            options = listOf(
                AnswerOption("Yes, both ears", 0.18, "Uncommon", traitLabel = "Can wiggle both ears"),
                AnswerOption("Only one ear", 0.04, "Rare", traitLabel = "Can wiggle one ear"),
                AnswerOption("No, they don't budge", 0.78, "Common", traitLabel = "Can't wiggle ears"),
            ),
            funFact = "Ear wiggling uses vestigial muscles that most humans have but few can control. Our evolutionary ancestors used them to turn their ears toward sounds — like cats still do!",
            source = "Hackley, 2015 — Psychophysiology journal"
        ),

        QuizQuestion(
            id = 7,
            category = QuizCategory.SKILLS,
            text = "How many languages can you hold a real conversation in?",
            explanation = "Count languages you can actually talk to someone in — not just \"I know a few words.\" Include your native language.",
            options = listOf(
                AnswerOption("1 language", 0.40, "Common", traitLabel = "Monolingual"),
                AnswerOption("2 languages", 0.43, "Common", traitLabel = "Bilingual"),
                AnswerOption("3 languages", 0.13, "Uncommon", traitLabel = "Trilingual"),
                AnswerOption("4 or more languages", 0.03, "Very rare", traitLabel = "Polyglot (4+ languages)"),
            ),
            funFact = "Only about 3% of the world speaks 4+ languages fluently. The most multilingual person documented, Ziad Fazah, claimed to speak 59 languages!",
            source = "European Commission Eurobarometer, Ethnologue"
        ),

        QuizQuestion(
            id = 8,
            category = QuizCategory.SKILLS,
            text = "Can you do a backflip?",
            explanation = "A full backward somersault — standing or from a running start. No trampoline, that doesn't count!",
            options = listOf(
                AnswerOption("Yes, I can do it on flat ground", 0.03, "Very rare", traitLabel = "Can do a standing backflip"),
                AnswerOption("Only on a trampoline or into water", 0.07, "Uncommon", traitLabel = "Trampoline backflipper"),
                AnswerOption("I used to but not anymore", 0.05, "Uncommon", traitLabel = "Former backflipper"),
                AnswerOption("No, never", 0.85, "Common", traitLabel = "Can't do a backflip"),
            ),
            funFact = "Only about 3-5% of people can do a standing backflip. Gymnasts, martial artists, and parkour athletes make it look easy, but it requires serious explosive power and courage!",
            source = "Gymnastics Federation estimates, sports physiology research"
        ),

        QuizQuestion(
            id = 9,
            category = QuizCategory.SKILLS,
            text = "Can you identify musical notes just by hearing them — without any reference?",
            explanation = "This is called 'perfect pitch' or 'absolute pitch.' If someone plays a random piano key, could you name the exact note (like C, F#, Bb) without seeing the keyboard?",
            options = listOf(
                AnswerOption("Yes, I've been tested and confirmed", 0.0001, "Astronomically rare", traitLabel = "Confirmed perfect pitch"),
                AnswerOption("I think so but never formally tested", 0.01, "Rare", traitLabel = "Possible perfect pitch"),
                AnswerOption("No, I need a reference note first", 0.9899, "Common", traitLabel = "No perfect pitch"),
            ),
            funFact = "True perfect pitch occurs in roughly 1 in 10,000 people. Mozart, Beethoven, and Mariah Carey are all believed to have had it. It's more common in speakers of tonal languages like Mandarin.",
            source = "Takeuchi & Hulse, 1993 — Psychonomic Bulletin; Deutsch et al., 2006"
        ),

        QuizQuestion(
            id = 10,
            category = QuizCategory.SKILLS,
            text = "Can you raise just one eyebrow while keeping the other one still?",
            options = listOf(
                AnswerOption("Yes, I can raise either one", 0.10, "Uncommon", traitLabel = "Can raise either eyebrow"),
                AnswerOption("Yes, but only one side works", 0.20, "Somewhat uncommon", traitLabel = "Can raise one eyebrow"),
                AnswerOption("No, both eyebrows always move together", 0.70, "Common", traitLabel = "Can't raise one eyebrow"),
            ),
            funFact = "About 30% of people can raise one eyebrow independently. Dwayne 'The Rock' Johnson made it his signature move — but most people genuinely can't do it!",
            source = "Freitas-Magalhães, 2012 — Facial expression studies"
        ),

        // ===== LIFE & BACKGROUND (Q11-Q15) =====

        QuizQuestion(
            id = 11,
            category = QuizCategory.LIFE,
            text = "Were you born on February 29th (a leap day)?",
            options = listOf(
                AnswerOption("Yes, I'm a leap day baby!", 0.00068, "Extremely rare", traitLabel = "Born on leap day"),
                AnswerOption("No", 0.99932, "Common", traitLabel = "Not a leap day baby"),
            ),
            funFact = "Only about 5 million people alive today were born on Feb 29th — that's roughly 1 in 1,461 people. They're called \"leaplings\" and technically only have a birthday every 4 years!",
            source = "Calendar mathematics — 1/1461 probability"
        ),

        QuizQuestion(
            id = 12,
            category = QuizCategory.LIFE,
            text = "Are you a twin, triplet, or part of a multiple birth?",
            options = listOf(
                AnswerOption("No, I was born alone", 0.966, "Common", traitLabel = "Single birth"),
                AnswerOption("Yes, I have a twin", 0.033, "Uncommon", traitLabel = "Twin"),
                AnswerOption("Triplet or more", 0.001, "Very rare", traitLabel = "Triplet or higher"),
            ),
            funFact = "The twin birth rate has risen 70% since the 1980s, partly due to fertility treatments. Nigeria has the world's highest natural twin rate — about 4.5% of births!",
            source = "CDC National Vital Statistics, 2021"
        ),

        QuizQuestion(
            id = 13,
            category = QuizCategory.LIFE,
            text = "How many countries have you visited in your life?",
            explanation = "Count any country you've physically been to, even for just a layover or day trip. Include your home country.",
            options = listOf(
                AnswerOption("Just my home country", 0.35, "Very common", traitLabel = "Visited 1 country"),
                AnswerOption("2-5 countries", 0.30, "Common", traitLabel = "Visited 2-5 countries"),
                AnswerOption("6-15 countries", 0.20, "Above average", traitLabel = "Visited 6-15 countries"),
                AnswerOption("16-30 countries", 0.09, "Uncommon", traitLabel = "Visited 16-30 countries"),
                AnswerOption("31-50 countries", 0.04, "Rare", traitLabel = "World traveler (31-50)"),
                AnswerOption("More than 50 countries", 0.02, "Very rare", traitLabel = "Globe trotter (50+)"),
            ),
            funFact = "The average person visits about 7 countries in their lifetime. There are 195 countries in the world — only a handful of people have visited them all!",
            source = "Hostelworld Travel Survey, 2023; UN Tourism data"
        ),

        QuizQuestion(
            id = 14,
            category = QuizCategory.LIFE,
            text = "Have you ever broken a bone in your body?",
            options = listOf(
                AnswerOption("Never broken anything", 0.50, "Common", traitLabel = "Never broken a bone"),
                AnswerOption("Yes, broke one bone", 0.30, "Common", traitLabel = "Broke one bone"),
                AnswerOption("Yes, broke 2 or more bones", 0.20, "Uncommon", traitLabel = "Broke 2+ bones"),
            ),
            funFact = "About half of all people go through their entire life without ever breaking a bone. The collarbone is the most commonly fractured bone in the human body.",
            source = "NHS UK, American Orthopaedic Association"
        ),

        QuizQuestion(
            id = 15,
            category = QuizCategory.LIFE,
            text = "Are you naturally a morning person or a night owl?",
            explanation = "Think about when you feel most alert and productive — not what your work schedule forces you to do.",
            options = listOf(
                AnswerOption("Definite morning person — I love early starts", 0.25, "Uncommon", traitLabel = "Early bird"),
                AnswerOption("Somewhere in the middle", 0.50, "Common", traitLabel = "Mixed chronotype"),
                AnswerOption("Night owl — I come alive after dark", 0.25, "Uncommon", traitLabel = "Night owl"),
            ),
            funFact = "Your sleep preference (chronotype) is roughly 50% genetic. True extreme morning people ('larks') and extreme night owls each make up only about 25% of the population.",
            source = "Roenneberg et al., 2007 — Current Biology; 23andMe research"
        ),

        // ===== QUIRKS (Q16-Q20) =====

        QuizQuestion(
            id = 16,
            category = QuizCategory.QUIRKS,
            text = "Do you have dimples when you smile?",
            explanation = "Dimples are the small dents or indentations that appear on some people's cheeks when they smile.",
            options = listOf(
                AnswerOption("Yes, on both cheeks", 0.15, "Uncommon", traitLabel = "Dimples on both cheeks"),
                AnswerOption("Yes, but only on one side", 0.05, "Rare", traitLabel = "Single dimple"),
                AnswerOption("No dimples", 0.80, "Common", traitLabel = "No dimples"),
            ),
            funFact = "Dimples are actually caused by a variation in the facial muscle called the zygomaticus major. They're considered attractive in many cultures and are one of the few 'defects' people find charming!",
            source = "Pessa et al., 1998 — Plastic and Reconstructive Surgery"
        ),

        QuizQuestion(
            id = 17,
            category = QuizCategory.QUIRKS,
            text = "Can you cross your eyes on purpose?",
            explanation = "Try to make both eyes point toward your nose at the same time — like looking at the tip of your own nose.",
            options = listOf(
                AnswerOption("Yes, easily", 0.70, "Common", traitLabel = "Can cross eyes"),
                AnswerOption("Only a little bit", 0.15, "Somewhat common", traitLabel = "Partially cross eyes"),
                AnswerOption("No, I can't do it at all", 0.15, "Uncommon", traitLabel = "Can't cross eyes"),
            ),
            funFact = "Most people can cross their eyes (it's called voluntary convergence), but about 15% of people lack the muscle control to do it. Don't worry — it won't make them get stuck that way!",
            source = "American Association for Pediatric Ophthalmology"
        ),

        QuizQuestion(
            id = 18,
            category = QuizCategory.QUIRKS,
            text = "Are any of your joints unusually flexible?",
            explanation = "Sometimes called 'double-jointed.' Can you bend your thumb backward to touch your wrist? Bend your fingers way back? Do the splits easily?",
            options = listOf(
                AnswerOption("Yes, I'm noticeably flexible", 0.15, "Uncommon", traitLabel = "Hypermobile joints"),
                AnswerOption("A little more flexible than average", 0.10, "Somewhat uncommon", traitLabel = "Slightly flexible"),
                AnswerOption("No, pretty normal flexibility", 0.75, "Common", traitLabel = "Normal flexibility"),
            ),
            funFact = "Joint hypermobility is more common in women and in people of Asian and African descent. About 10-25% of people have some degree of it, but true hypermobility syndrome is much rarer.",
            source = "Beighton et al. — hypermobility scoring system; Arthritis Research UK"
        ),

        QuizQuestion(
            id = 19,
            category = QuizCategory.QUIRKS,
            text = "Do you have a gap between your two front teeth?",
            explanation = "Dentists call this a 'diastema.' It's the visible space between your upper front teeth. Even a small gap counts.",
            options = listOf(
                AnswerOption("Yes, I have a noticeable gap", 0.20, "Uncommon", traitLabel = "Front tooth gap"),
                AnswerOption("Very slight one", 0.10, "Uncommon", traitLabel = "Slight tooth gap"),
                AnswerOption("No gap at all", 0.70, "Common", traitLabel = "No tooth gap"),
            ),
            funFact = "A front tooth gap is considered a sign of beauty and good luck in many West African and South Asian cultures. Supermodel Lauren Hutton and singer Madonna both famously embraced theirs!",
            source = "Journal of Clinical and Diagnostic Research, 2014"
        ),

        QuizQuestion(
            id = 20,
            category = QuizCategory.QUIRKS,
            text = "Are you colorblind or do you have trouble distinguishing certain colors?",
            explanation = "The most common type is red-green colorblindness, where reds and greens can look similar. It's much more common in men than women.",
            options = listOf(
                AnswerOption("Yes, I'm colorblind", 0.045, "Uncommon", traitLabel = "Color vision deficiency"),
                AnswerOption("I might be — some colors look the same to me", 0.02, "Uncommon", traitLabel = "Possible color blindness"),
                AnswerOption("No, I see all colors normally", 0.935, "Common", traitLabel = "Normal color vision"),
            ),
            funFact = "About 8% of men and 0.5% of women have some form of color vision deficiency. Most don't realize it until they take a test — they've just never seen certain colors the way others do!",
            source = "National Eye Institute (NEI), Colour Blind Awareness UK"
        ),
    )
}
