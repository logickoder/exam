package dev.logickoder.exams.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmptyScreen() {
    Column(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize()) {}
}