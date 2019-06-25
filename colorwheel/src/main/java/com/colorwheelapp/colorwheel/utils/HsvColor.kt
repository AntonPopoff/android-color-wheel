package com.colorwheelapp.colorwheel.utils

import android.graphics.Color

class HsvColor(hue: Float = 0f, saturation: Float = 0f, value: Float = 0f) {

    private val hsvComponents = floatArrayOf(
            ensureHueWithinRange(hue),
            ensureSaturationWithinRange(saturation),
            ensureValueWithinRange(value)
    )

    var hue
        get() = hsvComponents[0]
        set(hue) { hsvComponents[0] = ensureHueWithinRange(hue) }

    var saturation
        get() = hsvComponents[1]
        set(saturation) { hsvComponents[1] = ensureSaturationWithinRange(saturation) }

    var value
        get() = hsvComponents[2]
        set(value) { hsvComponents[2] = ensureValueWithinRange(value) }

    fun set(hue: Float, saturation: Float, value: Float) {
        hsvComponents[0] = ensureHueWithinRange(hue)
        hsvComponents[1] = ensureSaturationWithinRange(saturation)
        hsvComponents[2] = ensureValueWithinRange(value)
    }

    fun set(argb: Int) = Color.colorToHSV(argb, hsvComponents)

    fun toArgb() = Color.HSVToColor(hsvComponents)
}

private fun ensureHueWithinRange(hue: Float) = when {
    hue < 0f -> 0f
    else -> hue % 360
}

private fun ensureSaturationWithinRange(saturation: Float) = ensureValueWithinRange(saturation)

private fun ensureValueWithinRange(value: Float) = when {
    value < 0f -> 0f
    value > 1f -> 1f
    else -> value
}
