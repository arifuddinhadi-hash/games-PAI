package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

val FredokaFontFamily = FontFamily(
    Font(R.font.fredoka, FontWeight.Normal),
    Font(R.font.fredoka, FontWeight.Medium),
    Font(R.font.fredoka, FontWeight.SemiBold),
    Font(R.font.fredoka, FontWeight.Bold)
)

val PlusJakartaSansFamily = FontFamily(
    Font(R.font.plus_jakarta_sans, FontWeight.Normal),
    Font(R.font.plus_jakarta_sans, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_sans, FontWeight.Bold)
)

fun createAppTypography(scale: Float = 1.0f): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = FredokaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (34 * scale).sp,
            lineHeight = (42 * scale).sp
        ),
        headlineLarge = TextStyle(
            fontFamily = FredokaFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (28 * scale).sp,
            lineHeight = (36 * scale).sp
        ),
        headlineMedium = TextStyle(
            fontFamily = FredokaFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = (23 * scale).sp,
            lineHeight = (30 * scale).sp
        ),
        titleLarge = TextStyle(
            fontFamily = FredokaFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = (20 * scale).sp,
            lineHeight = (26 * scale).sp
        ),
        titleMedium = TextStyle(
            fontFamily = PlusJakartaSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (16 * scale).sp,
            lineHeight = (22 * scale).sp
        ),
        bodyLarge = TextStyle(
            fontFamily = PlusJakartaSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (16 * scale).sp,
            lineHeight = (24 * scale).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = PlusJakartaSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (14 * scale).sp,
            lineHeight = (21 * scale).sp
        ),
        labelLarge = TextStyle(
            fontFamily = PlusJakartaSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = (14 * scale).sp,
            lineHeight = (20 * scale).sp
        ),
        labelMedium = TextStyle(
            fontFamily = PlusJakartaSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = (12 * scale).sp,
            lineHeight = (16 * scale).sp
        ),
        labelSmall = TextStyle(
            fontFamily = PlusJakartaSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = (11 * scale).sp,
            lineHeight = (14 * scale).sp
        )
    )
}
