package dev.logickoder.exams.ui.screens.create_new_exam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.exams.R
import dev.logickoder.exams.ui.Navigate
import dev.logickoder.exams.ui.Navigator
import dev.logickoder.exams.ui.shared.components.CreateExamAppBar

@Composable
fun CreateNewExamSecondScreen(
    viewModel: CreateExamsViewModel = viewModel(),
    navigate: Navigate,
) {
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
        CreateExamAppBar(
            action = stringResource(R.string.save),
            navigate = screenNav
        )
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            if (saved) Text("Saved")
        }
    }
}