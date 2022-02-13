package dev.logickoder.edvora.utils

import android.content.Context
import android.util.TypedValue
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Converts the local date to am pm format
 */
fun LocalTime.toAmPm(): String = format(DateTimeFormatter.ofPattern("hh:mm a"))


/**
 * @param context the android resource of an activity or fragment
 *
 * @return the dp equivalent of this number in int
 * */
fun Number.dp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, toFloat(), context.resources.displayMetrics
).toInt()
