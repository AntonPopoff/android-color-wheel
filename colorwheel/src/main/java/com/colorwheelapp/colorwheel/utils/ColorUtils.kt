package com.colorwheelapp.colorwheel.utils

import android.graphics.Color
import kotlin.math.roundToInt

const val MAX_ALPHA = 255

fun clearAlpha(rgb: Int): Int = (rgb shl 8 ushr 8)

fun setAlphaComponent(rgb: Int, alpha: Int) = clearAlpha(rgb) or (alpha shl 24)

fun interpolateColorLinear(startColor: Int, endColor: Int, offset: Float): Int {
    val a = (Color.alpha(startColor) + offset * (Color.alpha(endColor) - Color.alpha(startColor))).roundToInt()
    val r = (Color.red(startColor) + offset * (Color.red(endColor) - Color.red(startColor))).roundToInt()
    val g = (Color.green(startColor) + offset * (Color.green(endColor) - Color.green(startColor))).roundToInt()
    val b = (Color.blue(startColor) + offset * (Color.blue(endColor) - Color.blue(startColor))).roundToInt()
    return Color.argb(a, r, g, b)
}
