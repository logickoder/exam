package dev.logickoder.exams.data.mapper

import android.content.Context
import dev.logickoder.exams.data.Attendance

fun Attendance.toTable(context: Context) = buildMap {
    put(context.getString(R.string.total_classes), totalClasses)
    put(context.getString(R.string.classes_attended), attended)
    put(context.getString(R.string.classes_missed), missed)
    put(context.getString(R.string.classes_left), totalClasses - (attended + missed))
}