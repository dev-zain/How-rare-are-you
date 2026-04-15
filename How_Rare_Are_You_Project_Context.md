# "How Rare Are You?" — Complete Project Context Document

**Purpose of this document:** This is the full context for an Android app called "How Rare Are You?" being built by Zain (dev-zain on GitHub). This document should be provided at the start of a new chat so Claude has complete context to continue development from where we left off.

---

## 1. App Concept

"How Rare Are You?" is a statistical uniqueness quiz app for Android. Users answer 20 questions about their physical traits, abilities, life experiences, and quirks. The app calculates how statistically rare their combination of traits is using real population data and presents a "1 in X" rarity score. The result is displayed on a beautiful shareable card designed for Instagram Stories, TikTok, and WhatsApp — this share card is the primary viral growth mechanism.

**Tagline:** "Discover how statistically unique you are among 8 billion humans."

**Target audience:** Everyone aged 13+ (COPPA-safe). Global audience with special attention to South Asian and European markets.

**Revenue model:** Ads-based (Google AdMob) for v1. Freemium with country-based pricing for premium features in v1.2+.

**Psychological hooks:** Narcissism bias (people want to feel unique), social comparison (competing with friends on rarity scores), identity expression (sharing results that say something about who you are).

---

## 2. What Has Been Built So Far

### Completed screens:
1. **Welcome Screen** — Purple gradient header with animated radar/fingerprint icon, floating white CTA card ("Discover Your Rarity — 20 questions, 2 min"), category icons row (Physical, Skills, Life, Quirks), "How it works" step cards, and honest tagline "Based on real data from 8.1 billion people"
2. **Quiz Screen** — Purple gradient header with progress bar and question counter, question displayed in a white floating card, colorful A/B/C/D answer options with rarity labels ("Very rare", "Common", etc.), fun fact popup after each answer, "Next Question" button that changes to "See My Rarity Score" on question 20, slide animation between questions

### Completed backend/data:
1. **Data models** — QuizQuestion, AnswerOption, QuizCategory enum, UserAnswer
2. **Question bank** — All 20 questions with real statistical probabilities from WHO, census data, and scientific studies
3. **Rarity calculator** — Multiplies probabilities together, calculates "1 in X" number, percentile, and rarity tier (Common/Uncommon/Rare/Epic/Legendary/Mythic)
4. **Simple navigation** — State-based enum navigation between Welcome and Quiz screens

### NOT yet built (in order of priority):
1. **Result screen** — The dramatic score reveal with rarity number, percentile, tier badge, AI-generated description, and confetti animation
2. **Share card generator** — Programmatically creates a 1080x1920 image for Instagram Stories sharing
3. **Trait breakdown screen** — Shows which specific traits are rare vs common with visual bars
4. **AdMob integration** — Banner on result screen, interstitial before breakdown, rewarded for extra features
5. **GDPR consent dialog** — Required for EU users (developer is in Germany)
6. **Privacy policy** — Hosted on GitHub Pages or developer's existing site
7. **App icon** — Custom radar/fingerprint icon matching the in-app design
8. **Google Play Store listing** — Screenshots, feature graphic, description, data safety form

### Future versions (NOT for v1.0):
- Daily quiz (5 questions/day, same for all users, Wordle-style)
- Leaderboard (Firebase Firestore, only after 5K+ users)
- Push notifications for streaks
- Google Sign-In (optional, for saving progress)
- Premium features with country-based pricing
- iOS version

---

## 3. Technical Stack

### Core:
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** Simple state-based for now (will migrate to MVVM with ViewModel if needed)
- **Min SDK:** 24 (Android 7.0, covers 99% of devices)
- **Target SDK:** 36
- **Compile SDK:** 36

### Key versions from gradle (libs.versions.toml):
```
agp = "9.1.1"
kotlin = "2.2.10"
composeBom = "2026.02.01"
coreKtx = "1.10.1"
lifecycleRuntimeKtx = "2.6.1"
activityCompose = "1.8.0"
gradle = "9.3.1"
```

### build.gradle.kts (app level) uses:
- plugins: android.application, kotlin.compose
- namespace: com.devzain.howrareareyou
- applicationId: com.devzain.howrareareyou
- Compose BOM for dependency management
- No additional libraries added yet beyond the default Compose starter

