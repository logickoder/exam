package dev.logickoder.exams.ui.shared.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import dev.logickoder.exams.utils.activity
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Composable
fun DatePicker(isOpen: IsOpen, listener: (LocalDate) -> Unit) {
    LocalContext.current.activity()?.run {
        MaterialDatePicker.Builder.datePicker().build().apply {
            show(supportFragmentManager, toString())
            addOnPositiveButtonClickListener {
                Calendar.getInstance().run {
                    timeInMillis = it
                    LocalDate.of(
                        get(Calendar.YEAR),
                        get(Calendar.MONTH) + 1,
                        get(Calendar.DAY_OF_MONTH)
                    )
                }.let { date -> listener(date) }
            }
            addOnDismissListener { isOpen(false) }
        }
    }
}

@Composable
fun TimePicker(isOpen: IsOpen, listener: (LocalTime) -> Unit) {
    LocalContext.current.activity()?.run {
        MaterialTimePicker.Builder().build().apply {
            show(supportFragmentManager, toString())
            addOnPositiveButtonClickListener { listener(LocalTime.of(hour, minute)) }
            addOnDismissListener { isOpen(false) }
        }
    }
}