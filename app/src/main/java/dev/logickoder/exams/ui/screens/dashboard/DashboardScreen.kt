package dev.logickoder.exams.ui.screens.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.logickoder.exams.R
import dev.logickoder.exams.data.Attendance
import dev.logickoder.exams.data.Classroom
import dev.logickoder.exams.data.mapper.toTable
import dev.logickoder.exams.ui.Navigate
import dev.logickoder.exams.ui.Navigator
import dev.logickoder.exams.ui.shared.components.CircularProgressIndicator
import dev.logickoder.exams.ui.shared.components.ExaminationItem
import dev.logickoder.exams.ui.shared.components.Table
import dev.logickoder.exams.ui.theme.CardTitle
import dev.logickoder.exams.utils.PASS_ATTENDANCE
import androidx.compose.material.MaterialTheme as Theme

@Composable
fun AttendanceItem(
    attendance: Attendance,
    classroom: Classroom,
    modifier: Modifier = Modifier
) {
    var isHidden by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.clickable { isHidden = !isHidden },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                classroom.name,
                style = Theme.typography.body1,
                color = Theme.colors.primaryVariant,
            )
            Image(
                modifier = Modifier.padding(start = 24.dp),
                painter = painterResource(id = if (isHidden) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                contentDescription = "Hides or shows the attendance info",
                colorFilter = ColorFilter.tint(Theme.colors.onPrimary)
            )
        }
        if (!isHidden) {
            val attendancePercentage = attendance.run { attended.toFloat() / (attended + missed) }
            Row(
                modifier = Modifier.padding(
                    top = 36.dp,
                    start = 12.dp,
                    bottom = 24.dp,
                    end = 12.dp
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(0.4f),
                    progress = attendancePercentage
                )
                Table(
                    modifier = Modifier.weight(0.5f),
                    data = attendance.toTable()
                )
            }
            if (attendancePercentage < PASS_ATTENDANCE) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Theme.shapes.medium)
                        .background(Color(0x40297CAA))
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val color = Color(0xFF004A76)
                    Image(
                        painter = painterResource(R.drawable.ic_notification),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.low_attendance, classroom.code),
                        textAlign = TextAlign.Center,
                        style = Theme.typography.button.run { copy(fontSize = fontSize * 0.75) },
                        color = color,
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardHeader(
    modifier: Modifier = Modifier,
    name: String,
    @DrawableRes image: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                stringResource(R.string.hello_name, name),
                style = Theme.typography.h1,
                color = Theme.colors.primaryVariant
            )
            Text(
                stringResource(R.string.welcome_to_your_dashboard),
                style = Theme.typography.body2,
                color = Theme.colors.primaryVariant
            )
        }
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp),
            painter = painterResource(id = image),
            contentScale = ContentScale.Crop,
            contentDescription = "Hides or shows the attendance info",
        )
    }
}

@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    title: String,
    addAction: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = CombinedModifier(
            modifier,
            Modifier
                .background(Color.White)
                .padding(top = 24.dp, start = 24.dp, bottom = 24.dp)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = Theme.typography.h3,
                color = CardTitle,
            )
            addAction?.let {
                Image(
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .clickable { it() },
                    painter = painterResource(id = R.drawable.ic_add),
                    colorFilter = ColorFilter.tint(Theme.colors.onPrimary),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        content()
    }
}

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = viewModel(),
    navigate: Navigate,
) {
    val scrollState = rememberScrollState()
    val user by viewModel.user.observeAsState()
    val examination by viewModel.examinations.observeAsState()
    val attendance by viewModel.attendance.observeAsState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        DashboardHeader(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 40.dp)
                .fillMaxWidth(),
            name = user?.name ?: "",
            image = R.drawable.image
        )
        DashboardCard(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.examination),
            addAction = { navigate(Navigator.Forward) },
        ) {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                examination?.forEach { exam ->
                    item {
                        ExaminationItem(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(end = 24.dp),
                            exam = exam,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        DashboardCard(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.attendance)
        ) {
            Column {
                attendance?.forEachIndexed { index, pair ->
                    pair.second?.let {
                        AttendanceItem(
                            modifier = Modifier.padding(end = 24.dp),
                            attendance = it,
                            classroom = pair.first
                        )
                        if (index < attendance!!.lastIndex) Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}