### Libraries to add when needed:
- Konfetti (nl.dionsegijn:konfetti-compose:2.0.4) — confetti particle effects for score reveal
- Lottie Compose — animated illustrations (optional)
- Capturable (dev.shreyaspatil:capturable) — capture composables as bitmaps for share cards
- Firebase Analytics + Crashlytics — when ready for production
- Google AdMob SDK — for ad monetization
- Compose Navigation — if the simple enum-based nav becomes insufficient

### Project structure:
```
app/src/main/java/com/devzain/howrareareyou/
├── MainActivity.kt                    # Entry point, handles screen navigation
├── data/
│   ├── QuizQuestion.kt               # Data models (QuizQuestion, AnswerOption, QuizCategory, UserAnswer)
│   ├── QuestionBank.kt               # All 20 questions with probabilities and fun facts
│   └── RarityCalculator.kt           # Math engine for rarity score calculation
└── ui/
    ├── screens/
    │   ├── WelcomeScreen.kt           # Landing/home screen
    │   ├── QuizScreen.kt             # Quiz gameplay screen
    │   ├── ResultScreen.kt           # [NOT YET BUILT] Score reveal
    │   └── ShareScreen.kt            # [NOT YET BUILT] Share card
    ├── components/                    # [NOT YET CREATED] Reusable composables
    └── theme/
        ├── Color.kt                   # App color palette
        ├── Theme.kt                   # Material theme setup
        └── Type.kt                    # Typography styles
```

### GitHub repository:
- URL: https://github.com/dev-zain/How-rare-are-you
- Branch: main

### Development setup:
- IDE: Android Studio (latest stable)
- Test device: Samsung Galaxy S20 Ultra (SM-G988B), Android 13, connected via USB cable
- Developer's laptop: Lenovo ThinkPad X1 Carbon, i7 7th gen, 16GB RAM, Windows
- Note: C: drive has very limited space (~4GB). Android SDK, Gradle cache, and AVD should be on D: drive if possible
- Emulator is too slow on this hardware — use the physical Samsung for testing

---

## 4. Design System

### Design philosophy:
Light, colorful, modern design inspired by popular quiz apps like Quizzo, Queezy, and the viral "How Rare Are You?" TikTok quizzes. Purple is the primary brand color. White floating cards on a light purple-tinted background. The design should feel playful, premium, and Instagram-worthy.

### Color palette (defined in Color.kt):
```kotlin
// Brand colors
val BrandPurple = Color(0xFF7B5EE0)        // primary purple
val BrandPurpleLight = Color(0xFF9B7FEB)    // lighter purple for gradients
val BrandPurpleSoft = Color(0xFFC4A8FF)     // soft purple
val BrandPurpleBg = Color(0xFFF5F0FF)       // very light purple background

// Accent colors
val AccentTeal = Color(0xFF5DCAA5)
val AccentCoral = Color(0xFFFF6B6B)
val AccentGold = Color(0xFFFFD166)          // used for "Rare" text highlight
val AccentPink = Color(0xFFED93B1)
val AccentBlue = Color(0xFF378ADD)
val AccentOrange = Color(0xFFF7934C)

// Category backgrounds (pastel)
val CatPhysical = Color(0xFFFFF0E0)         // warm peach
val CatSkills = Color(0xFFE8F5E9)           // soft green
val CatLife = Color(0xFFE3F2FD)             // soft blue
val CatQuirks = Color(0xFFFCE4EC)           // soft pink

// Text hierarchy
val TextDark = Color(0xFF1A1A2E)            // primary text
val TextMedium = Color(0xFF555577)          // secondary text
val TextLight = Color(0xFF9999AA)           // muted text
val TextOnPurple = Color(0xFFFFFFFF)        // white text on purple

// Surfaces
val SurfaceWhite = Color(0xFFFFFFFF)
val SurfaceBg = Color(0xFFF8F6FF)           // main background (slight purple tint)
val SurfaceCard = Color(0xFFFFFFFF)
val SurfaceDivider = Color(0xFFF0EEF5)

// Answer states
val AnswerDefault = Color(0xFFF0EEF5)
val AnswerSelected = Color(0xFF7B5EE0)
val AnswerSelectedBg = Color(0xFFF5F0FF)

// Rarity tiers
val TierLegendary = Color(0xFFFFD166)
val TierEpic = Color(0xFFC4A8FF)
val TierRare = Color(0xFF5DCAA5)
val TierCommon = Color(0xFFB4B2A9)
```

