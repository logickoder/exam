package dev.logickoder.exams.ui.shared.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.logickoder.exams.R
import dev.logickoder.exams.ui.Navigate
import dev.logickoder.exams.ui.Navigator
import androidx.compose.material.MaterialTheme as Theme

@Composable
fun CreateExamAppBar(action: String, modifier: Modifier = Modifier, navigate: Navigate) {
    val padding = 16.dp
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(id = R.string.create_new_exam)) },
        backgroundColor = Theme.colors.background,
        navigationIcon = {
            IconButton(
                onClick = { navigate(Navigator.Backward) },
                modifier = Modifier.padding(start = padding),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    tint = Theme.colors.primary,
                    contentDescription = null,
                )
            }
        },
        actions = {
            OutlinedButton(
                modifier = Modifier.padding(end = padding),
                onClick = { navigate(Navigator.Forward) },
                shape = Theme.shapes.medium,
                border = BorderStroke(1.dp, Theme.colors.onPrimary),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                contentPadding = PaddingValues(6.dp)
            ) {
                Text(
                    action,
                    style = Theme.typography.h4,
                    color = Theme.colors.onPrimary
                )
            }
        }
    )
}