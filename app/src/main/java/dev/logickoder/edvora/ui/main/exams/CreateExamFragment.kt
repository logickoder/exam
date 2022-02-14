package dev.logickoder.edvora.ui.main.exams

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dev.logickoder.edvora.R
import dev.logickoder.edvora.databinding.FragmentCreateExamBinding
import dev.logickoder.edvora.ui.base.BaseFragment
import dev.logickoder.edvora.ui.base.BaseListAdapter
import dev.logickoder.edvora.utils.view.viewBinding

class CreateExamFragment : BaseFragment(R.layout.fragment_create_exam) {

    override val binding by viewBinding(FragmentCreateExamBinding::bind)
    private val questions by lazy { BaseListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() = with(binding) {
        fceQuestions.pceqQuestionsList.apply {
            adapter = questions.also { it.submitList(listOf(QuestionItem())) }
        }
    }
}