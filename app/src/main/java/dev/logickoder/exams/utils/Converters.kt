package dev.logickoder.exams.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Converts the local date to am pm format
 */
fun LocalTime.toAmPm(): String = format(DateTimeFormatter.ofPattern("hh:mm a"))