### Theme setup:
- Always light mode (no dynamic colors, no dark theme toggle)
- Status bar: purple (matches gradient header) with white icons
- Navigation bar: light background with dark icons
- Custom light color scheme using the brand purple as primary

### Key design patterns used:
- Purple gradient headers with rounded bottom corners (bottomStart = 32.dp, bottomEnd = 32.dp)
- White cards that float over the gradient using negative offset (offset(y = (-28).dp))
- Rounded corner shapes: 20.dp for cards, 14-16.dp for buttons, 12.dp for badges
- Card elevation: 8.dp for the main CTA card, 2-4.dp for other cards
- Progress bars with gold (AccentGold) fill on translucent white track
- Answer options with colored letter badges (A, B, C, D) and border that turns purple when selected
- Fun fact cards with purple-tinted background and "i" icon

### Typography:
- Using default system font (FontFamily.Default) for now
- Can swap to Poppins or another Google Font later
- Bold (700) for titles, SemiBold (600) for headings, Medium (500) for labels, Normal (400) for body

### Animation:
- Pulsing glow on the welcome screen icon (infiniteRepeatable, 2500ms, LinearEasing)
- Smooth progress bar fill (animateFloatAsState, tween 400ms)
- Slide + fade transition between quiz questions (AnimatedContent, slideInHorizontally + fadeIn)
- Fade-in for fun fact card (AnimatedVisibility, fadeIn tween 300ms)

---

## 5. The 20 Quiz Questions

All questions with their answer options and probabilities are in QuestionBank.kt. Here's the summary:

### Physical Traits (Q1-Q5):
1. Handedness — Right (89%), Left (10%), Both (1%)
2. Eye color — Brown (79%), Blue (8%), Hazel (8%), Green (2%), Gray (3%)
3. Blood type — O+ (38%), A+ (27%), B+ (22%), AB+ (5%), O- (4%), A- (2%), B- (1%), AB- (0.6%)
4. Hair color — Black (75%), Brown (11%), Blonde (2%), Red (1.3%), Other (10.7%)
5. Tongue rolling — Yes (70%), No (30%)

### Skills (Q6-Q10):
6. Ear wiggling — Yes (22%), One ear (8%), No (70%)
7. Languages spoken — 1 (40%), 2 (43%), 3 (13%), 4+ (3%)
8. Backflip — Yes (5%), No (95%)
9. Perfect pitch — Confirmed (0.01%), Think so (1%), No (98.99%)
10. One eyebrow raise — Either (15%), One side (15%), No (70%)

### Life (Q11-Q15):
11. Birth month — ~8.1-8.8% each month
12. Leap day birth — Yes (0.068%), No (99.932%)
13. Twin/triplet — Single (96.6%), Twin (3.3%), Triplet+ (0.1%)
14. Countries visited — 0-5 (65%), 6-15 (25%), 16-30 (7%), 31+ (3%)
15. Broken bones — Never (50%), Once (30%), 2+ (20%)

### Quirks (Q16-Q20):
16. Diastema (tooth gap) — Yes (25%), No (75%)
17. Double-jointed — Yes (15%), Maybe (10%), No (75%)
18. Finger snapping both hands — Both (60%), One (25%), Can't (15%)
19. Hitchhiker's thumb — Yes (25%), Slightly (35%), No (40%)
20. Struck by lightning — Yes (0.003%), No (99.997%)

---

## 6. Rarity Score Calculation

The math is straightforward:
```
Combined Probability = P1 × P2 × P3 × ... × P20
One In X = 1 / Combined Probability
Percentile = (1 - Combined Probability) × 100
```

### Rarity tiers:
- Common: below 50th percentile
- Uncommon: 50-80th percentile
- Rare: 80-95th percentile
- Epic: 95-99th percentile
- Legendary: 99-99.9th percentile
- Mythic (One of a Kind): above 99.9th percentile

The calculator also tracks which single trait was the user's rarest (for highlighting on the breakdown screen).

---

## 7. Google Play Store Requirements

### Account:
- $25 one-time developer registration fee
- Identity verification with government ID
- Developer account can be registered from Pakistan (revenue depends on where USERS are, not where developer is)
- Bank account must match the developer account holder's name

