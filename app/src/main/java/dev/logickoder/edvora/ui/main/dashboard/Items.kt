package dev.logickoder.edvora.ui.main.dashboard

import android.view.ViewGroup
import androidx.core.view.isVisible
import dev.logickoder.edvora.R
import dev.logickoder.edvora.data.Attendance
import dev.logickoder.edvora.data.Classroom
import dev.logickoder.edvora.data.Examination
import dev.logickoder.edvora.data.mapper.toTable
import dev.logickoder.edvora.databinding.ItemAttendanceBinding
import dev.logickoder.edvora.databinding.ItemExamBinding
import dev.logickoder.edvora.databinding.PartialTableRowBinding
import dev.logickoder.edvora.ui.base.BaseItem
import dev.logickoder.edvora.utils.PASS_ATTENDANCE
import dev.logickoder.edvora.utils.toAmPm
import dev.logickoder.edvora.utils.view.layoutInflater
import dev.logickoder.edvora.utils.view.setAllOnClickListener
import java.time.LocalTime

class ExaminationItem(
    item: Examination
) : BaseItem<Examination, ItemExamBinding>(
    item, R.layout.item_exam, item.classroom
) {
    override fun inflate(parent: ViewGroup) = ItemExamBinding.inflate(
        parent.layoutInflater(), parent, false
    )

    override fun bind(binding: ItemExamBinding): Unit = with(binding) {
        ieTextExam.text = item.classroom.name
        ieTextMark.text = root.context.getString(R.string.marks, item.mark)
        ieTextDuration.text = item.duration.first.toAmPm()
            .plus(" - ").plus(item.duration.second.toAmPm())
        ieText1.text = item.text1
        ieText2.text = item.text2
        ieButtonTakeExam.isVisible = (LocalTime.now() in item.duration.first..item.duration.second)
    }
}

class AttendanceItem(
    item: Attendance, private val classroom: Classroom
) : BaseItem<Attendance, ItemAttendanceBinding>(
    item,
    R.layout.item_attendance,
    classroom
) {
    private var isDroppedDown = true

    override fun inflate(parent: ViewGroup) = ItemAttendanceBinding.inflate(
        parent.layoutInflater(), parent, false
    )

    override fun bind(binding: ItemAttendanceBinding): Unit = with(binding) {
        item.toTable(root.context).forEach { (text, amount) ->
            val tableRow = PartialTableRowBinding.inflate(
                iaTableStatistics.layoutInflater(), iaTableStatistics, false
            ).also {
                it.ptrTextHeader.text = text
                it.ptrTextContent.text = amount.toString()
            }
            iaTableStatistics.addView(tableRow.root)
        }
        val attendance = item.let { it.attended.toFloat() / (it.attended + it.missed) }.let {
            iaProgressAttendance.progress = (it * 100).toInt()
            if (it < PASS_ATTENDANCE) iaButtonAttendance.apply {
                isVisible = true
                text = context.getString(R.string.low_attendance, classroom.code)
            }
            it < PASS_ATTENDANCE
        }
        iaTextClass.text = classroom.name
        iaGroupDropdown.setAllOnClickListener {
            isDroppedDown = !isDroppedDown
            val drawable = if (isDroppedDown) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
            iaImageDropdown.setImageResource(drawable)
            iaGroup.isVisible = isDroppedDown
            if (attendance) iaButtonAttendance.isVisible = isDroppedDown
        }
    }

}