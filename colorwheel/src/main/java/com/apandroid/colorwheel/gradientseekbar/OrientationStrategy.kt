package com.apandroid.colorwheel.gradientseekbar

import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent

internal interface OrientationStrategy {

    val gradientOrientation: GradientDrawable.Orientation

    fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect

    fun calculateBarBounds(view: GradientSeekBar): Rect

    fun calculateThumbPos(view: GradientSeekBar, bounds: Rect): PointF

    fun calculateOffset(view: GradientSeekBar, event: MotionEvent, bounds: Rect): Float
}
