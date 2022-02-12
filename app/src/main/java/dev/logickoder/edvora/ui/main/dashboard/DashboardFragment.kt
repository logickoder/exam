package dev.logickoder.edvora.ui.main.dashboard

import dev.logickoder.edvora.R
import dev.logickoder.edvora.databinding.FragmentDashboardBinding
import dev.logickoder.edvora.ui.base.BaseFragment
import dev.logickoder.edvora.utils.view.viewBinding

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    override val binding by viewBinding(FragmentDashboardBinding::bind)
}