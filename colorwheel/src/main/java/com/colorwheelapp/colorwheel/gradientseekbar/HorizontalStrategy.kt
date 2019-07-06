package com.colorwheelapp.colorwheel.gradientseekbar

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import com.colorwheelapp.colorwheel.utils.ViewDimensions
import kotlin.math.roundToInt

class HorizontalStrategy : OrientationStrategy {

    private val rect = Rect()

    private val dimens = ViewDimensions()

    override val gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT

    override fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): ViewDimensions {
        val preferredWidth = View.MeasureSpec.getSize(widthSpec) + view.paddingStart + view.paddingEnd
        val preferredHeight = maxOf(view.barSize, view.thumbRadius * 2) + view.paddingTop + view.paddingBottom

        return dimens.apply {
            width = View.resolveSize(preferredWidth, widthSpec)
            height = View.resolveSize(preferredHeight, heightSpec)
        }
    }

    override fun calculateGradientBounds(view: GradientSeekBar): Rect {
        val left = view.paddingLeft + view.thumbRadius
        val right = view.width - view.paddingRight - view.thumbRadius
        val top = view.paddingTop + (view.height - view.paddingTop - view.paddingEnd - view.barSize) / 2
        val bottom = top + view.barSize
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun calculateThumbBounds(view: GradientSeekBar, barBounds: Rect): Rect {
        val thumbX = (barBounds.left + view.offset * barBounds.width()).roundToInt()
        val cy = barBounds.centerY()
        val left = thumbX - view.thumbRadius
        val right = thumbX + view.thumbRadius
        val top = cy - view.thumbRadius
        val bottom = cy + view.thumbRadius
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun calculateOffsetOnMotionEvent(view: GradientSeekBar, event: MotionEvent, barBounds: Rect): Float {
        val relativeThumbX = (ensureMotionEventInBounds(event, barBounds) - barBounds.left).toFloat()
        return relativeThumbX / barBounds.width()
    }

    private fun ensureMotionEventInBounds(event: MotionEvent, bounds: Rect) = when {
        event.x > bounds.right -> bounds.right
        event.x < bounds.left -> bounds.left
        else -> event.x.roundToInt()
    }
}
