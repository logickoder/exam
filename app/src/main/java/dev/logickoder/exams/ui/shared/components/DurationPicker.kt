package dev.logickoder.exams.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.material.MaterialTheme as Theme

class Duration(days: Int, hours: Int, minutes: Int, seconds: Int) {
    var days: Int
        private set

    var hours: Int
        private set

    var minutes: Int
        private set

    var seconds: Int
        private set

    init {
        this.days = if (days >= 0) days else 0
        this.hours = if (hours in 0..23) hours else if (hours < 0) 0 else 23
        this.minutes = if (minutes in 0..59) minutes else if (minutes < 0) 0 else 59
        this.seconds = if (seconds in 0..59) seconds else if (seconds < 0) 0 else 59
    }
}

@Composable
fun DurationPicker(
    open: IsOpen,
    modifier: Modifier = Modifier,
    showDays: Boolean = false,
    showSeconds: Boolean = false,
    durationCallback: ((Duration) -> Unit)? = null,
) {
    val max = remember {
        4 + if (showSeconds) 2 else 0 + if (showDays) 2 else 0
    }
    var duration by remember { mutableStateOf("") }
    val numberClicked: (Int) -> Unit = { number: Int ->
        if (duration.length < max) duration += number
    }
    val onOkClick = {
        duration.chunked(2).map { it.toInt() }.let {
            durationCallback?.invoke(
                Duration(
                    days = if (showDays) it.first() else 0,
                    hours = it[if (showDays) 1 else 0],
                    minutes = it[if (showSeconds) it.lastIndex - 1 else it.lastIndex],
                    seconds = if (showSeconds) it.last() else 0,
                )
            )
        }
        open(false)
    }

    @Composable
    fun Duration(text: String, denomination: Char, modifier: Modifier = Modifier) {
        require(text.length == 2)
        Text(
            modifier = modifier,
            text = buildAnnotatedString {
                with(Theme.typography.h1) {
                    withStyle(
                        copy(
                            fontWeight = FontWeight.Light,
                            fontSize = fontSize * 1.5
                        ).toSpanStyle()
                    ) {
                        append(text)
                    }
                }
                withStyle(
                    Theme.typography.overline.copy(fontWeight = FontWeight.Light).toSpanStyle()
                ) {
                    append(denomination)
                }
            }
        )
    }

    @Composable
    fun DurationHeader(
        duration: String,
        modifier: Modifier = Modifier,
        showDays: Boolean = false,
        showSeconds: Boolean = false,
    ) = duration.chunked(2).let {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showDays) Duration(text = it.first(), denomination = 'd')
            Duration(text = it[if (showDays) 1 else 0], denomination = 'h')
            Duration(
                text = it[if (showSeconds) it.lastIndex - 1 else it.lastIndex],
                denomination = 'm'
            )
            if (showSeconds) Duration(text = it.last(), denomination = 's')
        }
    }

    @Composable
    fun DurationButtons(
        numberClicked: (Int) -> Unit,
        removeNumber: () -> Unit,
    ) {
        val textStyle = with(Theme.typography.h3) {
            copy(fontWeight = FontWeight.Light, fontSize = fontSize * 1.5)
        }
        val size = 12.dp
        (1..9).chunked(3).forEach { row ->
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                row.forEach {
                    Text(
                        it.toString(),
                        modifier = Modifier
                            .clickable { numberClicked(it) }
                            .padding(size),
                        style = textStyle,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        Row {
            Text(
                "0",
                modifier = Modifier
                    .padding(size)
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start),
                textAlign = TextAlign.Center,
                style = textStyle.copy(color = Color.Transparent),
            )
            Text(
                "0",
                modifier = Modifier
                    .clickable { numberClicked(0) }
                    .padding(size)
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = textStyle,
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = removeNumber,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .padding(size)
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_input_delete),
                    tint = Color.Black,
                    contentDescription = "delete a number",
                )
            }
        }
    }

    @Composable
    fun CloseButtons(modifier: Modifier = Modifier, open: IsOpen) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            val buttonStyle = with(Theme.typography.button) {
                copy(
                    color = Theme.colors.secondary,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize * 1.1,
                )
            }
            Text(
                "Ok",
                modifier = Modifier.clickable { onOkClick() },
                style = buttonStyle
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text("Cancel", modifier = Modifier.clickable { open(false) }, style = buttonStyle)
        }
    }

    Dialog(
        onDismissRequest = { open(false) },
    ) {
        Column(
            modifier = modifier
                .clip(Theme.shapes.large)
                .background(Color.White)
                .padding(24.dp)
                .wrapContentSize()
        ) {
            DurationHeader(
                duration = duration.padStart(max, '0'),
                showDays = showDays,
                showSeconds = showSeconds,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Theme.colors.secondary, thickness = 2.dp)
            Spacer(modifier = Modifier.height(12.dp))
            DurationButtons(numberClicked = numberClicked) {
                if (duration.isNotEmpty()) {
                    duration = duration.substring(0, duration.lastIndex)
                }
            }
            CloseButtons(Modifier.fillMaxWidth(), open)
        }
    }
}