package com.colorwheelapp.colorwheel.alphaseekbar

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import com.colorwheelapp.colorwheel.utils.ViewDimensions

interface AlphaSeekBarOrientationStrategy {

    val gradientOrientation: GradientDrawable.Orientation

    fun measure(view: AlphaSeekBar, widthSpec: Int, heightSpec: Int): ViewDimensions

    fun calculateGradientBounds(view: AlphaSeekBar): Rect

    fun calculateThumbBounds(view: AlphaSeekBar, barBounds: Rect): Rect

    fun calculateAlphaOnMotionEvent(view: AlphaSeekBar, event: MotionEvent, barBounds: Rect): Int
}
