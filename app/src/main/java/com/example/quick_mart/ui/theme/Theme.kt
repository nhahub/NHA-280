package com.example.quick_mart.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun QuickMartTheme1(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    // نستخدم ألوانك اللي عرفتيها في Colors.kt
    val colorScheme = darkColorScheme(
        primary = Cyan,              // اللون الأساسي للأزرار
        onPrimary = Black,           // لون النص فوق العناصر الأساسية
        background = Black,          // خلفية الشاشة
        onBackground = White,        // النصوص العامة
        surface = Grey50,            // لون الخلفية للعناصر الداخلية
        onSurface = White,           // النص داخل الكروت
        secondary = Cyan50,          // لون ثانوي خفيف
        onSecondary = White,
        onError = White
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = QuickMartTypography, // الخطوط اللي بعتيها فوق
        shapes = Shapes(),
        content = content
    )
}


