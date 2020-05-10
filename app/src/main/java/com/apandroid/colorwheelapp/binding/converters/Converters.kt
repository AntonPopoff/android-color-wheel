package com.apandroid.colorwheelapp.binding.converters

import androidx.databinding.InverseMethod
import kotlin.math.roundToInt

@InverseMethod("convertProgressToFloatRatio")
fun convertFloatRatioToProgress(max: Int, value: Float) = (value * max).roundToInt()

fun convertProgressToFloatRatio(max: Int, value: Int) = value / max.toFloat()

@InverseMethod("convertIntToString")
fun convertStringToInt(value: String) = value.toIntOrNull() ?: 0

fun convertIntToString(value: Int) = value.toString()

@InverseMethod("convertFloatToString")
fun convertStringToFloat(value: String) = value.toFloatOrNull() ?: 0f

fun convertFloatToString(value: Float) = value.toString()
