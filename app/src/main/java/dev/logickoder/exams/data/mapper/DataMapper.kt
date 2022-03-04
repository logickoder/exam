package dev.logickoder.exams.data.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.logickoder.exams.R
import dev.logickoder.exams.data.Attendance

@Composable
fun Attendance.toTable() = buildMap {
    put(stringResource(R.string.total_classes), totalClasses)
    put(stringResource(R.string.classes_attended), attended)
    put(stringResource(R.string.classes_missed), missed)
    put(stringResource(R.string.classes_left), totalClasses - (attended + missed))
}