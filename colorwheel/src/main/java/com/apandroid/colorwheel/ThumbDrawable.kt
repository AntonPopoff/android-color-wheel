package com.apandroid.colorwheel

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import com.apandroid.colorwheel.utils.ensureNumberWithinRange

internal class ThumbDrawable {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { strokeWidth = 1f }
    private var x = 0f
    private var y = 0f

    var indicatorColor = 0
    var strokeColor = 0
    var thumbColor = 0
    var radius = 0

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
        canvas.drawCircle(x, y, radius.toFloat(), paint)
    }

    private fun drawStroke(canvas: Canvas) {
        val strokeCircleRadius = radius - paint.strokeWidth / 2f

        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(x, y, strokeCircleRadius, paint)
    }

    private fun drawColorIndicator(canvas: Canvas) {
        val colorIndicatorCircleRadius = radius * colorCircleScale

        paint.color = indicatorColor
        paint.style = Paint.Style.FILL
        canvas.drawCircle(x, y, colorIndicatorCircleRadius, paint)
    }

    fun restoreState(parcelable: Parcelable) {
        (parcelable as? ThumbDrawableState)?.let {
            radius = it.radius
            thumbColor = it.thumbColor
            strokeColor = it.strokeColor
            colorCircleScale = it.colorCircleScale
        }
    }

    fun saveState(): Parcelable = ThumbDrawableState(this)
}

private class ThumbDrawableState(
    val radius: Int,
    val thumbColor: Int,
    val strokeColor: Int,
    val colorCircleScale: Float
) : Parcelable {

    constructor(thumbDrawable: ThumbDrawable) : this(
        thumbDrawable.radius,
        thumbDrawable.thumbColor,
        thumbDrawable.strokeColor,
        thumbDrawable.colorCircleScale
    )

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(radius)
        parcel.writeInt(thumbColor)
        parcel.writeInt(strokeColor)
        parcel.writeFloat(colorCircleScale)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ThumbDrawableState> {

        override fun createFromParcel(parcel: Parcel) = ThumbDrawableState(parcel)

        override fun newArray(size: Int) = arrayOfNulls<ThumbDrawableState>(size)
    }
}
