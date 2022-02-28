package dev.logickoder.exams.ui.main.dashboard

import android.view.ViewGroup
import dev.logickoder.exams.R
import dev.logickoder.exams.data.Attendance
import dev.logickoder.exams.data.Classroom
import dev.logickoder.exams.data.Examination
import dev.logickoder.exams.databinding.ItemAttendanceBinding
import dev.logickoder.exams.databinding.ItemExamBinding
import dev.logickoder.exams.databinding.PartialTableRowBinding
import dev.logickoder.exams.ui.base.BaseItem
import dev.logickoder.exams.utils.view.layoutInflater
import dev.logickoder.exams.utils.view.setAllOnClickListener

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
        ieText1.text = item.syllabus
        ieTextCategory.text = item.category
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
            iaProgress.ppbProgress.setProgressCompat((it * 100).toInt(), true)
            iaProgress.ppbTextProgress.text = "${(it * 100).toInt()}%"
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