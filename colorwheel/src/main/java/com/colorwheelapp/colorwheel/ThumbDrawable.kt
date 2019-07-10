package com.colorwheelapp.colorwheel

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape

internal class ThumbDrawable {

    private val thumbCircle = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setStroke(1, Color.GRAY)
        setColor(Color.WHITE)
    }

    private val colorIndicatorDrawable = ShapeDrawable(OvalShape())

    private val thumbDrawable = LayerDrawable(arrayOf(thumbCircle, colorIndicatorDrawable))

    var indicatorColor
        get() = colorIndicatorDrawable.paint.color
        set(value) { colorIndicatorDrawable.paint.color = value }

    var bounds
        get() = thumbDrawable.bounds
        set(bounds) { thumbDrawable.bounds = bounds }

    fun applyInsets(thumbRadius: Float) {
        val colorHInset = (thumbRadius * 0.3f).toInt()
        val colorVInset = (thumbRadius * 0.3f).toInt()
        thumbDrawable.setLayerInset(1, colorHInset, colorVInset, colorHInset, colorVInset)
    }

    fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        thumbDrawable.setBounds(left, top, right, bottom)
    }

    fun draw(canvas: Canvas) = thumbDrawable.draw(canvas)
}
