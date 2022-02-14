package dev.logickoder.edvora.ui.main.exams

import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dev.logickoder.edvora.R
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
    fun updateChecked(option: OptionItem)
}

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
            OptionItem((A + options.itemCount).toString(),this).also {
                options.submitList(options.currentList + it)
            }
        }
    }

    override fun updateChecked(option: OptionItem) {
        (options.currentList - option).forEach { (it as OptionItem).updateCheck(false) }
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
        options.currentList.forEach { (it as OptionItem).changeEditability(boolean) }
    }
}

class OptionItem(
    private var letter: String, private val container: OptionsContainer
) : BaseItem<String, ItemQuestionOptionBinding>(letter, R.layout.item_question_option, letter) {

    private var binding: ItemQuestionOptionBinding? = null

    override fun inflate(parent: ViewGroup) = ItemQuestionOptionBinding.inflate(
        parent.layoutInflater(), parent, false
    )

    override fun bind(binding: ItemQuestionOptionBinding): Unit = with(binding) {
        this@OptionItem.binding = binding
        iqoTextOptionLetter.also {
            it.text = letter
        }
        iqoTextinputOption.also {
            it.setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    container.addOption()
                    true
                } else false
            }
            it.addTextChangedListener { iqoImageRemoveOption.isInvisible = it.isNullOrBlank() }
        }
        iqoSelected.setOnClickListener {
            if (iqoSelected.isChecked) {
                container.updateChecked(this@OptionItem)
                iqoOption.background.state = checkedState
                iqoTextOptionLetter.background.state = checkedState
                iqoTextOptionLetter.setTextColor(it.resources.getColor(R.color.create_new_exam_option_checked))
            }
        }
    }

    fun updateCheck(checked: Boolean) = binding?.also {
        it.iqoSelected.isChecked = checked
        if (!checked) {
            it.iqoOption.background.state = uncheckedState
            it.iqoTextOptionLetter.background.state = uncheckedState
            it.iqoTextOptionLetter.setTextColor(it.root.resources.getColor(R.color.create_new_exam_option_unchecked))
        }
    }

    fun changeEditability(boolean: Boolean) = binding?.also {
        it.iqoSelected.isVisible = boolean
        it.iqoImageRemoveOption.isVisible = boolean
        it.iqoTextinputOption.isEnabled = boolean
    }
}