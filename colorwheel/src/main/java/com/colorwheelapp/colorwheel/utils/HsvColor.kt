package com.colorwheelapp.colorwheel.utils

import android.graphics.Color

class HsvColor(hue: Float = 0f, saturation: Float = 0f, value: Float = 0f) {

    private val hsv = floatArrayOf(
            ensureHueWithinRange(hue),
            ensureSaturationWithinRange(saturation),
            ensureValueWithinRange(value)
    )

    var hue
        get() = hsv[0]
        set(hue) { hsv[0] = ensureHueWithinRange(hue) }

    var saturation
        get() = hsv[1]
        set(saturation) { hsv[1] = ensureSaturationWithinRange(saturation) }

    var value
        get() = hsv[2]
        set(value) { hsv[2] = ensureValueWithinRange(value) }

    fun set(hue: Float, saturation: Float, value: Float) {
        hsv[0] = ensureHueWithinRange(hue)
        hsv[1] = ensureSaturationWithinRange(saturation)
        hsv[2] = ensureValueWithinRange(value)
    }

    fun set(rgb: Int) {
        Color.colorToHSV(rgb, hsv)
        value = 1f
    }

    fun set(r: Int, g: Int, b: Int) {
        Color.RGBToHSV(r, g, b, hsv)
        value = 1f
    }

    fun toRgb() = Color.HSVToColor(hsv)
}

private fun ensureHueWithinRange(hue: Float) = when {
    hue < 0f -> 0f
    else -> hue % 360
}

private fun ensureValueWithinRange(value: Float) = when {
    value < 0f -> 0f
    value > 1f -> 1f
    else -> value
}

private fun ensureSaturationWithinRange(saturation: Float) = ensureValueWithinRange(saturation)
