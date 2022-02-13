package dev.logickoder.edvora.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base implementation of the view holder
 */
class BaseViewHolder<T : ViewBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)