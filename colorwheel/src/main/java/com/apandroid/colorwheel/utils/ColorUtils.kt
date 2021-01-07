package com.apandroid.colorwheel.utils

import android.graphics.Color
import kotlin.math.roundToInt

internal fun setColorAlpha(argb: Int, alpha: Int) =
    Color.argb(alpha, Color.red(argb), Color.green(argb), Color.blue(argb))

internal fun interpolateColorLinear(startColor: Int, endColor: Int, offset: Float): Int {
    val a = (Color.alpha(startColor) +
            offset * (Color.alpha(endColor) - Color.alpha(startColor))).roundToInt()
    val r = (Color.red(startColor) +
            offset * (Color.red(endColor) - Color.red(startColor))).roundToInt()
    val g = (Color.green(startColor) +
            offset * (Color.green(endColor) - Color.green(startColor))).roundToInt()
    val b = (Color.blue(startColor) +
            offset * (Color.blue(endColor) - Color.blue(startColor))).roundToInt()
    return Color.argb(a, r, g, b)
}
