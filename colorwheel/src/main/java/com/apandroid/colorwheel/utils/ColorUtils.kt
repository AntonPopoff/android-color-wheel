package com.apandroid.colorwheel.utils

import android.graphics.Color
import kotlin.math.roundToInt

internal const val MAX_ALPHA = 255

internal fun interpolateColorLinear(startColor: Int, endColor: Int, offset: Float) = Color.argb(
    (Color.alpha(startColor) + offset * (Color.alpha(endColor) - Color.alpha(startColor))).roundToInt(),
    (Color.red(startColor) + offset * (Color.red(endColor) - Color.red(startColor))).roundToInt(),
    (Color.green(startColor) + offset * (Color.green(endColor) - Color.green(startColor))).roundToInt(),
    (Color.blue(startColor) + offset * (Color.blue(endColor) - Color.blue(startColor))).roundToInt()
)

internal fun setColorAlpha(argb: Int, alpha: Int) = Color.argb(
    alpha,
    Color.red(argb),
    Color.green(argb),
    Color.blue(argb)
)
