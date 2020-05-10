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

@BindingAdapter("thumbRadius")
fun ColorWheel.bindThumbRadius(radius: Int) {
    if (this.thumbRadius != radius) this.thumbRadius = radius
}

@BindingAdapter("thumbColorCircleScale")
fun ColorWheel.bindThumbColorCircleScale(scale: Float) {
    if (this.thumbColorCircleScale != scale) this.thumbColorCircleScale = scale
}
