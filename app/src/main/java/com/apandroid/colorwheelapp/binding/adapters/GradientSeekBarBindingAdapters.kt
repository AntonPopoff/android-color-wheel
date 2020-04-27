package com.apandroid.colorwheelapp.binding.adapters

import androidx.databinding.BindingAdapter
import com.apandroid.colorwheel.gradientseekbar.GradientSeekBar
import com.apandroid.colorwheel.gradientseekbar.setAlphaListener
import com.apandroid.colorwheel.gradientseekbar.setBlackToColor
import com.apandroid.colorwheel.gradientseekbar.setTransparentToColor

interface GradientSeekBarOnColorChangedListener {

    fun onColorChanged(color: Int)
}

interface GradientSeekBarOnAlphaChangedListener {

    fun onAlphaChanged(alpha: Int)
}

@BindingAdapter("transparentToColor")
fun GradientSeekBar.bindTransparentToColor(argb: Int) {
    this.setTransparentToColor(argb, false)
}

@BindingAdapter("blackToColor")
fun GradientSeekBar.bindBlackToColor(argb: Int) {
    this.setBlackToColor(argb)
}

@BindingAdapter("colorListener")
fun GradientSeekBar.bindOnColorChangedListener(listener: GradientSeekBarOnColorChangedListener) {
    this.colorListener = { _, argb -> listener.onColorChanged(argb) }
}

@BindingAdapter("alphaListener")
fun GradientSeekBar.bindAlphaChangeListener(listener: GradientSeekBarOnAlphaChangedListener) {
    this.setAlphaListener { _, _, alpha -> listener.onAlphaChanged(alpha) }
}
