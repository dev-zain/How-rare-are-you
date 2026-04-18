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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import com.devzain.howrareareyou.ui.components.AnimatedFingerprintIcon
import com.devzain.howrareareyou.ui.theme.*
import com.devzain.howrareareyou.utils.SoundManager

@Composable
fun WelcomeScreen(onStartQuiz: () -> Unit = {}) {
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBg) // #0A0710 deep black
    ) {
        // Glowing background elements
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.radialGradient(
                    colors = listOf(NeonPurple.copy(alpha=0.2f), Color.Transparent),
                    radius = 900f
                ))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 48.dp, bottom = 36.dp)
                ) {
                    AnimatedFingerprintIcon(size = 140.dp)

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "HOW RARE\nARE YOU?",
                        style = TextStyle(
                            brush = Brush.linearGradient(listOf(NeonCyan, NeonPink)),
                            fontSize = 42.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center,
                            lineHeight = 44.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Find out how statistically unique you\nreally are compared to the global population.",
                        color = Color.White.copy(alpha = 0.6f),
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
                    .clickable { 
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onStartQuiz() 
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NeonCardBg),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Brush.linearGradient(listOf(NeonPink, NeonPurple))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("?", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Discover Your Rarity", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("20 questions \u2022 2 min", color = TextMedium, fontSize = 13.sp)
                    }
                }
                
                // the neon button inside the card at bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Brush.linearGradient(listOf(NeonCyan, NeonPurple)))
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Start Test \u2192", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // how it works
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text("How it works", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(14.dp))
                StepCard("1", "Answer questions", "About your traits & habits", NeonCyan)
                Spacer(modifier = Modifier.height(10.dp))
                StepCard("2", "Get rarity score", "Calculated from global population data", NeonPink)
                Spacer(modifier = Modifier.height(10.dp))
                StepCard("3", "Share result", "Compare statistical uniqueness", NeonPurple)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "\uD83D\uDD12 Anonymous & Secure",
                color = TextMedium, fontSize = 12.sp,
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
        colors = CardDefaults.cardColors(containerColor = NeonCardBg),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(number, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = TextMedium, fontSize = 12.sp, lineHeight = 16.sp)
            }
        }
    }
}
