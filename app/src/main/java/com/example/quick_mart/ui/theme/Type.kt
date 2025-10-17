package com.example.quick_mart.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.quick_mart.R


val plus_jakarta_sans = FontFamily(
    Font(R.font.plus_jakarta_sans_regular, FontWeight.Normal),
    Font(R.font.plus_jakarta_sans_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_sans_bold, FontWeight.Bold)
)

val QuickMartTypography = Typography(
    //  Heading 1
    displayLarge = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = 0.sp
    ),

    //  Heading 2
    displayMedium = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    //  Heading 3
    displaySmall = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp
    ),

    //  Body 1
    bodyLarge = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    //  Body 2
    bodyMedium = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.5.sp
    ),

    //  Button 1
    labelLarge = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),

    //  Button 2
    labelMedium = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),

    //  Caption
    labelSmall = TextStyle(
        fontFamily = plus_jakarta_sans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
