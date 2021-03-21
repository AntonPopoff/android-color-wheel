package com.github.antonpopoff.colorwheel.gradientseekbar

import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent

internal interface OrientationStrategy {

    val gradientOrientation: GradientDrawable.Orientation

    fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect

    fun getGradientBounds(view: GradientSeekBar): Rect

    fun getThumbPosition(view: GradientSeekBar, gradient: Rect): PointF

    fun getOffset(view: GradientSeekBar, event: MotionEvent, gradient: Rect): Float
}
