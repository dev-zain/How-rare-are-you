package com.devzain.howrareareyou.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devzain.howrareareyou.ui.theme.*

/**
 * Welcome/landing screen — first thing users see when they open the app.
 *
 * Design structure:
 * - gradient purple header with animated radar icon and title
 * - floating CTA card that overlaps the header (start quiz)
 * - category icons row
 * - "how it works" step cards
 * - honest tagline at the bottom
 */
@Composable
fun WelcomeScreen(
    onStartQuiz: () -> Unit = {}
) {
    // gentle breathing animation for the icon glow
    val pulse = rememberInfiniteTransition(label = "iconPulse")
    val glowRadius by pulse.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // === GRADIENT HEADER ===
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(BrandPurple, BrandPurpleLight)
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = 32.dp, bottomEnd = 32.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 48.dp)
                ) {
                    // animated icon with pulsing glow
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(100.dp)
                    ) {
                        // outer glow effect
                        Canvas(
                            modifier = Modifier
                                .size((90 * glowRadius).dp)
                                .alpha(0.2f)
                        ) {
                            drawCircle(
                                color = Color.White,
                                radius = size.minDimension / 2
                            )
                        }

                        // main icon circle
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            // radar/fingerprint arcs
                            Canvas(modifier = Modifier.size(32.dp)) {
                                val sw = 2.5f
                                drawArc(
                                    color = Color.White,
                                    startAngle = -50f,
                                    sweepAngle = 280f,
                                    useCenter = false,
                                    style = Stroke(width = sw),
                                    size = size * 0.9f,
                                    topLeft = Offset(
                                        size.width * 0.05f,
                                        size.height * 0.05f
                                    )
                                )
                                drawArc(
                                    color = Color.White.copy(alpha = 0.85f),
                                    startAngle = -30f,
                                    sweepAngle = 240f,
                                    useCenter = false,
                                    style = Stroke(width = sw),
                                    size = size * 0.6f,
                                    topLeft = Offset(
                                        size.width * 0.2f,
                                        size.height * 0.2f
                                    )
                                )
                                drawArc(
                                    color = Color.White.copy(alpha = 0.7f),
                                    startAngle = -10f,
                                    sweepAngle = 200f,
                                    useCenter = false,
                                    style = Stroke(width = sw),
                                    size = size * 0.35f,
                                    topLeft = Offset(
                                        size.width * 0.325f,
                                        size.height * 0.325f
                                    )
                                )
                                drawCircle(
                                    color = Color.White,
                                    radius = 3f,
                                    center = Offset(
                                        size.width / 2,
                                        size.height / 2
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // app title
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append("How ")
                            }
                            withStyle(
                                SpanStyle(
                                    color = AccentGold,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            ) {
                                append("Rare")
                            }
                            withStyle(SpanStyle(color = Color.White)) {
                                append("\nAre You?")
                            }
                        },
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 38.sp,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Find out how statistically unique you\nreally are compared to the world.",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                    )
                }
            }

            // === FLOATING CTA CARD ===
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-28).dp)
                    .clickable { onStartQuiz() },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceWhite
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(AccentGold, AccentOrange)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "?",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Discover Your Rarity",
                            color = TextDark,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "20 questions \u2022 2 min",
                            color = TextLight,
                            fontSize = 13.sp,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(BrandPurple)
                            .padding(
                                horizontal = 18.dp,
                                vertical = 10.dp
                            )
                    ) {
                        Text(
                            text = "Start",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            // === CATEGORIES ===
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Categories",
                    color = TextDark,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CategoryItem("Physical", CatPhysical, AccentOrange)
                    CategoryItem("Skills", CatSkills, AccentTeal)
                    CategoryItem("Life", CatLife, AccentBlue)
                    CategoryItem("Quirks", CatQuirks, AccentPink)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // === HOW IT WORKS ===
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "How it works",
                    color = TextDark,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(14.dp))
                StepCard("1", "Answer 20 questions",
                    "About your traits, skills, and life experiences",
                    BrandPurple)
                Spacer(modifier = Modifier.height(10.dp))
                StepCard("2", "Get your rarity score",
                    "Based on real global population statistics",
                    AccentTeal)
                Spacer(modifier = Modifier.height(10.dp))
                StepCard("3", "Share with friends",
                    "Challenge them to beat your uniqueness",
                    AccentOrange)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // === BOTTOM TAGLINE ===
            // honest version — no fake user counts
            Text(
                text = "Based on real data from 8.1 billion people",
                color = TextLight,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// reusable category circle with icon placeholder
@Composable
fun CategoryItem(label: String, bgColor: Color, iconColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(24.dp)) {
                val c = Offset(size.width / 2, size.height / 2)
                drawCircle(color = iconColor, radius = 4f, center = c)
                drawCircle(
                    color = iconColor, radius = size.minDimension / 2,
                    center = c, style = Stroke(width = 2f)
                )
                drawCircle(
                    color = iconColor.copy(alpha = 0.5f),
                    radius = size.minDimension / 3,
                    center = c, style = Stroke(width = 1.5f)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, color = TextMedium, fontSize = 12.sp)
    }
}

// step card for the "how it works" section
@Composable
fun StepCard(number: String, title: String, subtitle: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number, color = color,
                    fontSize = 16.sp, fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = title, color = TextDark,
                    fontSize = 15.sp, fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = subtitle, color = TextLight,
                    fontSize = 12.sp, lineHeight = 16.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    HowrareAreYouTheme { WelcomeScreen() }
}