### New developer requirement (critical):
- Must complete a "closed testing" phase with 20 real testers
- Testers must install and use the app for 14 consecutive days minimum
- After that, developer can apply for production (public) access
- Start recruiting testers NOW (classmates, friends, family with Android phones)

### Required for submission:
- Privacy policy URL (can host on GitHub Pages)
- Data safety form (declare AdMob data collection honestly)
- Content rating questionnaire (will get "Everyone" or "Teen")
- GDPR consent dialog for EU users (use Google's UMP SDK)
- App icon: 512×512 PNG
- Feature graphic: 1024×500 PNG
- Screenshots: minimum 2, recommended 6-8, size 1080×1920
- Short description: max 80 characters
- Full description: max 4000 characters
- Target age: 13+ (avoid under-13 to skip COPPA)

### Google Play commission:
- 15% for developers under $1M annual revenue (your situation)
- This only applies to in-app purchases/subscriptions, NOT ad revenue
- AdMob has its own split (developer keeps ~68%)
- No way to legally bypass Google Play Billing for digital goods on Android

---

## 8. Monetization Plan

### v1.0 (ads only):
- Banner ad on result screen (bottom)
- Interstitial ad before trait breakdown screen
- Rewarded video ad to unlock extra question packs
- NEVER show ads during the quiz flow (kills completion rate)
- Maximum 1 interstitial per session

### v1.2+ (freemium):
- "Remove Ads" one-time purchase: $2.99 (US), ₹99 (India), PKR 299 (Pakistan)
- Premium monthly: $3.99/mo (US), ₹99/mo (India)
- Premium features: detailed rarity reports, custom share cards, unlimited retakes, comparison mode
- Google Play Console allows setting different prices per country

### AdMob eCPM estimates by region:
- US rewarded video: $10-16
- Germany rewarded video: $15-30
- India rewarded video: $0.60-1.50
- Pakistan rewarded video: $0.35-0.60

---

## 9. Architecture Scaling Plan

### Phase 1 (0-10K users) — current:
- Everything runs on-device, no server needed
- Firebase free tier for analytics
- $0/month infrastructure cost

### Phase 2 (10K-500K users):
- Firebase Blaze (pay-as-you-go) for Firestore leaderboard
- Cloud Functions for daily quiz delivery
- Firebase Remote Config for feature flags
- Firebase Cloud Messaging for push notifications
- $20-80/month

### Phase 3 (500K+ users):
- Dedicated API server (Cloud Run or Railway)
- Redis cache for leaderboard
- CDN for pre-rendered share cards
- Ad mediation (AppLovin MAX)
- RevenueCat for IAP management
- $200-1000/month (but revenue should be $5K-30K+)

---

## 10. Code Style Guidelines

### Writing style:
- Write code in a natural, human style — not overly formal or AI-generated looking
- Use casual but informative comments that explain WHY, not WHAT
- Don't comment every single line — only add comments where the reasoning isn't obvious
- Use TODO comments for future work (e.g., "// TODO: replace with proper vector icons")
- Variable names should be clear and self-documenting
- Avoid unnecessarily clever or abstract patterns — keep it readable

### Comment examples (good):
```kotlin
// using a custom light scheme instead of dynamic colors
// because we want consistent branding across all devices

// gentle pulsing animation for the icon glow

// this card sits on top of the gradient header (negative offset)
// giving that nice overlapping effect

// only show the button after user picks an answer

// TODO: hook up navigation to quiz screen
```

### Comment examples (bad — too AI-like, avoid these):
```kotlin
// This function initializes the primary user interface components
// and establishes the foundational layout structure for optimal
// user engagement and interaction paradigms

// Leveraging Jetpack Compose's declarative UI framework to
// construct a visually appealing and responsive interface
```

### Architecture:
- KISS principle — don't over-engineer for v1
- Simple state-based navigation (enum + when) instead of full nav graph
- Local state with remember{} instead of ViewModel for quiz flow
- Can migrate to ViewModel + SavedStateHandle later if needed
- Single-activity architecture
- Composable functions for each screen in the ui/screens package
- Reusable composables in ui/components package
- Data models and business logic in data package

---

## 11. Key Design Decisions Already Made

1. **Light mode default** (not dark) — based on the quiz app reference designs Zain preferred
2. **No user login for v1** — let users play immediately, add optional sign-in later
3. **No leaderboard for v1** — looks empty/sad with few users, add at 5K+ users
4. **No daily quiz for v1** — retention feature, not acquisition feature
5. **All quiz data is offline/hardcoded** — no API calls, no server, no database for v1
6. **Honest social proof** — "Based on real data from 8.1 billion people" instead of fake user counts
7. **Share card is the #1 priority** — this IS the marketing strategy
8. **13+ age target** — avoids COPPA complexity
9. **Purple as brand color** — tested well in quiz apps, stands out on social media
10. **"Rare" highlighted in gold** in the title — draws attention to the key word

---

## 12. What to Build Next (In Order)

When continuing development, build these screens in this exact order:

### Next up: Result Screen
- Dramatic loading animation ("Analyzing your traits...", "Comparing to 8 billion people...")
- Hold for 3-4 seconds even though calculation is instant (builds anticipation)
- Large rarity number: "1 in 2,380,952"
- Percentile: "Rarer than 99.97% of humans"
- Rarity tier badge with color (Legendary = gold, Epic = purple, etc.)
- AI-generated personality description based on trait combination
- Confetti animation (Konfetti library) on reveal
- Two CTA buttons: "Share My Rarity" (primary) and "See Breakdown" (secondary)
- Banner ad at the bottom

### Then: Share Card Screen
- Beautiful gradient card (1080×1920 for Instagram Stories)
- Shows rarity score, percentile, top 3 rarest traits
- "Can you beat my rarity?" challenge text
- App branding with download prompt at bottom
- One-tap share to WhatsApp, Instagram, TikTok, Twitter/X
- Use Capturable library to render composable as bitmap

### Then: Trait Breakdown Screen
- List of all 20 answered traits
- Each trait shows: the trait name, global percentage, visual bar (green=common, red=rare)
- Highlight the rarest trait with a star badge
- "Retake Quiz" button at the bottom
- Interstitial ad shown before this screen

### Then: Polish & Production
- Integrate Google AdMob SDK
- Add GDPR consent dialog (Google UMP SDK)
- Create app icon (fingerprint/radar design in purple)
- Write privacy policy
- Create Play Store listing assets
- Set up Firebase Analytics + Crashlytics

---

## 13. Important Notes for the Next Chat

- The developer (Zain) is a Master's student in Software Engineering at University of Hildesheim, Germany, originally from Pakistan
- This is his FIRST Android app — explain things clearly, don't assume deep Android knowledge
- His laptop has limited C: drive space (~4GB) — SDK and caches should be on D: drive
- He tests on a Samsung Galaxy S20 Ultra via USB cable (not emulator)
- He uses an iPhone personally so he cannot test Android apps on his own phone
- He has a GitHub account: dev-zain, repo: How-rare-are-you
- The app should be written to pass Google Play review on first submission
- All code should look human-written with natural comments — not AI-generated
- He prefers the light/colorful design over dark mode
- He wants to earn money through this app via ads and eventually premium features
- He needs 20 testers for Google Play closed testing requirement

---

## 14. Files Currently in the Project

Here is every file and its current content status:

| File | Status | Description |
|------|--------|-------------|
| MainActivity.kt | DONE | Screen navigation with enum |
| data/QuizQuestion.kt | DONE | Data models |
| data/QuestionBank.kt | DONE | All 20 questions |
| data/RarityCalculator.kt | DONE | Score math engine |
| ui/screens/WelcomeScreen.kt | DONE | Home screen |
| ui/screens/QuizScreen.kt | DONE | Quiz gameplay |
| ui/screens/ResultScreen.kt | NOT BUILT | Next to build |
| ui/screens/ShareScreen.kt | NOT BUILT | After result |
| ui/screens/BreakdownScreen.kt | NOT BUILT | After share |
| ui/theme/Color.kt | DONE | Full color palette |
| ui/theme/Theme.kt | DONE | Light theme setup |
| ui/theme/Type.kt | DONE | Typography |
| res/values/strings.xml | DONE | App name set |
| AndroidManifest.xml | DONE | Default config |
| build.gradle.kts (app) | DONE | Default dependencies |
| gradle/libs.versions.toml | DONE | Version catalog |

---


