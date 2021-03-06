package dev.logickoder.exams.data

import java.time.LocalTime

typealias StartTime = LocalTime
typealias EndTime = LocalTime
typealias Mark = Int

/**
 *
 */
data class Examination(
    val classroom: Classroom,
    val duration: Pair<StartTime, EndTime>,
    val mark: Mark,
    val syllabus: String,
    val category: String
)