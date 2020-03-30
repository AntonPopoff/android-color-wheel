package com.apandroid.colorwheel

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape

internal class ThumbDrawable {

    private val thumbCircleDrawable = GradientDrawable().apply { shape = GradientDrawable.OVAL }
    private val colorIndicatorDrawable = ShapeDrawable(OvalShape())

    var indicatorColor
        get() = colorIndicatorDrawable.paint.color
        set(value) { colorIndicatorDrawable.paint.color = value }

    fun setStrokeColor(argb: Int) {
        thumbCircleDrawable.setStroke(1, argb)
    }

    fun setThumbColor(argb: Int) {
        thumbCircleDrawable.setColor(argb)
    }

    fun setBounds(bounds: Rect, thumbRadius: Int) {
        val inset = (thumbRadius * 0.3f).toInt()

        thumbCircleDrawable.bounds = bounds
        colorIndicatorDrawable.bounds = bounds.also { it.inset(inset, inset) }
    }

    fun setBounds(thumbX: Int, thumbY: Int, thumbRadius: Int) {
        val left = thumbX - thumbRadius
        val top = thumbY - thumbRadius
        val right = thumbX + thumbRadius
        val bottom = thumbY + thumbRadius
        val inset = (thumbRadius * 0.3f).toInt()

        thumbCircleDrawable.setBounds(left, top, right, bottom)
        colorIndicatorDrawable.setBounds(left + inset, top + inset, right - inset, bottom - inset)
    }

    fun draw(canvas: Canvas) {
        thumbCircleDrawable.draw(canvas)
        colorIndicatorDrawable.draw(canvas)
    }
}
