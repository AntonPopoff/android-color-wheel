package com.colorwheelapp.colorwheel.utils

import android.graphics.Color
import kotlin.math.roundToInt

const val MAX_ALPHA = 255

fun clearAlpha(rgb: Int): Int = (rgb shl 8 ushr 8)

fun setAlphaComponent(rgb: Int, alpha: Int) = clearAlpha(rgb) or (alpha shl 24)

fun interpolateColorLinear(startColor: Int, endColor: Int, offset: Float) = Color.argb(
    (Color.alpha(startColor) + offset * (Color.alpha(endColor) - Color.alpha(startColor))).roundToInt(),
    (Color.red(startColor) + offset * (Color.red(endColor) - Color.red(startColor))).roundToInt(),
    (Color.green(startColor) + offset * (Color.green(endColor) - Color.green(startColor))).roundToInt(),
    (Color.blue(startColor) + offset * (Color.blue(endColor) - Color.blue(startColor))).roundToInt()
)
