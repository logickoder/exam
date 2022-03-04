package dev.logickoder.exams.ui.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import androidx.compose.material.CircularProgressIndicator as ProgressIndicator
import androidx.compose.material.MaterialTheme as Theme

@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float = 50f,
    size: Dp = 100.dp,
    strokeWidth: Dp = 8.dp
) {
    Box(modifier = modifier) {
        // this indicator is for the empty portion
        ProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(size),
            progress = 1f,
            color = Theme.colors.background.copy(0.7f),
            strokeWidth = strokeWidth
        )
        // this indicator is for the progress
        ProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(size)
                .clip(CircleShape),
            progress = progress,
            color = Theme.colors.secondary,
            strokeWidth = strokeWidth
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "${(progress * 100).roundToInt()}%",
            style = Theme.typography.h1.run { copy(fontSize = fontSize * 0.85) },
            color = Theme.colors.secondaryVariant
        )
    }
}