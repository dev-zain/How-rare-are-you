package com.devzain.howrareareyou.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.devzain.howrareareyou.data.QuestionBank
import com.devzain.howrareareyou.data.QuizQuestion
import com.devzain.howrareareyou.data.RarityCalculator
import com.devzain.howrareareyou.data.UserAnswer
import com.devzain.howrareareyou.ui.theme.*

/**
 * The quiz gameplay screen.
 *
 * Shows one question at a time with answer options. After picking
 * an answer, a fun fact pops up and then the user can proceed
 * to the next question. When all questions are done, we calculate
 * the rarity score and call onQuizComplete.
 *
 * State is managed locally with remember{} for simplicity.
 * If we need to survive process death later, we can migrate
 * to a ViewModel + SavedStateHandle — but for now this works fine
 * since nobody is going to leave the quiz mid-flow and expect it
 * to be saved when they come back hours later.
 */
@Composable
fun QuizScreen(
    onBackPressed: () -> Unit = {},
    onQuizComplete: (RarityCalculator.RarityResult, List<UserAnswer>) -> Unit = { _, _ -> },
) {
    val questions = remember { QuestionBank.getAllQuestions() }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var showFunFact by remember { mutableStateOf(false) }
    val userAnswers = remember { mutableStateListOf<UserAnswer>() }

    val currentQuestion = questions[currentIndex]
    val progress = (currentIndex + 1).toFloat() / questions.size.toFloat()

    // smooth animation for the progress bar
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(400),
        label = "progress"
    )

    // letter labels for answer options (A, B, C, D, E...)
    val optionLetters = listOf("A", "B", "C", "D", "E", "F", "G", "H",
        "I", "J", "K", "L")

    // colors for the letter badges — cycle through these
    val letterColors = listOf(
        Pair(CatSkills, AccentTeal),        // green
        Pair(CatLife, AccentBlue),           // blue
        Pair(CatPhysical, AccentOrange),     // orange
        Pair(CatQuirks, AccentPink),         // pink
        Pair(BrandPurpleBg, BrandPurple),    // purple
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceBg)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // === TOP HEADER with gradient ===
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(BrandPurple, BrandPurpleLight)
                        ),
                        shape = RoundedCornerShape(
                            bottomStart = 24.dp, bottomEnd = 24.dp
                        )
                    )
                    .padding(horizontal = 20.dp)
                    .padding(top = 48.dp, bottom = 20.dp)
            ) {
                // back arrow and question counter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // back button
                    Text(
                        text = "\u2190",
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { onBackPressed() }
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Question ${currentIndex + 1}/${questions.size}",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // category badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentQuestion.category.displayName,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // progress bar
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = AccentGold,
                    trackColor = Color.White.copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round,
                )
            }

            // === QUESTION CARD ===
            // this is the main content area — scrollable in case
            // a question has many answer options (like blood types)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp)
            ) {
                // question card with animation when question changes
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {
                        (slideInHorizontally { it } + fadeIn()) togetherWith
                                (slideOutHorizontally { -it } + fadeOut())
                    },
                    label = "questionAnim"
                ) { questionIdx ->
                    val question = questions[questionIdx]

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = SurfaceWhite
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            // question text
                            Text(
                                text = question.text,
                                color = TextDark,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                lineHeight = 26.sp,
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // answer options
                            question.options.forEachIndexed { idx, option ->
                                val isSelected = selectedOptionIndex == idx
                                val letterPair = letterColors[
                                    idx % letterColors.size
                                ]

                                AnswerOptionRow(
                                    letter = optionLetters[idx],
                                    text = option.text,
                                    label = option.label,
                                    isSelected = isSelected,
                                    letterBgColor = if (isSelected)
                                        AnswerSelectedBg else letterPair.first,
                                    letterTextColor = if (isSelected)
                                        AnswerSelected else letterPair.second,
                                    onClick = {
                                        selectedOptionIndex = idx
                                        showFunFact = true
                                    }
                                )

                                if (idx < question.options.size - 1) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // fun fact — slides in after selecting an answer
                AnimatedVisibility(
                    visible = showFunFact,
                    enter = fadeIn(tween(300)),
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = BrandPurpleBg
                        ),
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top,
                        ) {
                            // info icon
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(BrandPurple),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "i",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = currentQuestion.funFact,
                                color = TextMedium,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }

                // bottom padding so content doesn't hide behind button
                Spacer(modifier = Modifier.height(80.dp))
            }

            // === BOTTOM BUTTON ===
            // only visible after selecting an answer
            AnimatedVisibility(
                visible = selectedOptionIndex != null,
                enter = fadeIn(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceBg)
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(BrandPurple, BrandPurpleLight)
                                )
                            )
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                            ) {
                                // save the answer
                                val option = currentQuestion
                                    .options[selectedOptionIndex!!]
                                userAnswers.add(
                                    UserAnswer(
                                        questionId = currentQuestion.id,
                                        selectedIndex = selectedOptionIndex!!,
                                        probability = option.probability,
                                    )
                                )

                                if (currentIndex < questions.size - 1) {
                                    // move to next question
                                    currentIndex++
                                    selectedOptionIndex = null
                                    showFunFact = false
                                } else {
                                    // quiz is done — calculate the result
                                    val result = RarityCalculator
                                        .calculate(userAnswers.toList())
                                    onQuizComplete(result, userAnswers.toList())
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        val isLastQuestion = currentIndex == questions.size - 1
                        Text(
                            text = if (isLastQuestion)
                                "See My Rarity Score" else "Next Question  \u2192",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}

/**
 * A single answer option row with a colored letter badge,
 * the answer text, and a rarity label.
 */
@Composable
fun AnswerOptionRow(
    letter: String,
    text: String,
    label: String,
    isSelected: Boolean,
    letterBgColor: Color,
    letterTextColor: Color,
    onClick: () -> Unit,
) {
    val borderColor = if (isSelected) AnswerSelected else AnswerDefault
    val bgColor = if (isSelected) AnswerSelectedBg else SurfaceWhite

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(
                width = if (isSelected) 2.dp else 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .background(bgColor)
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // letter badge (A, B, C...)
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(letterBgColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = letter,
                color = letterTextColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // answer text
        Text(
            text = text,
            color = if (isSelected) AnswerSelected else TextDark,
            fontSize = 15.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold
                else FontWeight.Normal,
            modifier = Modifier.weight(1f),
        )

        // rarity label on the right
        if (label.isNotEmpty()) {
            Text(
                text = label,
                color = TextLight,
                fontSize = 11.sp,
            )
        }

        // checkmark for selected option
        if (isSelected) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(AnswerSelected),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "\u2713",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    HowrareAreYouTheme {
        QuizScreen()
    }
}
