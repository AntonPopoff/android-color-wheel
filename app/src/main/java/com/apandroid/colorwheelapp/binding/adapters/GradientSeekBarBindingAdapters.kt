package com.apandroid.colorwheelapp.binding.adapters

import androidx.databinding.BindingAdapter
import com.apandroid.colorwheel.gradientseekbar.GradientSeekBar
import com.apandroid.colorwheel.gradientseekbar.setAlphaListener
import com.apandroid.colorwheel.gradientseekbar.setAlphaRgb
import com.apandroid.colorwheel.gradientseekbar.setBlackToColor

interface GradientSeekBarOnColorChangedListener {

    fun onColorChanged(color: Int)
}

interface GradientSeekBarOnAlphaChangedListener {

    fun onAlphaChanged(alpha: Int)
}

@BindingAdapter("alpha")
fun GradientSeekBar.bindAlpha(argb: Int) {
    this.setAlphaRgb(argb)
}

@BindingAdapter("black")
fun GradientSeekBar.bindBlack(argb: Int) {
    this.setBlackToColor(argb)
}

@BindingAdapter("colorListener")
fun GradientSeekBar.bindOnColorChangedListener(listener: GradientSeekBarOnColorChangedListener) {
    this.listener = { _, argb -> listener.onColorChanged(argb) }
}

@BindingAdapter("alphaListener")
fun GradientSeekBar.bindAlphaChangeListener(listener: GradientSeekBarOnAlphaChangedListener) {
    this.setAlphaListener { _, _, alpha -> listener.onAlphaChanged(alpha) }
}
