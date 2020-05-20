package com.apandroid.colorwheel.thumb

import android.graphics.Canvas
import android.graphics.Paint
import com.apandroid.colorwheel.utils.ensureNumberWithinRange

internal class ThumbDrawable {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { strokeWidth = 1f }
    private var x = 0f
    private var y = 0f

    var indicatorColor = 0
    var strokeColor = 0
    var thumbColor = 0
    var radius = 0
    var shadowColor = 0
    var shadowRadius = 0f

    var colorCircleScale = 0f
        set(value) { field = ensureNumberWithinRange(value, 0f, 1f) }

    fun setCoordinates(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun draw(canvas: Canvas) {
        drawThumb(canvas)
        drawStroke(canvas)
        drawColorIndicator(canvas)
    }

    private fun drawThumb(canvas: Canvas) {
        paint.color = thumbColor
        paint.style = Paint.Style.FILL
        paint.setShadowLayer(shadowRadius, shadowRadius * 0.3f, shadowRadius * 0.5f, shadowColor)
        canvas.drawCircle(x, y, radius.toFloat(), paint)
    }

    private fun drawStroke(canvas: Canvas) {
        val strokeCircleRadius = radius - paint.strokeWidth / 2f

        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        paint.setShadowLayer(0f, 0f, 0f, 0)
        canvas.drawCircle(x, y, strokeCircleRadius, paint)
    }

    private fun drawColorIndicator(canvas: Canvas) {
        val colorIndicatorCircleRadius = radius * colorCircleScale

        paint.color = indicatorColor
        paint.style = Paint.Style.FILL
        paint.setShadowLayer(0f, 0f, 0f, 0)
        canvas.drawCircle(x, y, colorIndicatorCircleRadius, paint)
    }

    fun restoreState(state: ThumbDrawableState) {
        radius = state.radius
        thumbColor = state.thumbColor
        strokeColor = state.strokeColor
        colorCircleScale = state.colorCircleScale
        shadowColor = state.colorShadow
        shadowRadius = state.shadowRadius
    }

    fun saveState() = ThumbDrawableState(this)
}
