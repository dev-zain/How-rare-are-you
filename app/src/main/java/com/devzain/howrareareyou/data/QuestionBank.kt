package com.devzain.howrareareyou.data

/**
 * All 20 quiz questions with real-world probabilities.
 *
 * Each question has a `shortName` (e.g., "Eye Color") used on result/share cards.
 * Each answer option has:
 * - `traitLabel`: short name for the result card (e.g., "Green eyes")
 * - `resultNote`: brief explanation shown in the result breakdown
 * Data sources are cited per question.
 */
object QuestionBank {

    fun getAllQuestions(): List<QuizQuestion> = questions

    private val questions = listOf(

        // ===== PHYSICAL TRAITS (Q1-Q5) =====

        QuizQuestion(
            id = 1,
            category = QuizCategory.PHYSICAL,
            shortName = "Handedness",
            text = "Which hand do you write with?",
            options = listOf(
                AnswerOption("Right hand", 0.89, "Most common",
                    traitLabel = "Right-handed",
                    resultNote = "Nine out of ten people write with their right hand \u2014 you're in the vast majority"),
                AnswerOption("Left hand", 0.10, "Uncommon",
                    traitLabel = "Left-handed",
                    resultNote = "Lefties are just 10% of people \u2014 a club shared with Da Vinci, Einstein, and Obama!"),
                AnswerOption("I use both hands equally", 0.01, "Very rare",
                    traitLabel = "Ambidextrous",
                    resultNote = "True ambidexterity is incredibly rare \u2014 fewer than 1 in 100 people can use both hands equally"),
            ),
            funFact = "Only about 10% of people are left-handed. Famous lefties include Leonardo da Vinci, Albert Einstein, and Barack Obama!",
            source = "Papadatou-Pastou et al., 2020 \u2014 Psychological Bulletin"
        ),

        QuizQuestion(
            id = 2,
            category = QuizCategory.PHYSICAL,
            shortName = "Eye Color",
            text = "What is your natural eye color?",
            explanation = "Pick the closest match. If your eyes change color in different lighting, pick what they look like most of the time.",
            options = listOf(
                AnswerOption("Brown or dark brown", 0.79, "Most common worldwide",
                    traitLabel = "Brown eyes",
                    resultNote = "Brown is the most common eye color worldwide, shared by nearly 4 in 5 people"),
                AnswerOption("Blue", 0.08, "Uncommon globally",
                    traitLabel = "Blue eyes",
                    resultNote = "All blue-eyed people share a single common ancestor from roughly 10,000 years ago"),
                AnswerOption("Hazel (brown-green mix)", 0.05, "Uncommon",
                    traitLabel = "Hazel eyes",
                    resultNote = "Hazel eyes can appear to shift between brown and green in different lighting \u2014 only 5% have them"),
                AnswerOption("Amber (golden/yellowish)", 0.03, "Rare",
                    traitLabel = "Amber eyes",
                    resultNote = "Amber eyes have a striking golden-yellow hue \u2014 found in only about 3% of people worldwide"),
                AnswerOption("Gray", 0.03, "Rare",
                    traitLabel = "Gray eyes",
                    resultNote = "True gray eyes are often mistaken for blue or green \u2014 one of the rarest eye colors in the world"),
                AnswerOption("Green", 0.02, "Very rare",
                    traitLabel = "Green eyes",
                    resultNote = "The rarest natural eye color on Earth \u2014 only about 2% of the world's population has green eyes"),
            ),
            funFact = "Green is the rarest natural eye color \u2014 only about 2% of the world has it. All blue-eyed people share a single common ancestor from around 10,000 years ago!",
            source = "World Atlas, American Academy of Ophthalmology"
        ),

        QuizQuestion(
            id = 3,
            category = QuizCategory.PHYSICAL,
            shortName = "Blood Type",
            text = "What is your blood type?",
            explanation = "You can find this on medical records, blood donation cards, or some health apps. If you don't know, pick \"I don't know\" \u2014 we won't count it against your rarity.",
            options = listOf(
                AnswerOption("O+", 0.38, "Most common",
                    traitLabel = "O+ blood type",
                    resultNote = "The most common blood type globally and the one blood banks always need the most"),
                AnswerOption("A+", 0.27, "Common",
                    traitLabel = "A+ blood type",
                    resultNote = "The second most common blood type, carried by about 1 in every 4 people"),
                AnswerOption("B+", 0.22, "Common",
                    traitLabel = "B+ blood type",
                    resultNote = "Relatively common worldwide, especially prevalent in South and Southeast Asian populations"),
                AnswerOption("AB+", 0.05, "Uncommon",
                    traitLabel = "AB+ blood type",
                    resultNote = "The universal plasma donor \u2014 only about 1 in 20 people have AB+ blood"),
                AnswerOption("O\u2212", 0.04, "Uncommon",
                    traitLabel = "O\u2212 blood type",
                    resultNote = "The universal red cell donor \u2014 critical for emergencies but only 4% of people carry it"),
                AnswerOption("A\u2212", 0.02, "Rare",
                    traitLabel = "A\u2212 blood type",
                    resultNote = "Quite rare \u2014 only about 1 in 50 people worldwide have A\u2212 blood"),
                AnswerOption("B\u2212", 0.01, "Very rare",
                    traitLabel = "B\u2212 blood type",
                    resultNote = "One of the rarest blood types \u2014 found in just about 1 in every 100 people worldwide"),
                AnswerOption("AB\u2212", 0.006, "Extremely rare",
                    traitLabel = "AB\u2212 blood type",
                    resultNote = "The rarest blood type on Earth \u2014 fewer than 1 in 150 people carry AB\u2212"),
                // probability = 1.0 means neutral — doesn't affect the rarity score
                AnswerOption("I don't know my blood type", 1.0, "",
                    traitLabel = "",
                    resultNote = ""),
            ),
            funFact = "AB\u2212 is the rarest blood type \u2014 less than 1% of people have it. Meanwhile, O\u2212 is the universal donor that hospitals always need!",
            source = "American Red Cross, Stanford Blood Center"
        ),

        QuizQuestion(
            id = 4,
            category = QuizCategory.PHYSICAL,
            shortName = "Hair Color",
            text = "What is your natural hair color?",
            explanation = "Your hair color before any dye or coloring. The color you were born with or had as a teenager.",
            options = listOf(
                AnswerOption("Black", 0.75, "Most common",
                    traitLabel = "Black hair",
                    resultNote = "Black hair is by far the most common hair color globally \u2014 about 3 in 4 people"),
                AnswerOption("Brown (any shade)", 0.11, "Common",
                    traitLabel = "Brown hair",
                    resultNote = "Brown hair is common in Europe and the Americas but relatively uncommon on a global scale"),
                AnswerOption("Blonde / Light", 0.02, "Uncommon",
                    traitLabel = "Natural blonde hair",
                    resultNote = "Natural blondes are surprisingly rare worldwide \u2014 only about 2% of the global population"),
                AnswerOption("Red or Auburn", 0.013, "Very rare",
                    traitLabel = "Natural red hair",
                    resultNote = "Natural red hair is found in just 1-2% of people \u2014 Scotland has the highest rate at 13%!"),
                AnswerOption("Gray or White (premature)", 0.007, "Very rare",
                    traitLabel = "Premature gray hair",
                    resultNote = "Premature graying before age 20 is quite rare and largely determined by your genetics"),
            ),
            funFact = "Only 1-2% of people worldwide are natural redheads. Scotland has the highest percentage at about 13% of its population.",
            source = "National Institutes of Health (NIH), ScotlandsDNA Project"
        ),

        QuizQuestion(
            id = 5,
            category = QuizCategory.PHYSICAL,
            shortName = "Tongue Rolling",
            text = "Can you roll your tongue into a tube shape?",
            explanation = "Stick out your tongue and try to curl the sides upward to make a U-shape or tube. Some people can do it easily, others can't at all.",
            options = listOf(
                AnswerOption("Yes, I can do it easily", 0.70, "Common",
                    traitLabel = "Can roll tongue",
                    resultNote = "About 7 in 10 people can roll their tongue \u2014 once thought purely genetic, but environment plays a role too"),
                AnswerOption("No, I've tried and I can't", 0.30, "Uncommon",
                    traitLabel = "Can't roll tongue",
                    resultNote = "About 3 in 10 people can't roll their tongue no matter how hard they try"),
            ),
            funFact = "About 70% of people can roll their tongue. Scientists used to think it was purely genetic, but twin studies showed environment plays a role too!",
            source = "Sturtevant 1940, McDonald 2010 \u2014 Journal of Heredity"
        ),

        // ===== ABILITIES (Q6-Q10) =====

        QuizQuestion(
            id = 6,
            category = QuizCategory.SKILLS,
            shortName = "Ear Wiggling",
            text = "Can you wiggle your ears without using your hands?",
            explanation = "Try to move your ears up and down or back and forth using just your ear muscles \u2014 no touching allowed!",
            options = listOf(
                AnswerOption("Yes, both ears", 0.18, "Uncommon",
                    traitLabel = "Can wiggle both ears",
                    resultNote = "Only about 18% of people can wiggle both ears \u2014 you've got control of vestigial muscles!"),
                AnswerOption("Only one ear", 0.04, "Rare",
                    traitLabel = "Can wiggle one ear",
                    resultNote = "Wiggling just one ear is even rarer than both \u2014 only about 4% of people can do this"),
                AnswerOption("No, they don't budge", 0.78, "Common",
                    traitLabel = "Can't wiggle ears",
                    resultNote = "Most people's ear muscles are vestigial leftovers \u2014 they exist but can't be voluntarily controlled"),
            ),
            funFact = "Ear wiggling uses vestigial muscles that most humans have but few can control. Our evolutionary ancestors used them to turn their ears toward sounds \u2014 like cats still do!",
            source = "Hackley, 2015 \u2014 Psychophysiology journal"
        ),

        QuizQuestion(
            id = 7,
            category = QuizCategory.SKILLS,
            shortName = "Languages Spoken",
            text = "How many languages can you hold a real conversation in?",
            explanation = "Count languages you can genuinely talk to someone in. Include your native language, foreign languages, and regional dialects/languages (like Punjabi, Pashto, Urdu, etc)!",
            options = listOf(
                AnswerOption("1 language", 0.40, "Common",
                    traitLabel = "Monolingual",
                    resultNote = "About 40% of people speak just one language \u2014 most common in the US, UK, and Australia"),
                AnswerOption("2 languages", 0.43, "Common",
                    traitLabel = "Bilingual",
                    resultNote = "Being bilingual is actually the global norm \u2014 about 43% of the world speaks two languages"),
                AnswerOption("3 languages", 0.13, "Uncommon",
                    traitLabel = "Trilingual",
                    resultNote = "Trilingual speakers make up just 13% of the world \u2014 that's genuinely impressive!"),
                AnswerOption("4 or more languages", 0.03, "Very rare",
                    traitLabel = "Polyglot (4+ languages)",
                    resultNote = "Speaking 4+ languages fluently puts you in an elite 3% of the global population"),
            ),
            funFact = "Only about 3% of the world speaks 4+ languages fluently. The most multilingual person documented, Ziad Fazah, claimed to speak 59 languages!",
            source = "European Commission Eurobarometer, Ethnologue"
        ),

        QuizQuestion(
            id = 8,
            category = QuizCategory.SKILLS,
            shortName = "Backflip",
            text = "Can you do a backflip?",
            explanation = "A full backward somersault \u2014 standing or from a running start. No trampoline, that doesn't count!",
            options = listOf(
                AnswerOption("Yes, I can do it on flat ground", 0.03, "Very rare",
                    traitLabel = "Can do a standing backflip",
                    resultNote = "Only about 3% of people can do a standing backflip \u2014 true athleticism and courage"),
                AnswerOption("Only on a trampoline or into water", 0.07, "Uncommon",
                    traitLabel = "Trampoline backflipper",
                    resultNote = "Backflipping on a trampoline still takes guts that most people don't have"),
                AnswerOption("I used to but not anymore", 0.05, "Uncommon",
                    traitLabel = "Former backflipper",
                    resultNote = "Former backflippers still earned the skill \u2014 that's more than most people can say"),
                AnswerOption("No, never", 0.85, "Common",
                    traitLabel = "Can't do a backflip",
                    resultNote = "85% of people have never done a backflip \u2014 it takes serious training and nerve"),
            ),
            funFact = "Only about 3-5% of people can do a standing backflip. Gymnasts, martial artists, and parkour athletes make it look easy, but it requires serious explosive power and courage!",
            source = "Gymnastics Federation estimates, sports physiology research"
        ),

        QuizQuestion(
            id = 9,
            category = QuizCategory.SKILLS,
            shortName = "Sun Sneezer",
            text = "Do you sneeze when you suddenly look at bright sunlight?",
            explanation = "This is called the 'Photic Sneeze Reflex'. It usually happens when stepping out of a dark room into bright sunshine.",
            options = listOf(
                AnswerOption("Yes, almost always", 0.25, "Uncommon",
                    traitLabel = "Sun Sneezer",
                    resultNote = "Having the Photic Sneeze Reflex means your visual and sneezing nerves are genetically crossed!"),
                AnswerOption("Only sometimes", 0.10, "Uncommon",
                    traitLabel = "Occasional Sun Sneezer",
                    resultNote = "A mild photic sneeze reflex is an uncommon but fascinating genetic trait"),
                AnswerOption("No, the sun doesn't make me sneeze", 0.65, "Common",
                    traitLabel = "No Sun Sneeze Reflex",
                    resultNote = "Most people do not have their visual pathways crossed with their sneeze reflex"),
            ),
            funFact = "It's officially called the ACHOO syndrome (Autosomal Dominant Compelling Helio-Ophthalmic Outburst). It's a genetic trait where bright light overstimulates the optic nerve and accidentally triggers the sneeze nerve!",
            source = "Scientific American, Medical Genetics Research"
        ),

        QuizQuestion(
            id = 10,
            category = QuizCategory.SKILLS,
            shortName = "Eyebrow Control",
            text = "Can you raise just one eyebrow while keeping the other one still?",
            options = listOf(
                AnswerOption("Yes, I can raise either one", 0.10, "Uncommon",
                    traitLabel = "Can raise either eyebrow",
                    resultNote = "Raising either eyebrow independently takes impressive muscle control \u2014 only 10% can do it"),
                AnswerOption("Yes, but only one side works", 0.20, "Somewhat uncommon",
                    traitLabel = "Can raise one eyebrow",
                    resultNote = "The famous 'Rock eyebrow' \u2014 about 20% of people can raise just one side"),
                AnswerOption("No, both eyebrows always move together", 0.70, "Common",
                    traitLabel = "Can't raise one eyebrow",
                    resultNote = "For about 70% of people, both eyebrows move together \u2014 it's how our muscles are wired"),
            ),
            funFact = "About 30% of people can raise one eyebrow independently. Dwayne 'The Rock' Johnson made it his signature move \u2014 but most people genuinely can't do it!",
            source = "Freitas-Magalh\u00e3es, 2012 \u2014 Facial expression studies"
        ),

        // ===== LIFE & BACKGROUND (Q11-Q15) =====

        QuizQuestion(
            id = 11,
            category = QuizCategory.LIFE,
            shortName = "Leap Day Birth",
            text = "Were you born on February 29th (a leap day)?",
            options = listOf(
                AnswerOption("Yes, I'm a leap day baby!", 0.00068, "Extremely rare",
                    traitLabel = "Born on leap day",
                    resultNote = "Only about 5 million people alive today share your Feb 29th birthday \u2014 truly special!"),
                AnswerOption("No", 0.99932, "Common",
                    traitLabel = "Not a leap day baby",
                    resultNote = "You share a non\u2011leap\u2011day birthday with 99.9% of the world's population"),
            ),
            funFact = "Only about 5 million people alive today were born on Feb 29th \u2014 that's roughly 1 in 1,461 people. They're called \"leaplings\" and technically only have a birthday every 4 years!",
            source = "Calendar mathematics \u2014 1/1461 probability"
        ),

        QuizQuestion(
            id = 12,
            category = QuizCategory.LIFE,
            shortName = "Multiple Birth",
            text = "Are you a twin, triplet, or part of a multiple birth?",
            options = listOf(
                AnswerOption("No, I was born alone", 0.966, "Common",
                    traitLabel = "Single birth",
                    resultNote = "Single births are the overwhelming norm \u2014 about 96.6% of all people"),
                AnswerOption("Yes, I have a twin", 0.033, "Uncommon",
                    traitLabel = "Twin",
                    resultNote = "Twins make up just 3.3% of all births \u2014 the rate has risen 70% since the 1980s"),
                AnswerOption("Triplet or more", 0.001, "Very rare",
                    traitLabel = "Triplet or higher",
                    resultNote = "Triplets or more occur in roughly 1 in 1,000 births \u2014 incredibly rare"),
            ),
            funFact = "The twin birth rate has risen 70% since the 1980s, partly due to fertility treatments. Nigeria has the world's highest natural twin rate \u2014 about 4.5% of births!",
            source = "CDC National Vital Statistics, 2021"
        ),

        QuizQuestion(
            id = 13,
            category = QuizCategory.LIFE,
            shortName = "Countries Visited",
            text = "How many foreign countries have you visited?",
            explanation = "Count any country outside of your home country that you've physically been to, even for a layover.",
            options = listOf(
                AnswerOption("None, just my home country", 0.35, "Very common",
                    traitLabel = "Never traveled abroad",
                    resultNote = "About 35% of people have never traveled outside their home country"),
                AnswerOption("1 to 4 foreign countries", 0.30, "Common",
                    traitLabel = "Visited 1-4 foreign countries",
                    resultNote = "Visiting a handful of countries is a fairly common travel experience worldwide"),
                AnswerOption("5 to 14 foreign countries", 0.20, "Above average",
                    traitLabel = "Visited 5-14 foreign countries",
                    resultNote = "You've explored more of the world than about 80% of people \u2014 nicely done!"),
                AnswerOption("15 to 29 foreign countries", 0.09, "Uncommon",
                    traitLabel = "Visited 15-29 foreign countries",
                    resultNote = "You're a well-traveled person \u2014 only about 1 in 11 people have visited this many foreign countries"),
                AnswerOption("30 to 49 foreign countries", 0.04, "Rare",
                    traitLabel = "World traveler (30-49)",
                    resultNote = "Serious globetrotter status \u2014 just 4% of people have been to this many countries"),
                AnswerOption("50+ foreign countries", 0.02, "Very rare",
                    traitLabel = "Globe trotter (50+)",
                    resultNote = "World explorer! Fewer than 2% of people have visited 50+ of the world's 195 countries"),
            ),
            funFact = "The average person visits about 7 countries in their lifetime. There are 195 countries in the world \u2014 only a handful of people have visited them all!",
            source = "Hostelworld Travel Survey, 2023; UN Tourism data"
        ),

        QuizQuestion(
            id = 14,
            category = QuizCategory.LIFE,
            shortName = "Broken Bones",
            text = "Have you ever broken a bone in your body?",
            options = listOf(
                AnswerOption("Never broken anything", 0.50, "Common",
                    traitLabel = "Never broken a bone",
                    resultNote = "About half of all people go their entire life without ever breaking a single bone"),
                AnswerOption("Yes, broke one bone", 0.30, "Common",
                    traitLabel = "Broke one bone",
                    resultNote = "One break is a fairly common life experience \u2014 the collarbone is the most common fracture"),
                AnswerOption("Yes, broke 2 or more bones", 0.20, "Uncommon",
                    traitLabel = "Broke 2+ bones",
                    resultNote = "Breaking 2+ bones is less common \u2014 only about 1 in 5 people have been through that"),
            ),
            funFact = "About half of all people go through their entire life without ever breaking a bone. The collarbone is the most commonly fractured bone in the human body.",
            source = "NHS UK, American Orthopaedic Association"
        ),

        QuizQuestion(
            id = 15,
            category = QuizCategory.LIFE,
            shortName = "Chronotype",
            text = "Are you naturally a morning person or a night owl?",
            explanation = "Think about when you feel most alert and productive \u2014 not what your work schedule forces you to do.",
            options = listOf(
                AnswerOption("Definite morning person \u2014 I love early starts", 0.25, "Uncommon",
                    traitLabel = "Early bird",
                    resultNote = "True early birds make up about 25% of people \u2014 your sleep preference is partly in your DNA"),
                AnswerOption("Somewhere in the middle", 0.50, "Common",
                    traitLabel = "Mixed chronotype",
                    resultNote = "Most people (about half) fall somewhere in between \u2014 not extreme morning or night"),
                AnswerOption("Night owl \u2014 I come alive after dark", 0.25, "Uncommon",
                    traitLabel = "Night owl",
                    resultNote = "Night owls are about 25% of the population \u2014 your body clock naturally peaks later"),
            ),
            funFact = "Your sleep preference (chronotype) is roughly 50% genetic. True extreme morning people ('larks') and extreme night owls each make up only about 25% of the population.",
            source = "Roenneberg et al., 2007 \u2014 Current Biology; 23andMe research"
        ),

        // ===== QUIRKS (Q16-Q20) =====

        QuizQuestion(
            id = 16,
            category = QuizCategory.QUIRKS,
            shortName = "Dimples",
            text = "Do you have dimples when you smile?",
            explanation = "Dimples are the small dents or indentations that appear on some people's cheeks when they smile.",
            options = listOf(
                AnswerOption("Yes, on both cheeks", 0.15, "Uncommon",
                    traitLabel = "Dimples on both cheeks",
                    resultNote = "Dimples are caused by a facial muscle variation \u2014 only about 15% have them on both cheeks"),
                AnswerOption("Yes, but only on one side", 0.05, "Rare",
                    traitLabel = "Single dimple",
                    resultNote = "A single dimple is rarer than having two \u2014 just about 5% of people have a unilateral dimple"),
                AnswerOption("No dimples", 0.80, "Common",
                    traitLabel = "No dimples",
                    resultNote = "80% of people don't have dimples \u2014 they require a specific genetic muscle variation"),
            ),
            funFact = "Dimples are actually caused by a variation in the facial muscle called the zygomaticus major. They're considered attractive in many cultures and are one of the few 'defects' people find charming!",
            source = "Pessa et al., 1998 \u2014 Plastic and Reconstructive Surgery"
        ),

        QuizQuestion(
            id = 17,
            category = QuizCategory.QUIRKS,
            shortName = "Eye Crossing",
            text = "Can you intentionally cross your eyes?",
            explanation = "Try to make both eyes point toward your nose at the same time \u2014 like looking at the tip of your own nose.",
            options = listOf(
                AnswerOption("Yes, easily", 0.70, "Common",
                    traitLabel = "Can cross eyes",
                    resultNote = "Most people can cross their eyes on command \u2014 it's called voluntary convergence"),
                AnswerOption("Only a little bit", 0.15, "Somewhat common",
                    traitLabel = "Partially cross eyes",
                    resultNote = "Partial eye crossing is moderately common \u2014 about 15% of people fall in this range"),
                AnswerOption("No, I can't do it at all", 0.15, "Uncommon",
                    traitLabel = "Can't cross eyes",
                    resultNote = "About 15% of people genuinely can't cross their eyes \u2014 it requires specific muscle control"),
            ),
            funFact = "Most people can cross their eyes (it's called voluntary convergence), but about 15% of people lack the muscle control to do it. Don't worry \u2014 it won't make them get stuck that way!",
            source = "American Association for Pediatric Ophthalmology"
        ),

        QuizQuestion(
            id = 18,
            category = QuizCategory.QUIRKS,
            shortName = "Joint Flexibility",
            text = "Are you \"double-jointed\" or unusually flexible?",
            explanation = "Sometimes called 'double-jointed.' Can you bend your thumb backward to touch your wrist? Bend your fingers way back? Do the splits easily?",
            options = listOf(
                AnswerOption("Yes, I'm noticeably flexible", 0.15, "Uncommon",
                    traitLabel = "Hypermobile joints",
                    resultNote = "Hypermobile joints affect about 15% of people \u2014 it's more common in women"),
                AnswerOption("A little more flexible than average", 0.10, "Somewhat uncommon",
                    traitLabel = "Slightly flexible",
                    resultNote = "Slightly above-average flexibility is found in about 1 in every 10 people"),
                AnswerOption("No, pretty normal flexibility", 0.75, "Common",
                    traitLabel = "Normal flexibility",
                    resultNote = "Normal joint flexibility is the standard \u2014 shared by about 3 in 4 people"),
            ),
            funFact = "Joint hypermobility is more common in women and in people of Asian and African descent. About 10-25% of people have some degree of it, but true hypermobility syndrome is much rarer.",
            source = "Beighton et al. \u2014 hypermobility scoring system; Arthritis Research UK"
        ),

        QuizQuestion(
            id = 19,
            category = QuizCategory.QUIRKS,
            shortName = "Tooth Gap",
            text = "Do you have a gap between your two front teeth?",
            explanation = "Dentists call this a 'diastema.' It's the visible space between your upper front teeth. Even a small gap counts.",
            options = listOf(
                AnswerOption("Yes, I have a noticeable gap", 0.20, "Uncommon",
                    traitLabel = "Front tooth gap",
                    resultNote = "A diastema is considered a sign of beauty and good luck in many cultures around the world"),
                AnswerOption("Very slight one", 0.10, "Uncommon",
                    traitLabel = "Slight tooth gap",
                    resultNote = "A subtle front tooth gap is found in about 10% of people \u2014 often seen as charming"),
                AnswerOption("No gap at all", 0.70, "Common",
                    traitLabel = "No tooth gap",
                    resultNote = "About 70% of people have no visible gap between their front teeth"),
            ),
            funFact = "A front tooth gap is considered a sign of beauty and good luck in many West African and South Asian cultures. Supermodel Lauren Hutton and singer Madonna both famously embraced theirs!",
            source = "Journal of Clinical and Diagnostic Research, 2014"
        ),

        QuizQuestion(
            id = 20,
            category = QuizCategory.QUIRKS,
            shortName = "Color Vision",
            text = "Are you colorblind or do you have trouble distinguishing certain colors?",
            explanation = "The most common type is red-green colorblindness, where reds and greens can look similar. It's much more common in men than women.",
            options = listOf(
                AnswerOption("Yes, I'm colorblind", 0.045, "Uncommon",
                    traitLabel = "Color vision deficiency",
                    resultNote = "Color vision deficiency affects about 8% of men but only 0.5% of women \u2014 it's genetic"),
                AnswerOption("I might be \u2014 some colors look the same to me", 0.02, "Uncommon",
                    traitLabel = "Possible color blindness",
                    resultNote = "Many people with mild color blindness don't realize it until they take a clinical test"),
                AnswerOption("No, I see all colors normally", 0.935, "Common",
                    traitLabel = "Normal color vision",
                    resultNote = "Standard trichromatic color vision \u2014 shared by about 93% of the population"),
            ),
            funFact = "About 8% of men and 0.5% of women have some form of color vision deficiency. Most don't realize it until they take a test \u2014 they've just never seen certain colors the way others do!",
            source = "National Eye Institute (NEI), Colour Blind Awareness UK"
        ),
    )
}
