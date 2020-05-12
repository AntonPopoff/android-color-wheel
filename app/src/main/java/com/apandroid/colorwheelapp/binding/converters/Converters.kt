package com.apandroid.colorwheelapp.binding.converters

import androidx.databinding.InverseMethod
import kotlin.math.roundToInt

@InverseMethod("convertProgressToFloatRatio")
fun convertFloatRatioToProgress(max: Int, value: Float) = (value * max).roundToInt()

fun convertProgressToFloatRatio(max: Int, value: Int) = value / max.toFloat()

fun convertIntToFloat(value: Int) = value.toFloat()
