package com.devzain.howrareareyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.devzain.howrareareyou.data.RarityCalculator
import com.devzain.howrareareyou.data.UserAnswer
import com.devzain.howrareareyou.ui.screens.QuizScreen
import com.devzain.howrareareyou.ui.screens.WelcomeScreen
import com.devzain.howrareareyou.ui.theme.HowrareAreYouTheme

/**
 * Single-activity architecture.
 * Using a simple state enum for navigation right now.
 * Will switch to compose navigation once we have more screens
 * (result, share, breakdown, etc.)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HowrareAreYouTheme {
                // which screen are we showing
                var currentScreen by remember { mutableStateOf(AppScreen.WELCOME) }

                // quiz results — stored here so we can pass to the result screen later
                var quizResult by remember {
                    mutableStateOf<RarityCalculator.RarityResult?>(null)
                }
                var quizAnswers by remember {
                    mutableStateOf<List<UserAnswer>>(emptyList())
                }

                when (currentScreen) {
                    AppScreen.WELCOME -> {
                        WelcomeScreen(
                            onStartQuiz = {
                                currentScreen = AppScreen.QUIZ
                            }
                        )
                    }

                    AppScreen.QUIZ -> {
                        QuizScreen(
                            onBackPressed = {
                                currentScreen = AppScreen.WELCOME
                            },
                            onQuizComplete = { result, answers ->
                                quizResult = result
                                quizAnswers = answers
                                // TODO: navigate to result screen
                                // for now, just go back to welcome
                                currentScreen = AppScreen.WELCOME
                            }
                        )
                    }

                    // we'll add these screens next
                    // AppScreen.RESULT -> { ... }
                    // AppScreen.SHARE -> { ... }
                }
            }
        }
    }
}

// simple navigation for now — keeps things straightforward
enum class AppScreen {
    WELCOME,
    QUIZ,
    // these are coming next:
    // RESULT,
    // SHARE,
    // BREAKDOWN,
}
