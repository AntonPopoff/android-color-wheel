package com.apandroid.colorwheel.gradientseekbar

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import com.apandroid.colorwheel.ThumbDrawable

internal interface OrientationStrategy {

    val gradientOrientation: GradientDrawable.Orientation

    fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect

    fun calculateGradientBounds(view: GradientSeekBar): Rect

    fun calculateThumbBounds(view: GradientSeekBar, thumbDrawable: ThumbDrawable, barBounds: Rect)

    fun calculateOffsetOnMotionEvent(view: GradientSeekBar, event: MotionEvent, barBounds: Rect): Float
}
