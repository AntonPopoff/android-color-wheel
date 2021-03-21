package com.github.antonpopoff.colorwheelapp.binding.adapters

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("gradientDrawableColor")
fun View.bindBackgroundColor(color: Int) {
    (this.background as? GradientDrawable)?.setColor(color)
}
