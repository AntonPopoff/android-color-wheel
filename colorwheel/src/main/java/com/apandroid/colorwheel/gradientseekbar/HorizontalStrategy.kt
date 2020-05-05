package com.apandroid.colorwheel.gradientseekbar

import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import com.apandroid.colorwheel.utils.ensureNumberWithinRange
import kotlin.math.roundToInt

internal class HorizontalStrategy : OrientationStrategy {

    private val rect = Rect()
    private val point = PointF()

    override val gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT

    override fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect {
        val preferredWidth = View.MeasureSpec.getSize(widthSpec) + view.paddingLeft + view.paddingRight
        val preferredHeight = maxOf(view.barSize, view.thumbRadius * 2) + view.paddingTop + view.paddingBottom
        val finalWidth = View.resolveSize(preferredWidth, widthSpec)
        val finalHeight = View.resolveSize(preferredHeight, heightSpec)
        return rect.apply { set(0, 0, finalWidth, finalHeight) }
    }

    override fun calculateGradientBounds(view: GradientSeekBar): Rect {
        val left = view.paddingLeft + view.thumbRadius
        val right = view.width - view.paddingRight - view.thumbRadius
        val top = view.paddingTop + (view.height - view.paddingTop - view.paddingRight - view.barSize) / 2
        val bottom = top + view.barSize
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun calculateThumbCoordinates(view: GradientSeekBar, barBounds: Rect): PointF {
        val x = (barBounds.left + view.offset * barBounds.width())
        val y = view.height / 2f
        return point.apply { set(x, y) }
    }

    override fun calculateOffsetOnMotionEvent(view: GradientSeekBar, event: MotionEvent, barBounds: Rect): Float {
        val checkedX = ensureNumberWithinRange(event.x.roundToInt(), barBounds.left, barBounds.right)
        val relativeX = (checkedX - barBounds.left).toFloat()
        return relativeX / barBounds.width()
    }
}
