package dev.logickoder.exams.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dev.logickoder.exams.R
import dev.logickoder.exams.data.Examination
import dev.logickoder.exams.ui.theme.CardCaption
import dev.logickoder.exams.utils.toAmPm
import java.time.LocalTime
import androidx.compose.material.MaterialTheme as Theme


@Composable
fun ExaminationItem(
    exam: Examination,
    modifier: Modifier = Modifier,
) = with(exam) {

    @Composable
    fun Text(text: String, color: Color = CardCaption, modifier: Modifier) {
        Text(text = text, color = color, style = Theme.typography.body2, modifier = modifier)
    }

    ConstraintLayout(
        modifier = modifier
            .clip(Theme.shapes.large)
            .background(Theme.colors.background)
            .padding(24.dp)
    ) {
        val (_title, _button, _duration, _syllabus, _marks, _category) = createRefs()
        val guideline = createGuidelineFromStart(0.8f)
        Text(
            modifier = Modifier.constrainAs(_title) {
                top.linkTo(parent.top)
                linkTo(parent.start, guideline, endMargin = 4.dp, bias = 0f)
                width = Dimension.fillToConstraints
            },
            text = classroom.name,
            style = Theme.typography.h2,
            color = Color.Black,
        )
        Button(
            modifier = Modifier
                .alpha(if (LocalTime.now() in duration.first..duration.second) 1f else 0f)
                .constrainAs(_button) {
                    top.linkTo(_title.top)
                    linkTo(guideline, parent.end, bias = 1f)
                },
            onClick = { },
            colors = ButtonDefaults.buttonColors(backgroundColor = Theme.colors.secondary),
            shape = Theme.shapes.medium,
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
        ) {
            Text(stringResource(id = R.string.take), style = Theme.typography.button)
        }
        Text(
            "${duration.first.toAmPm()} - ${duration.second.toAmPm()}",
            modifier = Modifier.constrainAs(_duration) {
                start.linkTo(parent.start)
                linkTo(
                    _title.bottom,
                    _marks.top,
                    bottomMargin = 16.dp,
                    topMargin = 24.dp,
                    bias = 1f
                )
            }
        )
        Text(
            syllabus,
            modifier = Modifier.constrainAs(_syllabus) {
                end.linkTo(parent.end)
                linkTo(_duration.top, _duration.bottom)
            }
        )
        Text(
            stringResource(id = R.string.marks, mark),
            modifier = Modifier.constrainAs(_marks) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        )
        Text(
            category,
            Theme.colors.primaryVariant,
            modifier = Modifier.constrainAs(_category) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}