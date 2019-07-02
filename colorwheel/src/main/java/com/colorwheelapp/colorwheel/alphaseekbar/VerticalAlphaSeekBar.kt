package com.colorwheelapp.colorwheel.alphaseekbar

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import com.colorwheelapp.colorwheel.utils.MAX_ALPHA
import com.colorwheelapp.colorwheel.utils.ViewDimensions
import kotlin.math.roundToInt

class VerticalAlphaSeekBar : AlphaSeekBarOrientationStrategy {

    private val rect = Rect()

    private val dimens = ViewDimensions()

    override val gradientOrientation = GradientDrawable.Orientation.BOTTOM_TOP

    override fun measure(view: AlphaSeekBar, widthSpec: Int, heightSpec: Int): ViewDimensions {
        val preferredWidth = maxOf(view.barSize, view.thumbRadius * 2) + view.paddingStart + view.paddingEnd
        val preferredHeight = View.MeasureSpec.getSize(heightSpec) + view.paddingTop + view.paddingBottom

        return dimens.apply {
            width = View.resolveSize(preferredWidth, widthSpec)
            height = View.resolveSize(preferredHeight, heightSpec)
        }
    }

    override fun calculateGradientBounds(view: AlphaSeekBar): Rect {
        val left = view.paddingLeft + (view.width - view.paddingLeft - view.paddingRight - view.barSize) / 2
        val right = left + view.barSize
        val top = view.paddingTop + view.thumbRadius
        val bottom = view.height - view.paddingBottom - view.thumbRadius
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun calculateThumbBounds(view: AlphaSeekBar, barBounds: Rect): Rect {
        val thumbY = convertAlphaToThumbPosition(barBounds, view.alphaValue)
        val cx = barBounds.centerX()
        val left = cx - view.thumbRadius
        val right = cx + view.thumbRadius
        val top = thumbY - view.thumbRadius
        val bottom = thumbY + view.thumbRadius
        return rect.apply { set(left, top, right, bottom) }
    }

    private fun convertAlphaToThumbPosition(barBounds: Rect, alpha: Int): Int {
        val alphaNormalized = 1 - (alpha.toFloat() / MAX_ALPHA)
        return (barBounds.top + alphaNormalized * barBounds.height()).roundToInt()
    }

    override fun calculateAlphaOnMotionEvent(view: AlphaSeekBar, event: MotionEvent, barBounds: Rect): Int {
        val relativeThumbY = (ensureMotionEventYInBounds(event, barBounds) - barBounds.top).toFloat()
        return MAX_ALPHA - ((relativeThumbY / barBounds.height()) * MAX_ALPHA).roundToInt()
    }

    private fun ensureMotionEventYInBounds(event: MotionEvent, bounds: Rect) = when {
        event.y > bounds.bottom -> bounds.bottom
        event.y < bounds.top -> bounds.top
        else -> event.y.roundToInt()
    }
}
