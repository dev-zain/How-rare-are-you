package com.devzain.howrareareyou.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devzain.howrareareyou.data.*
import com.devzain.howrareareyou.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * The big reveal screen after completing all 20 questions.
 *
 * Flow: loading phase (dramatic text changes) → score reveal with
 * animated ring + counter → personalized description → top rarest
 * traits → full Q&A breakdown → share/retake buttons.
 *
 * This screen is designed to be screenshot-worthy because
 * that's literally how the app spreads.
 */
@Composable
fun ResultScreen(
    result: RarityCalculator.RarityResult,
    answers: List<UserAnswer>,
    onShareClick: () -> Unit = {},
    onRetakeClick: () -> Unit = {},
) {
    // loading state — show "analyzing" for a few seconds for drama
    var isLoading by remember { mutableStateOf(true) }
    var loadingText by remember { mutableStateOf("Analyzing your traits...") }
    var loadingProgress by remember { mutableFloatStateOf(0f) }

    // cycle through loading messages
    LaunchedEffect(Unit) {
        loadingProgress = 0.15f
        delay(800)
        loadingText = "Scanning 8 billion profiles..."
        loadingProgress = 0.35f
        delay(800)
        loadingText = "Comparing your traits..."
        loadingProgress = 0.55f
        delay(700)
        loadingText = "Calculating rarity score..."
        loadingProgress = 0.78f
        delay(600)
        loadingText = "Almost there..."
        loadingProgress = 0.95f
        delay(500)
        loadingProgress = 1.0f
        delay(300)
        isLoading = false
    }

    if (isLoading) {
        LoadingPhase(loadingText, loadingProgress)
    } else {
        RevealPhase(result, answers, onShareClick, onRetakeClick)
    }
}

/**
 * The "analyzing" loading screen with progress bar and spinning animation.
 * Even though the calculation is instant, this builds anticipation.
 */
@Composable
private fun LoadingPhase(text: String, progress: Float) {
    val anim = rememberInfiniteTransition(label = "loading")
    val rotation by anim.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing)),
        label = "spin"
    )
    val pulse by anim.animateFloat(
        initialValue = 0.6f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse),
        label = "pulse"
    )
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "loadBar"
    )

    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(BrandPurple, BrandPurpleLight))
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            // spinning ring
            Canvas(modifier = Modifier.size(80.dp)) {
                val center = Offset(size.width / 2, size.height / 2)
                drawArc(
                    color = Color.White.copy(alpha = 0.2f),
                    startAngle = 0f, sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 4f),
                    size = size * 0.85f,
                    topLeft = Offset(size.width * 0.075f, size.height * 0.075f),
                )
                drawArc(
                    color = Color.White,
                    startAngle = rotation, sweepAngle = 90f,
                    useCenter = false,
                    style = Stroke(width = 4f),
                    size = size * 0.85f,
                    topLeft = Offset(size.width * 0.075f, size.height * 0.075f),
                )
                drawCircle(color = Color.White, radius = 6f, center = center)
            }

            Spacer(Modifier.height(32.dp))

            // loading text that changes
            AnimatedContent(
                targetState = text,
                transitionSpec = { fadeIn(tween(400)) togetherWith fadeOut(tween(300)) },
                label = "loadText"
            ) { msg ->
                Text(
                    msg, color = Color.White.copy(alpha = pulse),
                    fontSize = 16.sp, fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(24.dp))

            // progress bar
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = AccentGold,
                trackColor = Color.White.copy(alpha = 0.15f),
                strokeCap = StrokeCap.Round,
            )
        }
    }
}

/**
 * The actual result reveal with all the good stuff.
 */
