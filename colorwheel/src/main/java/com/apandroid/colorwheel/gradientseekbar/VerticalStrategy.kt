package com.apandroid.colorwheel.gradientseekbar

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import com.apandroid.colorwheel.utils.ensureNumberWithinRange
import kotlin.math.roundToInt

internal class VerticalStrategy : OrientationStrategy {

    private val rect = Rect()

    override val gradientOrientation = GradientDrawable.Orientation.BOTTOM_TOP

    override fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect {
        val preferredWidth = maxOf(view.barSize, view.thumbRadius * 2) + view.paddingStart + view.paddingEnd
        val preferredHeight = View.MeasureSpec.getSize(heightSpec) + view.paddingTop + view.paddingBottom
        val finalWidth = View.resolveSize(preferredWidth, widthSpec)
        val finalHeight = View.resolveSize(preferredHeight, heightSpec)
        return rect.apply { set(0, 0, finalWidth, finalHeight) }
    }

    override fun calculateGradientBounds(view: GradientSeekBar): Rect {
        val left = view.paddingLeft + (view.width - view.paddingLeft - view.paddingRight - view.barSize) / 2
        val right = left + view.barSize
        val top = view.paddingTop + view.thumbRadius
        val bottom = view.height - view.paddingBottom - view.thumbRadius
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun calculateThumbBounds(view: GradientSeekBar, barBounds: Rect): Rect {
        val thumbY = (barBounds.top + (1f - view.offset) * barBounds.height()).roundToInt()
        val cx = view.width / 2
        val left = cx - view.thumbRadius
        val right = cx + view.thumbRadius
        val top = thumbY - view.thumbRadius
        val bottom = thumbY + view.thumbRadius
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun calculateOffsetOnMotionEvent(view: GradientSeekBar, event: MotionEvent, barBounds: Rect): Float {
        val checkedY = ensureNumberWithinRange(event.y.roundToInt(), barBounds.top, barBounds.bottom)
        val relativeY = (checkedY - barBounds.top).toFloat()
        return 1f - relativeY / barBounds.height()
    }
}
