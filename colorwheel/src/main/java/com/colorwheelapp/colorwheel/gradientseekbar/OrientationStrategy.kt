package com.colorwheelapp.colorwheel.gradientseekbar

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import com.colorwheelapp.colorwheel.utils.ViewDimensions

internal interface OrientationStrategy {

    val gradientOrientation: GradientDrawable.Orientation

    fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): ViewDimensions

    fun calculateGradientBounds(view: GradientSeekBar): Rect

    fun calculateThumbBounds(view: GradientSeekBar, barBounds: Rect): Rect

    fun calculateOffsetOnMotionEvent(view: GradientSeekBar, event: MotionEvent, barBounds: Rect): Float
}
