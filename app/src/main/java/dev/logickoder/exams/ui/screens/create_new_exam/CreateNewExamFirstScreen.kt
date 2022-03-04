package dev.logickoder.exams.ui.screens.create_new_exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
                .fillMaxSize()
                .padding(horizontal = 24.dp)
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
                Section(stringResource(R.string.syllabus_title)) {
                    StandaloneTextInput(
                        onValueChange = {},
                        placeholder = stringResource(R.string.syllabus_title_hint),
                        modifier = Modifier.width(first)
                    )
                }
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
                Section(text = stringResource(R.string.duration_title)) {
                    DurationPickerTextField(
                        modifier = Modifier.width(third),
                        placeholder = stringResource(R.string.duration_title_hint),
                    )
                }
                Section(text = stringResource(R.string.timeframe_title)) {
                    DurationPickerTextField(
                        modifier = Modifier.width(third),
                        placeholder = stringResource(R.string.duration_title_hint),
                    )
                }
                Section(stringResource(R.string.total_marks_title)) {
                    StandaloneTextInput(
                        onValueChange = {},
                        modifier = Modifier.width(third),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        visualTransformation = DigitVisualTransformation()
                    )
                }
                Section(stringResource(R.string.category_title)) {
                    StandaloneTextInput(
                        onValueChange = {},
                        modifier = Modifier.width(second),
                        placeholder = stringResource(R.string.category_title_hint),
                    )
                }
            }
        }
    }
}