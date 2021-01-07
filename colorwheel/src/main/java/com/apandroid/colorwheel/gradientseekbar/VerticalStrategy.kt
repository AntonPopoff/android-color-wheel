package com.apandroid.colorwheel.gradientseekbar

import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import com.apandroid.colorwheel.utils.ensureWithinRange
import kotlin.math.roundToInt

internal class VerticalStrategy : OrientationStrategy {

    private val rect = Rect()
    private val point = PointF()

    override val gradientOrientation = GradientDrawable.Orientation.BOTTOM_TOP

    override fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect {
        val preferredWidth = maxOf(view.barSize, view.thumbRadius * 2) + view.paddingLeft + view.paddingRight
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

    override fun calculateThumbCoordinates(view: GradientSeekBar, barBounds: Rect): PointF {
        val y = (barBounds.top + (1f - view.offset) * barBounds.height())
        val x = view.width / 2f
        return point.apply { set(x, y) }
    }

    override fun calculateOffsetOnMotionEvent(view: GradientSeekBar, event: MotionEvent, barBounds: Rect): Float {
        val checkedY = ensureWithinRange(event.y.roundToInt(), barBounds.top, barBounds.bottom)
        val relativeY = (checkedY - barBounds.top).toFloat()
        return 1f - relativeY / barBounds.height()
    }
}
