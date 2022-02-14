package dev.logickoder.edvora.ui.main.exams

import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import dev.logickoder.edvora.R
import dev.logickoder.edvora.data.Question
import dev.logickoder.edvora.databinding.ItemQuestionBinding
import dev.logickoder.edvora.databinding.ItemQuestionOptionBinding
import dev.logickoder.edvora.ui.base.BaseItem
import dev.logickoder.edvora.ui.base.BaseListAdapter
import dev.logickoder.edvora.utils.view.layoutInflater

interface OptionsContainer {
    fun addOption()
    fun updateChecked(option: OptionItem)
}

class QuestionItem(
) : BaseItem<Unit, ItemQuestionBinding>(
    Unit,
    R.layout.item_question,
    R.layout.item_question
), OptionsContainer {
    private val options by lazy { BaseListAdapter() }

    private lateinit var _question: Question
    val question: Question get() = _question

    override fun inflate(parent: ViewGroup) = ItemQuestionBinding.inflate(
        parent.layoutInflater(), parent, false
    )

    override fun bind(binding: ItemQuestionBinding): Unit = with(binding) {
        iqListOptions.adapter = options.also { addOption() }
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
        options.notifyDataSetChanged()
    }

    companion object {
        const val A = 'A'
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
            it.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    container.addOption()
                    true
                } else false
            }
        }
        iqoImageRemoveOption.setOnClickListener { container.removeOption(this@OptionItem) }
        iqoSelected.setOnClickListener {
            if (iqoSelected.isChecked) container.updateChecked(this@OptionItem)
        }
    }

    fun updateCheck(checked: Boolean) = binding?.also {
        it.iqoSelected.checked = checked
    }
}