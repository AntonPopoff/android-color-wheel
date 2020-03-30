package com.apandroid.colorwheel

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape

internal class ThumbDrawable {

    private val thumbCircle = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setStroke(1, Color.GRAY)
        setColor(Color.WHITE)
    }

    private val colorIndicatorDrawable = ShapeDrawable(OvalShape())

    private var inset = 0

    var indicatorColor
        get() = colorIndicatorDrawable.paint.color
        set(value) { colorIndicatorDrawable.paint.color = value }

    var bounds
        get() = thumbCircle.bounds
        set(bounds) { setBounds(bounds.left, bounds.top, bounds.right, bounds.bottom) }

    fun setStrokeColor(argb: Int) {
        thumbCircle.setStroke(1, argb)
    }

    fun setThumbColor(argb: Int) {
        thumbCircle.setColor(argb)
    }

    fun calculateThumbInset(radius: Float) {
        inset = (radius * 0.3f).toInt()
    }

    fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        thumbCircle.setBounds(left, top, right, bottom)
        colorIndicatorDrawable.setBounds(left + inset, top + inset, right - inset, bottom - inset)
    }

    fun draw(canvas: Canvas) {
        thumbCircle.draw(canvas)
        colorIndicatorDrawable.draw(canvas)
    }
}
