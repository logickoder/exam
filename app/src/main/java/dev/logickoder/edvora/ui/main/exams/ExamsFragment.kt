package dev.logickoder.edvora.ui.main.exams

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dev.logickoder.edvora.R
import dev.logickoder.edvora.databinding.FragmentExamsBinding
import dev.logickoder.edvora.ui.base.BaseFragment
import dev.logickoder.edvora.utils.view.viewBinding

class ExamsFragment : BaseFragment(R.layout.fragment_exams) {

    override val binding by viewBinding(FragmentExamsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().navigate(R.id.action_examsFragment_to_createExamFragment)
    }
}