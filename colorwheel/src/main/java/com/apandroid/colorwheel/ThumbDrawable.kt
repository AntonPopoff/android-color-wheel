package com.apandroid.colorwheel

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.apandroid.colorwheel.utils.ensureNumberWithinRange

internal class ThumbDrawable {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { strokeWidth = 1f }

    var indicatorColor = 0

    var strokeColor = 0

    var thumbColor = 0

    var radius = 0

    var bounds = Rect()
        set(value) { field.set(value) }

    var colorCircleScale = 0f
        set(value) { field = ensureNumberWithinRange(value, 0f, 1f) }

    fun setBounds(thumbX: Int, thumbY: Int) {
        bounds.set(thumbX - radius, thumbY - radius, thumbX + radius, thumbY + radius)
    }

    fun draw(canvas: Canvas) {
        val cx = bounds.exactCenterX()
        val cy = bounds.exactCenterY()

        drawThumb(canvas, cx, cy)
        drawStroke(canvas, cx, cy)
        drawColorIndicator(canvas, cx, cy)
    }

    private fun drawThumb(canvas: Canvas, cx: Float, cy: Float) {
        paint.color = thumbColor
        paint.style = Paint.Style.FILL
        canvas.drawCircle(cx, cy, radius.toFloat(), paint)
    }

    private fun drawStroke(canvas: Canvas, cx: Float, cy: Float) {
        val strokeCircleRadius = radius - paint.strokeWidth / 2f

        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(cx, cy, strokeCircleRadius, paint)
    }

    private fun drawColorIndicator(canvas: Canvas, cx: Float, cy: Float) {
        val colorIndicatorCircleRadius = radius * colorCircleScale

        paint.color = indicatorColor
        paint.style = Paint.Style.FILL
        canvas.drawCircle(cx, cy, colorIndicatorCircleRadius, paint)
    }
}
