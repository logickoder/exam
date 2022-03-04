package dev.logickoder.exams.ui.screens.create_new_exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.exams.R
import dev.logickoder.exams.ui.Navigate
import dev.logickoder.exams.ui.shared.components.*
import androidx.compose.material.MaterialTheme as Theme

@Composable
fun CreateNewExamFirstScreen(
    viewModel: CreateExamsViewModel = viewModel(),
    navigate: Navigate,
) {
    @Composable
    fun Section(
        text: String,
        content: @Composable () -> Unit,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text, style = Theme.typography.body1, color = Color.Black)
            content()
        }
        Spacer(modifier = Modifier.height(24.dp))
    }

    @Composable
    fun InputSection(text: String, placeholder: String, width: Dp) = Section(text) {
        StandaloneTextInput(
            onValueChange = {},
            placeholder = placeholder,
            modifier = Modifier.width(width)
        )
    }

    val scrollState = rememberScrollState()
    val classroom by viewModel.classrooms.observeAsState()

    Scaffold(topBar = {
        CreateExamAppBar(
            action = stringResource(R.string.next),
            navigate = navigate
        )
    }) {
        BoxWithConstraints(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            val (first, second, third) = Triple(maxWidth * 0.7f, maxWidth * 0.55f, maxWidth * 0.3f)
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Section(text = stringResource(id = R.string.classroom_title)) {
                    DropdownTextField(
                        modifier = Modifier.width(first),
                        placeholder = stringResource(id = R.string.classroom_title_hint),
                        suggestions = classroom?.map { it.name } ?: emptyList(),
                    )
                }
                InputSection(
                    stringResource(R.string.syllabus_title),
                    stringResource(R.string.syllabus_title_hint),
                    first
                )
                Section(text = stringResource(R.string.date_title)) {
                    DatePickerTextField(
                        modifier = Modifier.width(second),
                        placeholder = stringResource(R.string.date_title_hint),
                    )
                }
                Section(text = stringResource(R.string.time_title)) {
                    TimePickerTextField(
                        modifier = Modifier.width(second),
                        placeholder = stringResource(R.string.time_title_hint),
                    )
                }
                InputSection(
                    stringResource(R.string.duration_title),
                    stringResource(R.string.duration_title_hint),
                    third
                )
                InputSection(
                    stringResource(R.string.timeframe_title),
                    stringResource(R.string.duration_title_hint),
                    third
                )
                InputSection(stringResource(R.string.total_marks_title), "", third)
                InputSection(
                    stringResource(R.string.category_title),
                    stringResource(R.string.category_title_hint),
                    second
                )
            }
        }
    }
}