@Composable
private fun RevealPhase(
    result: RarityCalculator.RarityResult,
    answers: List<UserAnswer>,
    onShareClick: () -> Unit,
    onRetakeClick: () -> Unit,
) {
    val questions = remember { QuestionBank.getAllQuestions() }
    val description = remember { ResultGenerator.generateDescription(answers, result) }
    val topTraits = remember { ResultGenerator.getTopRarestTraits(answers) }
    val tierColor = getTierColor(result.tier)

    // animate the score counting up from 0
    var animationPlayed by remember { mutableStateOf(false) }
    val animatedPercentile by animateFloatAsState(
        targetValue = if (animationPlayed) result.percentile.toFloat() else 0f,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "percentile"
    )
    // ring fill animation
    val ringProgress by animateFloatAsState(
        targetValue = if (animationPlayed) (result.percentile / 100f).toFloat() else 0f,
        animationSpec = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
        label = "ring"
    )
    // fade in the rest of the content after the number finishes
    val contentAlpha by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(durationMillis = 800, delayMillis = 1500),
        label = "contentFade"
    )
    // tier badge pop-in
    val badgeScale by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "badgeScale"
    )

    LaunchedEffect(Unit) {
        // tiny delay for the "reveal" feeling
        delay(100)
        animationPlayed = true
    }

    // confetti particles
    val particles = remember {
        List(50) {
            ConfettiParticle(
                x = Random.nextFloat(),
                speed = Random.nextFloat() * 2 + 1,
                size = Random.nextFloat() * 7 + 3,
                color = listOf(AccentGold, BrandPurpleSoft, AccentPink, AccentTeal, AccentCoral).random(),
                delay = Random.nextInt(2000),
                rotation = Random.nextFloat() * 360,
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(SurfaceBg)) {
        // confetti layer
        ConfettiOverlay(particles, animationPlayed)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // gradient header area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(listOf(BrandPurple, BrandPurpleLight)),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(top = 56.dp, bottom = 40.dp)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // "Your Rarity Score" label
                    Text(
                        "Your Rarity Score",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(Modifier.height(20.dp))

                    // animated ring with score inside
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
                        // background ring track
                        Canvas(modifier = Modifier.size(185.dp)) {
                            // outer glow
                            drawArc(
                                color = AccentGold.copy(alpha = 0.1f),
                                startAngle = -90f, sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = 20f),
                            )
                            // track
                            drawArc(
                                color = Color.White.copy(alpha = 0.15f),
                                startAngle = -90f, sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = 12f, cap = StrokeCap.Round),
                            )
                            // filled arc
                            drawArc(
                                color = AccentGold,
                                startAngle = -90f,
                                sweepAngle = 360f * ringProgress,
                                useCenter = false,
                                style = Stroke(width = 12f, cap = StrokeCap.Round),
                            )
                        }

                        // score number inside the ring
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "1 in",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp,
                            )
                            Text(
                                RarityCalculator.formatOneInX(result.oneInX),
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                "people",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 12.sp,
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // percentile with animated counter
                    Text("Rarer than", color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp)
                    Text(
                        "%.2f%%".format(animatedPercentile),
                        color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.Bold,
                    )
                    Text("of all humans", color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp)

                    Spacer(Modifier.height(14.dp))

                    // tier badge with bounce animation
                    Box(
                        modifier = Modifier
                            .scale(badgeScale)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        tierColor.copy(alpha = 0.3f),
                                        tierColor.copy(alpha = 0.15f)
                                    )
                                )
                            )
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text(
                            "${getTierEmoji(result.tier)} ${result.tier.label} Rarity",
                            color = tierColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            // below the header — description, traits, breakdown
            Column(
                modifier = Modifier.padding(horizontal = 24.dp).alpha(contentAlpha)
            ) {
                Spacer(Modifier.height(20.dp))

                // personalized description card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(BrandPurple.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("\uD83E\uDDE0", fontSize = 16.sp) // brain emoji
                            }
                            Spacer(Modifier.width(10.dp))
                            Text("About Your Rarity", color = TextDark, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            description, color = TextMedium, fontSize = 14.sp,
                            lineHeight = 22.sp,
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))

                // top rarest traits
                if (topTraits.isNotEmpty()) {
                    Text(
                        "\u2B50 Your Rarest Traits",
                        color = TextDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.height(10.dp))

                    topTraits.forEachIndexed { idx, (name, pct) ->
                        val traitColor = when (idx) {
                            0 -> AccentGold
                            1 -> BrandPurpleSoft
                            else -> AccentTeal
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                            elevation = CardDefaults.cardElevation(2.dp),
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // rank badge
                                    Box(
                                        modifier = Modifier.size(36.dp).clip(CircleShape)
                                            .background(traitColor.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            if (idx == 0) "\u2B50" else "#${idx + 1}",
                                            fontSize = if (idx == 0) 16.sp else 13.sp,
                                            color = if (idx == 0) AccentOrange else BrandPurple,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(name, color = TextDark, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                        Text(
                                            "Only %.1f%% of people".format(pct),
                                            color = TextLight, fontSize = 12.sp,
                                        )
                                    }
                                }
                                // visual rarity bar
                                Spacer(Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(SurfaceDivider)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = (pct / 100f).toFloat().coerceIn(0.02f, 1f))
                                            .fillMaxHeight()
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(traitColor)
                                    )
                                }
                            }
                        }
                        if (idx < topTraits.size - 1) Spacer(Modifier.height(8.dp))
                    }
                }

                Spacer(Modifier.height(22.dp))

                // ====== FULL Q&A BREAKDOWN ======
                Text(
                    "\uD83D\uDCCB Your Full Breakdown",
                    color = TextDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    "Every question and what you answered",
                    color = TextLight,
                    fontSize = 12.sp,
                )
                Spacer(Modifier.height(12.dp))

                answers.forEachIndexed { idx, answer ->
                    // find the matching question
                    val question = questions.find { it.id == answer.questionId }
                    if (question != null) {
                        val rarityLevel = when {
                            answer.probability <= 0.01 -> "Extremely Rare"
                            answer.probability <= 0.05 -> "Very Rare"
                            answer.probability <= 0.15 -> "Rare"
                            answer.probability <= 0.30 -> "Uncommon"
                            answer.probability < 1.0 -> "Common"
                            else -> "Neutral"
                        }
                        val rarityColor = when {
                            answer.probability <= 0.01 -> AccentCoral
                            answer.probability <= 0.05 -> AccentOrange
                            answer.probability <= 0.15 -> AccentGold
                            answer.probability <= 0.30 -> AccentTeal
                            else -> TextLight
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                            elevation = CardDefaults.cardElevation(1.dp),
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                // question number + category
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(BrandPurple.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            "${idx + 1}",
                                            color = BrandPurple,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        question.text,
                                        color = TextDark,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f),
                                        lineHeight = 18.sp,
                                    )
                                }

                                Spacer(Modifier.height(8.dp))

                                // user's answer + rarity
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(rarityColor.copy(alpha = 0.08f))
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        answer.traitName,
                                        color = TextDark,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f),
                                    )
                                    // rarity badge
                                    if (answer.probability < 1.0) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(rarityColor.copy(alpha = 0.15f))
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Text(
                                                rarityLevel,
                                                color = rarityColor,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(6.dp))
                    }
                }

                Spacer(Modifier.height(24.dp))

                // share button
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(54.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.horizontalGradient(listOf(BrandPurple, BrandPurpleLight)))
                        .clickable { onShareClick() },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "Share My Rarity  \u2192",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Spacer(Modifier.height(12.dp))

                // retake button
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(BrandPurple.copy(alpha = 0.08f))
                        .clickable { onRetakeClick() },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Retake Quiz", color = BrandPurple, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

