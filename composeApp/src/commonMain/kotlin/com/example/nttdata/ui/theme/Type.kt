package com.example.nttdata.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Función para obtener la tipografía escalada
fun getTypography(fontScale: Float): Typography {
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = (16 * fontScale).sp,
            lineHeight = (24 * fontScale).sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = (14 * fontScale).sp,
            lineHeight = (20 * fontScale).sp,
            letterSpacing = 0.25.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = (22 * fontScale).sp,
            lineHeight = (28 * fontScale).sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = (16 * fontScale).sp,
            lineHeight = (24 * fontScale).sp,
            letterSpacing = 0.15.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = (11 * fontScale).sp,
            lineHeight = (16 * fontScale).sp,
            letterSpacing = 0.5.sp
        )
    )
}