package dev.logickoder.edvora.ui.main.exams

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.logickoder.edvora.R
import dev.logickoder.edvora.databinding.PartialDashboardCardBinding
import dev.logickoder.edvora.ui.base.BaseFragment
import dev.logickoder.edvora.ui.base.BaseListAdapter
import dev.logickoder.edvora.ui.main.dashboard.DashboardViewModel
import dev.logickoder.edvora.ui.main.dashboard.ExaminationItem
import dev.logickoder.edvora.utils.dp
import dev.logickoder.edvora.utils.view.viewBinding

class ExamsFragment : BaseFragment(R.layout.partial_dashboard_card) {

    override val binding by viewBinding(PartialDashboardCardBinding::bind)
    private val viewModel by viewModels<DashboardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI() = with(binding) {
        pdcTitle.text = requireContext().getString(R.string.examination)
        pdcList.layoutManager = LinearLayoutManager(requireContext())
        pdcList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                view.updateLayoutParams {
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                outRect.bottom = 24.dp(requireContext())
            }
        })
        pdcAdd.isVisible = true
        pdcAdd.setOnClickListener {
            findNavController().navigate(R.id.action_action_exams_to_createExamFragment)
        }
    }

    private fun setupObservers() = with(viewModel) {
        getExamination()
        examinations.observe(viewLifecycleOwner) { examinations ->
            binding.pdcList.adapter = BaseListAdapter().apply {
                submitList(examinations.map { ExaminationItem(it) })
            }
        }
    }
}