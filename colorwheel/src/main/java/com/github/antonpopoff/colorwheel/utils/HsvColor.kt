package com.github.antonpopoff.colorwheel.utils

import android.graphics.Color

class HsvColor(hue: Float = 0f, saturation: Float = 0f, value: Float = 0f) {

    private val hsv = floatArrayOf(
            ensureHue(hue),
            ensureSaturation(saturation),
            ensureValue(value)
    )

    var hue
        get() = hsv[0]
        set(hue) { hsv[0] = ensureHue(hue) }

    var saturation
        get() = hsv[1]
        set(saturation) { hsv[1] = ensureSaturation(saturation) }

    var value
        get() = hsv[2]
        set(value) { hsv[2] = ensureValue(value) }

    var rgb
        get() = Color.HSVToColor(hsv)
        set(rgb) { Color.colorToHSV(rgb, hsv) }

    fun set(hue: Float = hsv[0], saturation: Float = hsv[1], value: Float = hsv[2]) {
        hsv[0] = ensureHue(hue)
        hsv[1] = ensureSaturation(saturation)
        hsv[2] = ensureValue(value)
    }

    private fun ensureHue(hue: Float) = ensureWithinRange(hue, 0f, 360f)

    private fun ensureValue(value: Float) = ensureWithinRange(value, 0f, 1f)

    private fun ensureSaturation(saturation: Float) = ensureValue(saturation)
}
