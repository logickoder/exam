package dev.logickoder.edvora.ui.main.exams

import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dev.logickoder.edvora.R
import dev.logickoder.edvora.data.Option
import dev.logickoder.edvora.databinding.ItemQuestionBinding
import dev.logickoder.edvora.databinding.ItemQuestionOptionBinding
import dev.logickoder.edvora.ui.base.BaseItem
import dev.logickoder.edvora.ui.base.BaseListAdapter
import dev.logickoder.edvora.utils.view.layoutInflater
import java.time.Instant

private val checkedState = listOf(android.R.attr.state_checked).toIntArray()
private val uncheckedState = emptyList<Int>().toIntArray()
private const val A = 'A'

interface OptionsContainer {
    fun addOption()
    fun removeOption(option: OptionItem)
    fun updateChecked(option: OptionItem)
}

data class OptionDomain(
    val letter: Char,
    var option: Option = "",
    val isSelected: Boolean = false,
    val enabled: Boolean = true
)

interface QuestionsContainer {
    fun removeQuestion(question: QuestionItem)
}

class QuestionItem(
    val container: QuestionsContainer, val id: Long = Instant.now().epochSecond
) : BaseItem<Long, ItemQuestionBinding>(id, R.layout.item_question, id), OptionsContainer {

    private val options by lazy { BaseListAdapter() }
    private var binding: ItemQuestionBinding? = null

    override fun inflate(parent: ViewGroup) = ItemQuestionBinding.inflate(
        parent.layoutInflater(), parent, false
    )

    override fun bind(binding: ItemQuestionBinding): Unit = with(binding) {
        this@QuestionItem.binding = binding
        iqListOptions.adapter = options.also { addOption() }
        iqTextRemove.setOnClickListener { container.removeQuestion(this@QuestionItem) }
        iqTextSave.setOnClickListener {
            changeEditability(
                iqTextSave.text.toString().equals(root.context.getString(R.string.edit), true)
            )
        }
    }

    override fun addOption() {
        if (options.itemCount < 4) {
            OptionItem(OptionDomain(A + options.itemCount), this).also {
                options.submitList(options.currentList + it)
            }
        }
    }

    override fun removeOption(option: OptionItem) {
        options.submitList((options.currentList - option).mapIndexed { index, it ->
            val item = it as OptionItem
            return@mapIndexed OptionItem(
                item.item.copy(letter = A + index), this
            )
        })
    }

    override fun updateChecked(option: OptionItem) {
        options.submitList(options.currentList.map {
            val item = it as OptionItem
            return@map OptionItem(
                item.item.copy(isSelected = (item == option)), this
            )
        })
    }

    private fun changeEditability(boolean: Boolean) = binding?.apply {
        iqTextRemove.apply {
            text = context.getText(if (boolean) R.string.discard else R.string.remove)
            setTextColor(resources.getColor(if (boolean) R.color.primary_color else R.color.create_new_exam_remove))
        }
        iqTextSave.apply {
            text = context.getText(if (boolean) R.string.save else R.string.edit)
            setTextColor(resources.getColor(if (boolean) R.color.dashboard_card_button else R.color.create_new_exam_edit))
        }
        iqTextinputQuestion.isEnabled = boolean
//        options.currentList.forEach { (it as OptionItem).changeEditability(boolean) }
    }
}

class OptionItem(
    option: OptionDomain, private val container: OptionsContainer
) : BaseItem<OptionDomain, ItemQuestionOptionBinding>(
    option,
    R.layout.item_question_option,
    option.letter
) {

    override fun inflate(parent: ViewGroup) = ItemQuestionOptionBinding.inflate(
        parent.layoutInflater(), parent, false
    )

    override fun bind(binding: ItemQuestionOptionBinding): Unit = with(binding) {
        iqoTextOptionLetter.also {
            it.text = item.letter.toString()
            it.isEnabled = item.enabled
            it.background.state = if (item.isSelected) checkedState else uncheckedState
            it.setTextColor(
                root.resources.getColor(
                    if (item.isSelected)
                        R.color.create_new_exam_option_checked
                    else R.color.create_new_exam_option_unchecked
                )
            )
        }
        iqoTextinputOption.also {
            it.setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // instruct the container to add a new option if the next button on the keyboard is clicked
                    container.addOption()
                    true
                } else false
            }
            it.addTextChangedListener {
                // hide the remove button if there is no text in the option
                iqoImageRemoveOption.isInvisible = it.isNullOrBlank()
                // update the options data item with the text
                item.option = it.toString()
            }
            it.setText(item.option)
        }
        iqoImageRemoveOption.also {
            it.setOnClickListener { container.removeOption(this@OptionItem) }
            it.isVisible = item.enabled
        }
        iqoSelected.also {
            it.setOnClickListener { _ ->
                // tell the options container that this option is selected
                if (it.isChecked) {
                    container.updateChecked(this@OptionItem)
                }
            }
            it.isVisible = item.enabled
            it.isChecked = item.isSelected
        }
        iqoOption.background.state = if (item.isSelected) checkedState else uncheckedState
    }
}