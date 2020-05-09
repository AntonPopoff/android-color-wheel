package com.apandroid.colorwheelapp.binding.converters

import android.widget.SeekBar
import androidx.databinding.InverseMethod
import kotlin.math.roundToInt

@InverseMethod("convertProgressToFloatRatio")
fun convertFloatRatioToProgress(view: SeekBar, value: Float) = (value * view.max).roundToInt()

fun convertProgressToFloatRatio(view: SeekBar, value: Int): Float = view.progress / view.max.toFloat()
