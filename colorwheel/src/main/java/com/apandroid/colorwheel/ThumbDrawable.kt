package com.apandroid.colorwheel

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import com.apandroid.colorwheel.utils.ensureNumberWithinRange

internal class ThumbDrawable {

    private val backingCircleDrawable = GradientDrawable().apply { shape = GradientDrawable.OVAL }

    private val colorCircleDrawable = ShapeDrawable(OvalShape())

    var colorCircleScale = 0f
        set(value) { field = ensureNumberWithinRange(value, 0f, 1f) }

    var indicatorColor
        get() = colorCircleDrawable.paint.color
        set(value) { colorCircleDrawable.paint.color = value }

    var strokeColor = 0
        set(value) {
            field = value
            backingCircleDrawable.setStroke(1, value)
        }

    var thumbColor = 0
        set(value) {
            field = value
            backingCircleDrawable.setColor(value)
        }

    var radius = 0

    fun setBounds(bounds: Rect) {
        val inset = calculateColorCircleInset()

        backingCircleDrawable.bounds = bounds
        colorCircleDrawable.bounds = bounds.also { it.inset(inset, inset) }
    }

    fun setBounds(thumbX: Int, thumbY: Int) {
        val left = thumbX - radius
        val top = thumbY - radius
        val right = thumbX + radius
        val bottom = thumbY + radius
        val inset = calculateColorCircleInset()

        backingCircleDrawable.setBounds(left, top, right, bottom)
        colorCircleDrawable.setBounds(left + inset, top + inset, right - inset, bottom - inset)
    }

    private fun calculateColorCircleInset() = (radius - radius * colorCircleScale).toInt()

    fun draw(canvas: Canvas) {
        backingCircleDrawable.draw(canvas)
        colorCircleDrawable.draw(canvas)
    }
}
