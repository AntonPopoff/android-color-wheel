package com.apandroid.colorwheelapp.binding.adapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.apandroid.colorwheel.ColorWheel

@BindingAdapter("rgb")
fun ColorWheel.setRgb(rgb: Int) {
    this.rgb = rgb
}

@InverseBindingAdapter(attribute = "rgb")
fun ColorWheel.getRgb() = this.rgb

@BindingAdapter("rgbAttrChanged")
fun ColorWheel.setRgbAttributeListener(listener: InverseBindingListener) {
    this.colorChangeListener = { listener.onChange() }
}
