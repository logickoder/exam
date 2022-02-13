package dev.logickoder.edvora.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dev.logickoder.edvora.R
import dev.logickoder.edvora.databinding.ActivityMainBinding
import dev.logickoder.edvora.ui.base.BaseActivity
import dev.logickoder.edvora.utils.view.viewBinding

class MainActivity : BaseActivity() {

    override val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.appBottomNavigation.setupWithNavController(
            findNavController(R.id.nav_host_fragment)
        )
    }
}