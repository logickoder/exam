package dev.logickoder.exams.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dev.logickoder.exams.R
import dev.logickoder.exams.data.Option
import dev.logickoder.exams.data.Question
import androidx.compose.material.MaterialTheme as Theme

private data class OptionItem (
    val character: Char,
    var option: Option = "",
    val checked: Boolean = false,
)

@Composable
fun Option(
    character: Char,
    value: String,
    onValueChange: ValueChange,
    checked: Boolean,
    checkedChanged: (Boolean) -> Unit,
    enabled: Boolean,
    createOption: () -> Unit,
    removeOption: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier,
    ) {
        val (check, input, remove) = createRefs()
        if (enabled) RadioButton(
            modifier = Modifier.constrainAs(check){
                linkTo(top = input.top, bottom = input.bottom)
                start.linkTo(parent.start)
            },
            selected = checked,
            onClick = { checkedChanged(!checked) },
            colors = RadioButtonDefaults.colors(),
        )
        OptionInput(
            character = character,
            value = value,
            onValueChange = onValueChange,
            checked = checked,
            enabled = enabled,
            createOption = createOption,
            modifier = Modifier.constrainAs(input) {
                top.linkTo(parent.top)
                linkTo(start = check.end, end = remove.start, startMargin = 8.dp, endMargin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )
        if (enabled) Icon(
            modifier = Modifier
                .clickable { removeOption() }
                .constrainAs(remove) {
                    linkTo(top = input.top, bottom = input.bottom)
                    end.linkTo(parent.end)
                },
            painter = painterResource(id = R.drawable.ic_cancel),
            contentDescription = "remove this option",
            tint = Theme.colors.onPrimary,
        )
    }
}

@Composable
fun Question(
    question: Question,
    onQuestionSaved: (Question) -> Unit,
    deleteQuestion: () -> Unit,
) {
    var questionText by rememberSaveable { mutableStateOf(question.question) }
    val options = remember {
        question.options.mapIndexed{ index, option ->
            OptionItem('A' + index, option)
        }.let { options ->
            options.ifEmpty { listOf(OptionItem('A', "")) }
        }.toMutableStateList()
    }
    var enabled by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(top = 24.dp)
            .clip(Theme.shapes.large)
            .background(Theme.colors.background)
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        val textStyle = Theme.typography.body1
        BasicTextField(
            value = questionText,
            onValueChange = { if (it !== questionText) questionText = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle.copy(color = Theme.colors.primaryVariant),
            singleLine = true,
            enabled = enabled,
            decorationBox = { textField ->
                Column(modifier = Modifier.fillMaxWidth()){
                    Text(
                        stringResource(id = R.string.question_input_hint),
                        modifier = Modifier.alpha(if (questionText.isEmpty()) 1f else 0f),
                        style = textStyle.copy(color = Theme.colors.onPrimary),
                    )
                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = Theme.colors.onPrimary.copy(0.4f)
                    )
                }
                textField()
            }
        )
        options.forEachIndexed { index, option ->
            Option(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                character = option.character,
                value = option.option,
                onValueChange = { options[index] = option.copy(option = it) },
                checked = option.checked,
                checkedChanged = {
                    for (i in 0 until options.size) {
                        options[i] = if (options[i] == options[index]) {
                            options[i].copy(checked = it)
                        } else options[i].copy(checked = false)
                    }
                },
                enabled = enabled,
                createOption = {
                    if (options.size < 4) options.add(OptionItem('A' + options.size))
                },
                removeOption = {
                    if (options.size > 1) {
                        options -= option
                        for (i in 0 until options.size) {
                            options[i] = options[i].copy(character = 'A' + i)
                        }
                    }
                }
            )
        }
        Row(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text(
                stringResource(id = if (enabled) R.string.delete else R.string.remove),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .clickable {
                        deleteQuestion()
                    }.padding(12.dp),
                style = Theme.typography.h4,
                textAlign = TextAlign.Center,
                color = if (enabled) Theme.colors.onPrimary else Color.Red,
            )
            Text(
                stringResource(id = if (enabled) R.string.save else R.string.edit),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
                    .clickable {
                        if (enabled) {
                            onQuestionSaved(Question(questionText, options.map { it.option }))
                        }
                        enabled =! enabled
                    }.padding(12.dp),
                style = Theme.typography.h4,
                textAlign = TextAlign.Center,
                color = if (enabled) Theme.colors.secondary else Color.Blue,
            )
        }
    }
}

@Composable
private fun OptionInput(
    character: Char,
    value: String,
    onValueChange: ValueChange,
    checked: Boolean,
    enabled: Boolean,
    createOption: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .border(1.dp, Theme.colors.onPrimary, Theme.shapes.medium)
            .clip(Theme.shapes.medium)
            .background(if (checked) Theme.colors.onPrimary else Color.Transparent)
    ) {
        val (option, input) = createRefs()
        Text(
            text = character.toString(),
            modifier = Modifier
                .constrainAs(option) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        topMargin = 4.dp,
                        bottomMargin = 4.dp
                    )
                    start.linkTo(parent.start, 4.dp)
                }
                .clip(Theme.shapes.small)
                .background(if (checked) Theme.colors.primaryVariant else Theme.colors.onPrimary)
                .padding(10.dp, 6.dp),
            style = Theme.typography.h4,
            color = if (checked) Theme.colors.onPrimary else Theme.colors.primaryVariant,
        )
        val textStyle = Theme.typography.button.copy(fontWeight = FontWeight.Light)
        BasicTextField(
            value = value,
            onValueChange = { if (it !== value) onValueChange(it) },
            modifier = Modifier.constrainAs(input){
                linkTo(
                    top = option.top, bottom = option.bottom,
                    start = option.end, end = parent.end, endMargin = 4.dp, startMargin = 8.dp
                )
                height = Dimension.preferredWrapContent
                width = Dimension.fillToConstraints
            },
            enabled = enabled,
            textStyle = textStyle,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { createOption() }),
            decorationBox = { textField ->
                Row(modifier = Modifier.fillMaxWidth()){
                    if(value.isEmpty()){
                        Text(
                            stringResource(id = R.string.option_field_hint),
                            style = textStyle,
                            color = Theme.colors.onSurface.copy(ContentAlpha.medium),
                        )
                    }
                }
                textField()
            }
        )
    }
}

