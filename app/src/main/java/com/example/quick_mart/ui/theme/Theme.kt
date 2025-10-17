package com.example.quick_mart.ui.theme

import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = Cyan,
    onPrimary = Black,
    primaryContainer = Grey50,
    onPrimaryContainer = White,
    secondary = Orange,
    onSecondary = Black,
    tertiary = Purple,
    background = Black,
    onBackground = White,
    surface = Grey50,
    onSurface = Grey150,
    error = Red,
    onError = White
)

@Composable
fun QuickMartTheme(content: @Composable () -> Unit) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = QuickMartTypography,
        content = content
    )
}
