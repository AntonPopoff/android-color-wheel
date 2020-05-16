package com.apandroid.colorwheelapp.binding.converters

import androidx.databinding.InverseMethod
import com.apandroid.colorwheel.gradientseekbar.GradientSeekBar
import com.apandroid.colorwheelapp.R
import kotlin.math.roundToInt

@InverseMethod("convertProgressToFloatRatio")
fun convertFloatRatioToProgress(max: Int, value: Float) = (value * max).roundToInt()

fun convertProgressToFloatRatio(max: Int, value: Int) = value / max.toFloat()

fun convertIntToFloat(value: Int) = value.toFloat()

@InverseMethod("convertIdToOrientation")
fun convertOrientationToId(orientation: GradientSeekBar.Orientation) = when (orientation) {
    GradientSeekBar.Orientation.VERTICAL -> R.id.verticalOrientationButton
    GradientSeekBar.Orientation.HORIZONTAL -> R.id.horizontalOrientationButton
}

fun convertIdToOrientation(id: Int) = when (id) {
    R.id.verticalOrientationButton -> GradientSeekBar.Orientation.VERTICAL
    R.id.horizontalOrientationButton -> GradientSeekBar.Orientation.HORIZONTAL
    else -> GradientSeekBar.Orientation.VERTICAL
}
