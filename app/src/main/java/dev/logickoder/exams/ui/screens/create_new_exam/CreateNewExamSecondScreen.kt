package dev.logickoder.exams.ui.screens.create_new_exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.exams.R
import dev.logickoder.exams.ui.Navigate
import dev.logickoder.exams.ui.Navigator
import dev.logickoder.exams.ui.shared.components.CreateExamAppBar
import dev.logickoder.exams.ui.shared.components.StandaloneTextInput
import androidx.compose.material.MaterialTheme as Theme

@Composable
fun CreateNewExamSecondScreen(
    viewModel: CreateExamsViewModel = viewModel(),
    navigate: Navigate,
) {

    @Composable
    fun Button(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = Theme.shapes.large,
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Theme.colors.onPrimary.copy(0.4f)),
        ) {
            Text(
                text = text,
                style = Theme.typography.button.copy(color = Theme.colors.primaryVariant)
            )
        }
    }

    val scrollState = rememberScrollState()
    var saved by remember { mutableStateOf(false) }
    val screenNav: Navigate = {
        when (it) {
            Navigator.Forward -> {
                saved = true
                navigate(it)
            }
            Navigator.Backward -> navigate(it)
        }
    }
    Scaffold(topBar = {
        CreateExamAppBar(action = stringResource(R.string.save), navigate = screenNav)
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .background(Color.White)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(stringResource(id = R.string.add_instructions), style = Theme.typography.h5)
            StandaloneTextInput(
                onValueChange = {}, modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
            Text(
                stringResource(id = R.string.section_name),
                style = Theme.typography.h5,
                modifier = Modifier.padding(top = 24.dp)
            )
            StandaloneTextInput(
                onValueChange = {},
                placeholder = stringResource(id = R.string.section_title),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
            StandaloneTextInput(
                onValueChange = {},
                placeholder = stringResource(id = R.string.section_description),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(89.dp)
                    .fillMaxWidth()
            )
            Button(
                text = stringResource(id = R.string.add_question),
                modifier = Modifier.padding(top = 24.dp)
            ) {}
            Button(
                text = stringResource(id = R.string.add_another_section),
                modifier = Modifier.padding(top = 16.dp)
            ) {}
        }
    }
}