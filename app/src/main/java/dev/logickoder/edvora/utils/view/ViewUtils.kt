package dev.logickoder.edvora.utils.view

import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.Group

fun View.layoutInflater(): LayoutInflater = LayoutInflater.from(context)

fun Group.setAllOnClickListener(listener: View.OnClickListener) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}
 