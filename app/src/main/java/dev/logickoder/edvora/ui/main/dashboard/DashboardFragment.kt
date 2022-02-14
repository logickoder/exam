package dev.logickoder.edvora.ui.main.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import dev.logickoder.edvora.R
import dev.logickoder.edvora.databinding.FragmentDashboardBinding
import dev.logickoder.edvora.ui.base.BaseFragment
import dev.logickoder.edvora.ui.base.BaseListAdapter
import dev.logickoder.edvora.utils.dp
import dev.logickoder.edvora.utils.view.viewBinding

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val binding by viewBinding(FragmentDashboardBinding::bind)
    private val viewModel by viewModels<DashboardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI(): Unit = with(binding) {
        val context = requireContext()
        fdExamination.apply {
            pdcTitle.text = context.getString(R.string.examination)
            pdcList.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            pdcAdd.isVisible = true
            pdcAdd.setOnClickListener {
                findNavController().navigate(R.id.action_action_dashboard_to_createExamFragment)
            }
        }
        fdAttendance.apply {
            pdcTitle.text = context.getString(R.string.attendance)
            pdcList.layoutManager = LinearLayoutManager(context)
            pdcList.updatePadding(right = 24.dp(context))
        }
    }

    private fun setupObservers() = with(viewModel) {
        user.observe(viewLifecycleOwner) {
            binding.fdTextGreeting.text =
                binding.root.context.getString(R.string.hello_name, it.name)
        }
        examinations.observe(viewLifecycleOwner) { examinations ->
            binding.fdExamination.pdcList.adapter = BaseListAdapter().apply {
                submitList(examinations.map { ExaminationItem(it) })
            }
        }
        attendance.observe(viewLifecycleOwner) { attendance ->
            binding.fdAttendance.pdcList.adapter = BaseListAdapter().apply {
                submitList(attendance.filter { it.second != null }.map {
                    AttendanceItem(it.second!!, it.first)
                })
            }
        }
    }
}