package com.apandroid.colorwheelapp.binding.adapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.apandroid.colorwheel.ColorWheel

@BindingAdapter("rgb")
fun ColorWheel.bindRgb(rgb: Int) {
    if (this.rgb != rgb) this.rgb = rgb
}

@InverseBindingAdapter(attribute = "rgb")
fun ColorWheel.receiveRgb() = this.rgb

@BindingAdapter("rgbAttrChanged")
fun ColorWheel.bindRgbAttributeListener(listener: InverseBindingListener) {
    this.colorChangeListener = { listener.onChange() }
}
