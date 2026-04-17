package com.devzain.howrareareyou.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devzain.howrareareyou.data.*
import com.devzain.howrareareyou.ui.theme.*

/**
 * The quiz gameplay screen. One question at a time with animations.
 *
 * Supports going back to previous questions and changing answers.
 * The user's answers are stored in a mutable map keyed by question index,
 * so going back and forward preserves + allows editing previous picks.
 */
@Composable
fun QuizScreen(
    onBackPressed: () -> Unit = {},
    onQuizComplete: (RarityCalculator.RarityResult, List<UserAnswer>) -> Unit = { _, _ -> },
) {
    val questions = remember { QuestionBank.getAllQuestions() }
    var currentIndex by remember { mutableIntStateOf(0) }
    var showFunFact by remember { mutableStateOf(false) }

    // store answers keyed by question index so we can go back and edit
    val savedAnswers = remember { mutableStateMapOf<Int, Int>() }

    // the currently selected option for the visible question
    val selectedOptionIndex = savedAnswers[currentIndex]

    val currentQuestion = questions[currentIndex]
    val progress = (currentIndex + 1f) / questions.size

    // track navigation direction for slide animation
    var goingForward by remember { mutableStateOf(true) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress, animationSpec = tween(400), label = "progress"
    )

    val letters = listOf("A","B","C","D","E","F","G","H","I","J","K","L")
    val letterStyles = listOf(
        Pair(CatSkills, AccentTeal),
        Pair(CatLife, AccentBlue),
        Pair(CatPhysical, AccentOrange),
        Pair(CatQuirks, AccentPink),
        Pair(BrandPurpleBg, BrandPurple),
    )

    Box(modifier = Modifier.fillMaxSize().background(SurfaceBg)) {
        Column(modifier = Modifier.fillMaxSize()) {

            // header with gradient
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(listOf(BrandPurple, BrandPurpleLight)),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(horizontal = 20.dp)
                    .padding(top = 48.dp, bottom = 20.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    // back arrow — goes to previous question, or exits quiz on Q1
                    Text(
                        "\u2190", color = Color.White, fontSize = 22.sp,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                if (currentIndex > 0) {
                                    goingForward = false
                                    currentIndex--
                                    showFunFact = savedAnswers.containsKey(currentIndex)
                                } else {
                                    onBackPressed()
                                }
                            }
                            .padding(4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Text("Question ${currentIndex + 1}/${questions.size}", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.2f)).padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(currentQuestion.category.displayName, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    }
                }
                Spacer(Modifier.height(14.dp))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = AccentGold,
                    trackColor = Color.White.copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round,
                )
            }

            // scrollable question area
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp).padding(top = 20.dp)
            ) {
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {
                        if (goingForward) {
                            (slideInHorizontally { it } + fadeIn()) togetherWith (slideOutHorizontally { -it } + fadeOut())
                        } else {
                            (slideInHorizontally { -it } + fadeIn()) togetherWith (slideOutHorizontally { it } + fadeOut())
                        }
                    },
                    label = "questionAnim"
                ) { qIdx ->
                    val q = questions[qIdx]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    ) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                q.text, color = TextDark, fontSize = 19.sp,
                                fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, lineHeight = 26.sp,
                            )

                            // explanation for tricky terms
                            if (q.explanation.isNotEmpty()) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    q.explanation,
                                    color = TextLight,
                                    fontSize = 12.sp,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 17.sp,
                                )
                            }

                            Spacer(Modifier.height(20.dp))

                            // answer options
                            q.options.forEachIndexed { idx, option ->
                                val isSelected = selectedOptionIndex == idx
                                val style = letterStyles[idx % letterStyles.size]

                                AnswerRow(
                                    letter = letters[idx],
                                    text = option.text,
                                    label = option.label,
                                    isSelected = isSelected,
                                    letterBg = if (isSelected) AnswerSelectedBg else style.first,
                                    letterColor = if (isSelected) AnswerSelected else style.second,
                                    onClick = {
                                        savedAnswers[currentIndex] = idx
                                        showFunFact = true
                                    }
                                )
                                if (idx < q.options.size - 1) Spacer(Modifier.height(10.dp))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // fun fact + source
                AnimatedVisibility(visible = showFunFact, enter = fadeIn(tween(300))) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = BrandPurpleBg),
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.Top) {
                                Box(
                                    modifier = Modifier.size(32.dp).clip(RoundedCornerShape(10.dp)).background(BrandPurple),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("i", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
                                }
                                Spacer(Modifier.width(12.dp))
                                Text(currentQuestion.funFact, color = TextMedium, fontSize = 13.sp, lineHeight = 18.sp, modifier = Modifier.weight(1f))
                            }
                            // source citation
                            Spacer(Modifier.height(6.dp))
                            Text(
                                "Source: ${currentQuestion.source}",
                                color = TextLight, fontSize = 10.sp, fontStyle = FontStyle.Italic,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(80.dp))
            }

            // next question button — always visible once an answer is selected
            AnimatedVisibility(visible = selectedOptionIndex != null, enter = fadeIn()) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(SurfaceBg).padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().height(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Brush.horizontalGradient(listOf(BrandPurple, BrandPurpleLight)))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                goingForward = true

                                if (currentIndex < questions.size - 1) {
                                    currentIndex++
                                    // show fun fact if we already answered this question
                                    showFunFact = savedAnswers.containsKey(currentIndex)
                                } else {
                                    // build the final answer list from our saved map
                                    val userAnswers = questions.mapIndexedNotNull { idx, q ->
                                        savedAnswers[idx]?.let { optIdx ->
                                            val option = q.options[optIdx]
                                            UserAnswer(
                                                questionId = q.id,
                                                selectedIndex = optIdx,
                                                probability = option.probability,
                                                // use the descriptive traitLabel if available, otherwise fall back to the answer text
                                                traitName = option.traitLabel.ifEmpty { option.text },
                                                questionShortName = q.shortName,
                                            )
                                        }
                                    }
                                    val result = RarityCalculator.calculate(userAnswers)
                                    onQuizComplete(result, userAnswers)
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        val isLast = currentIndex == questions.size - 1
                        Text(
                            if (isLast) "See My Rarity Score" else "Next Question  \u2192",
                            color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerRow(letter: String, text: String, label: String, isSelected: Boolean, letterBg: Color, letterColor: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(if (isSelected) 2.dp else 1.5.dp, if (isSelected) AnswerSelected else AnswerDefault, RoundedCornerShape(14.dp))
            .background(if (isSelected) AnswerSelectedBg else SurfaceWhite)
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(letterBg), contentAlignment = Alignment.Center) {
            Text(letter, color = letterColor, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.width(12.dp))
        Text(text, color = if (isSelected) AnswerSelected else TextDark, fontSize = 15.sp, fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal, modifier = Modifier.weight(1f))
        if (label.isNotEmpty()) Text(label, color = TextLight, fontSize = 11.sp)
        if (isSelected) {
            Spacer(Modifier.width(8.dp))
            Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(AnswerSelected), contentAlignment = Alignment.Center) {
                Text("\u2713", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizPreview() { HowrareAreYouTheme { QuizScreen() } }
