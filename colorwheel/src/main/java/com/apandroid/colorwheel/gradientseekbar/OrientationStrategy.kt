package com.apandroid.colorwheel.gradientseekbar

import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent

internal interface OrientationStrategy {

    val gradientOrientation: GradientDrawable.Orientation

    fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect

    fun calculateGradientBounds(view: GradientSeekBar): Rect

    fun calculateOffsetOnMotionEvent(view: GradientSeekBar, event: MotionEvent, barBounds: Rect): Float

    fun calculateThumbCoordinates(view: GradientSeekBar, barBounds: Rect): PointF
}
