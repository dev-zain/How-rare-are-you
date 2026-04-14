package com.devzain.howrareareyou.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// using a custom light scheme instead of dynamic colors
// because we want consistent branding across all devices
private val AppColorScheme = lightColorScheme(
    primary = BrandPurple,
    onPrimary = TextOnPurple,
    secondary = AccentTeal,
    tertiary = AccentGold,
    background = SurfaceBg,
    onBackground = TextDark,
    surface = SurfaceWhite,
    onSurface = TextDark,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = TextMedium,
    outline = SurfaceDivider,
)

@Composable
fun HowrareAreYouTheme(content: @Composable () -> Unit) {
    // make the status bar blend with our gradient header
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // status bar matches the purple gradient top
            window.statusBarColor = BrandPurple.toArgb()
            window.navigationBarColor = SurfaceBg.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false    // white icons on purple
                isAppearanceLightNavigationBars = true // dark icons on light bg
            }
        }
    }

    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
