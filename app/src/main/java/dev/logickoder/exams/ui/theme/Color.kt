package dev.logickoder.exams.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Base App Colors
val PrimaryColor = Color(0xFFBF9B9B)
val PrimaryColorDark = Color(0xFF733D47)
val PrimaryColorLight = Color(0xFFBF9B9B)
val Background = Color(0xFFF4F4F4)
val SecondaryColor = Color(0xFF1AAE02)
val SecondaryColorDark = Color(0xFF148D00)

val CardTitle = Color(0xFF696969)
val CardCaption = Color(0xFF8D8D8D)

@Composable
fun defaultTextFieldColor(
    textColor: Color = MaterialTheme.colors.primaryVariant,
    trailingIconColor: Color = MaterialTheme.colors.onPrimary,
    placeholderColor: Color = MaterialTheme.colors.onPrimary,
    backgroundColor: Color = MaterialTheme.colors.background,
    focusedBorderColor: Color = Color.Transparent,
    unfocusedBorderColor: Color = Color.Transparent,
) = TextFieldDefaults.outlinedTextFieldColors(
    textColor = textColor,
    trailingIconColor = trailingIconColor,
    placeholderColor = placeholderColor,
    backgroundColor = backgroundColor,
    focusedBorderColor = focusedBorderColor,
    unfocusedBorderColor = unfocusedBorderColor,
)