package com.colorwheelapp.colorwheel.utils

const val MAX_ALPHA = 255

fun clearAlpha(rgb: Int): Int = (rgb shl 8 ushr 8)

fun setAlpha(rgb: Int, alpha: Int) = clearAlpha(rgb) or (alpha shl 24)

fun getAlpha(rgb: Int) = rgb ushr 24

fun ensureAlphaWithinRange(alpha: Int) = when {
    alpha < 0 -> 0
    alpha > MAX_ALPHA -> MAX_ALPHA
    else -> alpha
}
