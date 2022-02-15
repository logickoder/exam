package dev.logickoder.edvora.ui.main.exams

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dev.logickoder.edvora.R
import dev.logickoder.edvora.data.Classroom
import dev.logickoder.edvora.databinding.FragmentCreateExamBinding
import dev.logickoder.edvora.ui.base.BaseFragment
import dev.logickoder.edvora.ui.base.BaseListAdapter
import dev.logickoder.edvora.utils.view.viewBinding

class CreateExamFragment : BaseFragment(R.layout.fragment_create_exam), QuestionsContainer {

    override val binding by viewBinding(FragmentCreateExamBinding::bind)
    private val viewModel by viewModels<CreateExamsViewModel>()
    private val questions by lazy { BaseListAdapter() }
    private val textViewsPcei by lazy {
        binding.fceInfo.run {
            listOf(
                pceiTextinputCategory, pceiTextinputDate, pceiTextinputTime, pceiTextinputDuration,
                pceiTextinputSyllabus, pceiTextinputTimeframe, pceiTextinputTotalmarks
            )
        }
    }
    private val textViewsPceq by lazy {
        binding.fceQuestions.run {
            listOf(
                pceqTextinputInstructions,
                pceqTextinputSectionTitle,
                pceqTextinputSectionDescription
            )
        }
    }

    private var start: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.restore(PCEI).forEachIndexed { index, text ->
            textViewsPcei[index].editText!!.setText(text)
        }
        viewModel.restore(PCEQ).forEachIndexed { index, text ->
            textViewsPceq[index].editText!!.setText(text)
        }
    }

    override fun onPause() {
        save()
        super.onPause()
    }

    override fun removeQuestion(question: QuestionItem) {
        questions.submitList(questions.currentList - question)
    }

    private fun setupUI() = with(binding) {
        fceToolbar.apply {
            ptImageBack.setOnClickListener {
                when (start) {
                    false -> {
                        ptButtonNext.text = getString(R.string.next)
                        fceInfo.root.isVisible = true
                        fceQuestions.root.isVisible = false
                    }
                    true -> findNavController().navigateUp()
                }
                start = !start
            }
            ptButtonNext.setOnClickListener {
                when (start) {
                    true -> {
                        ptButtonNext.text = getString(R.string.save)
                        fceInfo.root.isVisible = false
                        fceQuestions.root.isVisible = true
                    }
                    false -> {
                        save()
                        viewModel.save()
                        findNavController().navigateUp()
                    }
                }
                start = !start
            }
        }
        fceQuestions.pceqQuestionsList.apply {
            adapter = questions.also { addQuestion(QuestionItem(this@CreateExamFragment)) }
        }
        fceQuestions.pceqButtonAddQuestion.setOnClickListener {
            addQuestion(QuestionItem(this@CreateExamFragment))
        }
    }

    private fun setupObservers() = with(viewModel) {
        classrooms.observe(viewLifecycleOwner) {
            (binding.fceInfo.pceiTextinputClassroom.editText as? AutoCompleteTextView)!!.also { classrooms ->
                classrooms.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        it
                    )
                )
            }.setOnItemClickListener { adapterView, _, position, _ ->
                classroom = adapterView.getItemAtPosition(position) as Classroom
            }
        }
    }

    private fun addQuestion(question: QuestionItem) {
        questions.submitList(questions.currentList + question)
    }

    private fun save() {
        viewModel.save(PCEI, textViewsPcei.map { it.editText!!.text.toString() })
        viewModel.save(PCEQ, textViewsPceq.map { it.editText!!.text.toString() })
    }

    companion object {
        const val PCEQ = "pceq"
        const val PCEI = "pcei"
    }
}