package com.silverstar.mvvmtodoapp.ui.util

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


@BindingAdapter("isVisible")
fun FloatingActionButton.setVisibility(isVisible: Boolean) {
    if (isVisible) show() else hide()
}