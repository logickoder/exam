package dev.logickoder.exams.ui.screens.exams

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.exams.R
import dev.logickoder.exams.ui.Navigate
import dev.logickoder.exams.ui.Navigator
import dev.logickoder.exams.ui.screens.dashboard.DashboardCard
import dev.logickoder.exams.ui.screens.dashboard.DashboardViewModel
import dev.logickoder.exams.ui.shared.components.ExaminationItem

@Composable
fun ExamsScreen(
    viewModel: DashboardViewModel = viewModel(),
    navigate: Navigate,
) {
    val examination by viewModel.examinations.observeAsState()
    DashboardCard(
        modifier = Modifier.fillMaxSize(),
        title = stringResource(id = R.string.examination),
        addAction = { navigate(Navigator.Forward) },
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            examination?.forEachIndexed { index, exam ->
                item {
                    ExaminationItem(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(end = 24.dp),
                        exam = exam,
                    )
                    if (index < examination!!.lastIndex) Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}