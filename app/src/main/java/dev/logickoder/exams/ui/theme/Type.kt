package dev.logickoder.exams.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.logickoder.exams.R

val Font = FontFamily(
    listOf(
        Font(R.font.inter_regular, weight = FontWeight.Normal),
        Font(R.font.inter_thin, weight = FontWeight.Thin),
        Font(R.font.inter_light, weight = FontWeight.Light),
        Font(R.font.inter_medium, weight = FontWeight.Medium),
        Font(R.font.inter_semi_bold, weight = FontWeight.SemiBold),
        Font(R.font.inter_bold, weight = FontWeight.Bold),
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Font,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.sp,
    ),
    body2 = TextStyle(
        fontFamily = Font,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 17.sp,
    ),
    h1 = TextStyle(
        fontFamily = Font,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 34.sp,
    ),
    h2 = TextStyle(
        fontFamily = Font,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    h3 = TextStyle(
        fontFamily = Font,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 22.sp,
    ),
    h4 = TextStyle(
        fontFamily = Font,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.sp,
    ),
    button = TextStyle(
        fontFamily = Font,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 18.sp,
        color = Color.White,
    ),
    caption = TextStyle(
        fontFamily = Font,
        fontSize = 12.sp,
    ),
    overline = TextStyle(
        fontFamily = Font,
    ),
)