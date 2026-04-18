package com.devzain.howrareareyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.devzain.howrareareyou.data.RarityCalculator
import com.devzain.howrareareyou.data.UserAnswer
import com.devzain.howrareareyou.ui.screens.QuizScreen
import com.devzain.howrareareyou.ui.screens.ResultScreen
import com.devzain.howrareareyou.ui.screens.ShareScreen
import com.devzain.howrareareyou.ui.screens.WelcomeScreen
import com.devzain.howrareareyou.ui.theme.HowrareAreYouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HowrareAreYouTheme {
                var screen by remember { mutableStateOf(Screen.WELCOME) }
                var quizResult by remember { mutableStateOf<RarityCalculator.RarityResult?>(null) }
                var quizAnswers by remember { mutableStateOf<List<UserAnswer>>(emptyList()) }
                var skipResultLoading by remember { mutableStateOf(false) }

                when (screen) {
                    Screen.WELCOME -> {
                        WelcomeScreen(
                            onStartQuiz = { screen = Screen.QUIZ }
                        )
                    }
                    Screen.QUIZ -> {
                        QuizScreen(
                            onBackPressed = { screen = Screen.WELCOME },
                            onQuizComplete = { result, answers ->
                                quizResult = result
                                quizAnswers = answers
                                skipResultLoading = false
                                screen = Screen.RESULT
                            }
                        )
                    }
                    Screen.RESULT -> {
                        quizResult?.let { result ->
                            ResultScreen(
                                result = result,
                                answers = quizAnswers,
                                skipLoading = skipResultLoading,
                                onShareClick = {
                                    skipResultLoading = true
                                    screen = Screen.SHARE
                                },
                                onRetakeClick = {
                                    quizResult = null
                                    quizAnswers = emptyList()
                                    screen = Screen.QUIZ
                                },
                                onHomeClick = {
                                    quizResult = null
                                    quizAnswers = emptyList()
                                    screen = Screen.WELCOME
                                }
                            )
                        }
                    }
                    Screen.SHARE -> {
                        quizResult?.let { result ->
                            ShareScreen(
                                result = result,
                                answers = quizAnswers,
                                onBackClick = {
                                    screen = Screen.RESULT
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class Screen { WELCOME, QUIZ, RESULT, SHARE }
