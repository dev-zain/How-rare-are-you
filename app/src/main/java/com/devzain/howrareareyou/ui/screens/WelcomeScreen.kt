package com.devzain.howrareareyou.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devzain.howrareareyou.ui.components.AnimatedFingerprintIcon
import com.devzain.howrareareyou.ui.theme.*

@Composable
fun WelcomeScreen(onStartQuiz: () -> Unit = {}) {
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
            // purple gradient header — height bumped from 340 to 380
            // so the subtitle text doesn't bleed into the CTA card below
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .background(
                        brush = Brush.verticalGradient(listOf(BrandPurple, BrandPurpleLight)),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 48.dp, bottom = 36.dp)
                ) {
                    // the animated fingerprint icon
                    AnimatedFingerprintIcon(size = 100.dp)

                    Spacer(modifier = Modifier.height(20.dp))

                    // title with "Rare" highlighted in gold
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) { append("How ") }
                            withStyle(SpanStyle(color = AccentGold, fontWeight = FontWeight.ExtraBold)) { append("Rare") }
                            withStyle(SpanStyle(color = Color.White)) { append("\nAre You?") }
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

            // floating CTA card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-28).dp)
                    .clickable { onStartQuiz() },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Brush.linearGradient(listOf(AccentGold, AccentOrange))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("?", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Discover Your Rarity", color = TextDark, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("20 questions \u2022 2 min", color = TextLight, fontSize = 13.sp)
                    }
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(BrandPurple).padding(horizontal = 18.dp, vertical = 10.dp)
                    ) {
                        Text("Start", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // how it works
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text("How it works", color = TextDark, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(14.dp))
                StepCard("1", "Answer 20 questions", "About your traits, skills, and life experiences", BrandPurple)
                Spacer(modifier = Modifier.height(10.dp))
                StepCard("2", "Get your rarity score", "Calculated from real global population data", AccentTeal)
                Spacer(modifier = Modifier.height(10.dp))
                StepCard("3", "Share with friends", "Challenge them to beat your uniqueness", AccentOrange)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Based on real data from 8.1 billion people",
                color = TextLight, fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StepCard(number: String, title: String, subtitle: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(number, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(title, color = TextDark, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = TextLight, fontSize = 12.sp, lineHeight = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() { HowrareAreYouTheme { WelcomeScreen() } }
