package com.github.antonpopoff.colorwheel.gradientseekbar

import android.graphics.PointF
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import com.github.antonpopoff.colorwheel.utils.ensureWithinRange
import kotlin.math.roundToInt

internal class HorizontalStrategy : OrientationStrategy {

    private val rect = Rect()
    private val point = PointF()

    override val gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT

    override fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect {
        val widthSize = View.MeasureSpec.getSize(widthSpec)
        val maxHeight = maxOf(view.barSize, view.thumbRadius * 2)
        val preferredWidth = widthSize + view.paddingLeft + view.paddingRight
        val preferredHeight = maxHeight + view.paddingTop + view.paddingBottom
        val finalWidth = View.resolveSize(preferredWidth, widthSpec)
        val finalHeight = View.resolveSize(preferredHeight, heightSpec)
        return rect.apply { set(0, 0, finalWidth, finalHeight) }
    }

    override fun getGradientBounds(view: GradientSeekBar): Rect {
        val availableHeight = view.height - view.paddingTop - view.paddingRight
        val left = view.paddingLeft + view.thumbRadius
        val right = view.width - view.paddingRight - view.thumbRadius
        val top = view.paddingTop + (availableHeight - view.barSize) / 2
        val bottom = top + view.barSize
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun getThumbPosition(view: GradientSeekBar, gradient: Rect): PointF {
        val x = (gradient.left + view.offset * gradient.width())
        val y = view.height / 2f
        return point.apply { set(x, y) }
    }

    override fun getOffset(view: GradientSeekBar, event: MotionEvent, gradient: Rect): Float {
        val checkedX = ensureWithinRange(event.x.roundToInt(), gradient.left, gradient.right)
        val relativeX = (checkedX - gradient.left).toFloat()
        return relativeX / gradient.width()
    }
}
