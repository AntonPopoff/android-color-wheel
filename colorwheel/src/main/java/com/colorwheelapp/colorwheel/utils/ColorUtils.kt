package com.colorwheelapp.colorwheel.utils

fun clearAlpha(rgb: Int): Int = (rgb shl 8 ushr 8)

fun setAlpha(rgb: Int, alpha: Int) = clearAlpha(rgb) or (alpha shl 24)

fun getAlpha(rgb: Int) = rgb ushr 24