// simple confetti particle data
private data class ConfettiParticle(
    val x: Float, val speed: Float, val size: Float,
    val color: Color, val delay: Int, val rotation: Float,
)

/**
 * Colorful confetti dots falling down the screen on reveal.
 * Simple but effective — can swap for Konfetti library later.
 */
@Composable
private fun ConfettiOverlay(particles: List<ConfettiParticle>, animate: Boolean) {
    val anim = rememberInfiniteTransition(label = "confetti")
    val time by anim.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "confettiTime"
    )

    if (!animate) return

    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { p ->
            val y = ((time * p.speed + p.x) % 1.2f) * size.height
            val x = p.x * size.width
            // fade out as they approach the bottom
            val alpha = when {
                y > size.height * 0.85f -> 0.15f
                y < size.height * 0.1f -> 0.3f
                else -> 0.65f
            }
            drawCircle(
                color = p.color.copy(alpha = alpha),
                radius = p.size,
                center = Offset(x, y),
            )
        }
    }
}

private fun getTierColor(tier: RarityCalculator.RarityTier): Color {
    return when (tier) {
        RarityCalculator.RarityTier.MYTHIC -> TierMythic
        RarityCalculator.RarityTier.LEGENDARY -> TierLegendary
        RarityCalculator.RarityTier.EPIC -> TierEpic
        RarityCalculator.RarityTier.RARE -> TierRare
        RarityCalculator.RarityTier.UNCOMMON -> TierUncommon
        RarityCalculator.RarityTier.COMMON -> TierCommon
    }
}

private fun getTierEmoji(tier: RarityCalculator.RarityTier): String {
    return when (tier) {
        RarityCalculator.RarityTier.MYTHIC -> "\uD83C\uDF1F"     // 🌟
        RarityCalculator.RarityTier.LEGENDARY -> "\uD83D\uDD25"  // 🔥
        RarityCalculator.RarityTier.EPIC -> "\uD83D\uDC8E"       // 💎
        RarityCalculator.RarityTier.RARE -> "\u2728"              // ✨
        RarityCalculator.RarityTier.UNCOMMON -> "\uD83D\uDCA0"   // 💠
        RarityCalculator.RarityTier.COMMON -> "\uD83D\uDD35"     // 🔵
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPreview() {
    HowrareAreYouTheme {
        ResultScreen(
            result = RarityCalculator.RarityResult(
                combinedProbability = 0.0000004,
                oneInX = 2380952,
                percentile = 87.5,
                tier = RarityCalculator.RarityTier.EPIC,
                rarestTraitIndex = 2,
                rarityScore = 12.5,
            ),
            answers = listOf(
                UserAnswer(1, 1, 0.10, "Left-handed"),
                UserAnswer(2, 5, 0.02, "Green eyes"),
                UserAnswer(3, 7, 0.006, "AB\u2212 blood"),
            ),
        )
    }
}
