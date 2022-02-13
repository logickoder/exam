package dev.logickoder.edvora.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * The base activity that every activity should extend from
 */
abstract class BaseActivity : AppCompatActivity() {
    protected abstract val binding: ViewBinding
}