package dev.logickoder.edvora.ui.main.exams

import androidx.viewbinding.ViewBinding
import dev.logickoder.edvora.R
import dev.logickoder.edvora.databinding.FragmentExamsBinding
import dev.logickoder.edvora.ui.base.BaseFragment
import dev.logickoder.edvora.utils.view.viewBinding

class ExamsFragment : BaseFragment(R.layout.fragment_exams) {

    override val binding by viewBinding(FragmentExamsBinding::bind)
}