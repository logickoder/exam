package dev.logickoder.exams.ui.shared.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.PopupProperties
import dev.logickoder.exams.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.material.MaterialTheme as Theme

typealias ValueChange = (String) -> Unit
typealias IsOpen = (Boolean) -> Unit

data class PopupTextFieldScope(
    val open: IsOpen,
    val updateValue: ValueChange,
    val isOpen: Boolean,
)

@Composable
fun TextInput(
    onValueChange: ValueChange,
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String? = null,
    icon: Painter? = null,
    readOnly: Boolean = false,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it !== value) {
                onValueChange(it)
            }
        },
        modifier = modifier,
        textStyle = Theme.typography.body2,
        placeholder = placeholder?.let { { Text(it, style = Theme.typography.body2) } },
        trailingIcon = icon?.let { { Icon(painter = icon, contentDescription = null) } },
        shape = Theme.shapes.large,
        readOnly = readOnly,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Theme.colors.primaryVariant,
            trailingIconColor = Theme.colors.onPrimary,
            placeholderColor = Theme.colors.onPrimary,
            backgroundColor = Theme.colors.background,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        )
    )
}

@Composable
fun StandaloneTextInput(
    onValueChange: ValueChange,
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String? = null,
    icon: Painter? = null,
) {
    var text by remember { mutableStateOf(value) }
    TextInput(
        onValueChange = {
            text = it
            onValueChange(it)
        },
        modifier, text, placeholder, icon
    )
}

@Composable
fun PopupTextInput(
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    icon: Painter? = null,
    content: @Composable PopupTextFieldScope.() -> Unit,
) {
    var isOpen by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    val isOpenListener: IsOpen = { isOpen = it }
    val selectedListener: ValueChange = { selected = it }
    Column(modifier = modifier) {
        Log.e("PopupTextInput", "selected: $selected")
        Box {
            TextInput(
                value = selected,
                onValueChange = {},
                placeholder = placeholder,
                icon = icon,
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
            )
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .clip(Theme.shapes.large)
                    .background(Color.Transparent)
                    .clickable { isOpen = true }
            )
        }
        PopupTextFieldScope(isOpenListener, selectedListener, isOpen).content()
    }
}

@Composable
fun DropdownTextField(
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    onSelected: ValueChange? = null,
    suggestions: List<String> = emptyList(),
) = PopupTextInput(
    modifier = modifier,
    placeholder = placeholder,
    icon = painterResource(id = R.drawable.ic_arrow_down)
) {
    DropdownMenu(
        expanded = isOpen,
        onDismissRequest = { open(false) },
        modifier = Modifier.fillMaxWidth(),
        properties = PopupProperties(focusable = false),
    ) {
        suggestions.forEach { suggestion ->
            DropdownMenuItem(
                onClick = {
                    open(false)
                    updateValue(suggestion)
                    onSelected?.invoke(suggestion)
                }
            ) {
                Text(text = suggestion)
            }
        }
    }
}

@Composable
fun DatePickerTextField(
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    icon: Painter? = painterResource(id = R.drawable.ic_calendar),
    onSelected: ((LocalDate) -> Unit)? = null,
) = PopupTextInput(modifier = modifier, placeholder = placeholder, icon = icon) {
    if (isOpen)
        DatePicker(open) { date ->
            updateValue(date.format(DateTimeFormatter.ofPattern("dd / MM / yyyy")))
            onSelected?.invoke(date)
        }
}

@Composable
fun TimePickerTextField(
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    icon: Painter? = painterResource(id = R.drawable.ic_time),
    onSelected: ((LocalTime) -> Unit)? = null,
) = PopupTextInput(modifier = modifier, placeholder = placeholder, icon = icon) {
    if (isOpen)
        TimePicker(open) { time ->
            updateValue(time.format(DateTimeFormatter.ofPattern("h : mm a")))
            onSelected?.invoke(time)
        }
}

