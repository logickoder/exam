package dev.logickoder.edvora.ui.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * The base class for fragments
 */
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected abstract val binding: ViewBinding
}