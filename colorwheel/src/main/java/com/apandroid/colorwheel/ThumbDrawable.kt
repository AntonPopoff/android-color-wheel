package com.apandroid.colorwheel

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape

internal class ThumbDrawable {

    private val backingCircleDrawable = GradientDrawable().apply { shape = GradientDrawable.OVAL }
    private val colorCircleDrawable = ShapeDrawable(OvalShape())

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
        val inset = (thumbRadius * 0.3f).toInt()

        backingCircleDrawable.bounds = bounds
        colorCircleDrawable.bounds = bounds.also { it.inset(inset, inset) }
    }

    fun setBounds(thumbX: Int, thumbY: Int, thumbRadius: Int) {
        val left = thumbX - thumbRadius
        val top = thumbY - thumbRadius
        val right = thumbX + thumbRadius
        val bottom = thumbY + thumbRadius
        val inset = (thumbRadius * 0.3f).toInt()

        backingCircleDrawable.setBounds(left, top, right, bottom)
        colorCircleDrawable.setBounds(left + inset, top + inset, right - inset, bottom - inset)
    }

    fun draw(canvas: Canvas) {
        backingCircleDrawable.draw(canvas)
        colorCircleDrawable.draw(canvas)
    }
}
