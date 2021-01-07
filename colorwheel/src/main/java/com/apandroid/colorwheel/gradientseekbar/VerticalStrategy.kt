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
        val heightSize = View.MeasureSpec.getSize(heightSpec)
        val maxWidth = maxOf(view.barSize, view.thumbRadius * 2)
        val preferredWidth = maxWidth + view.paddingLeft + view.paddingRight
        val preferredHeight = heightSize + view.paddingTop + view.paddingBottom
        val finalWidth = View.resolveSize(preferredWidth, widthSpec)
        val finalHeight = View.resolveSize(preferredHeight, heightSpec)
        return rect.apply { set(0, 0, finalWidth, finalHeight) }
    }

    override fun calculateBarBounds(view: GradientSeekBar): Rect {
        val availableWidth = view.width - view.paddingLeft - view.paddingRight
        val left = view.paddingLeft + (availableWidth - view.barSize) / 2
        val right = left + view.barSize
        val top = view.paddingTop + view.thumbRadius
        val bottom = view.height - view.paddingBottom - view.thumbRadius
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun calculateThumbPos(view: GradientSeekBar, bounds: Rect): PointF {
        val y = (bounds.top + (1f - view.offset) * bounds.height())
        val x = view.width / 2f
        return point.apply { set(x, y) }
    }

    override fun calculateOffset(view: GradientSeekBar, event: MotionEvent, bounds: Rect): Float {
        val checkedY = ensureWithinRange(event.y.roundToInt(), bounds.top, bounds.bottom)
        val relativeY = (checkedY - bounds.top).toFloat()
        return 1f - relativeY / bounds.height()
    }
}
