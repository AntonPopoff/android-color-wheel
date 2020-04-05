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

    fun setStrokeColor(argb: Int) {
        backingCircleDrawable.setStroke(1, argb)
    }

    fun setThumbColor(argb: Int) {
        backingCircleDrawable.setColor(argb)
    }

    fun setBounds(bounds: Rect, thumbRadius: Int) {
        val inset = calculateColorCircleInset(thumbRadius)

        backingCircleDrawable.bounds = bounds
        colorCircleDrawable.bounds = bounds.also { it.inset(inset, inset) }
    }

    fun setBounds(thumbX: Int, thumbY: Int, thumbRadius: Int) {
        val left = thumbX - thumbRadius
        val top = thumbY - thumbRadius
        val right = thumbX + thumbRadius
        val bottom = thumbY + thumbRadius
        val inset = calculateColorCircleInset(thumbRadius)

        backingCircleDrawable.setBounds(left, top, right, bottom)
        colorCircleDrawable.setBounds(left + inset, top + inset, right - inset, bottom - inset)
    }

    private fun calculateColorCircleInset(thumbRadius: Int) = (thumbRadius - thumbRadius * colorCircleScale).toInt()

    fun draw(canvas: Canvas) {
        backingCircleDrawable.draw(canvas)
        colorCircleDrawable.draw(canvas)
    }
}
