package com.apandroid.colorwheel.gradientseekbar

import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import android.view.View
import com.apandroid.colorwheel.utils.ensureNumberWithinRange
import kotlin.math.roundToInt

internal class HorizontalStrategy : OrientationStrategy {

    private val rect = Rect()

    override val gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT

    override fun measure(view: GradientSeekBar, widthSpec: Int, heightSpec: Int): Rect {
        val preferredWidth = View.MeasureSpec.getSize(widthSpec) + view.paddingStart + view.paddingEnd
        val preferredHeight = maxOf(view.barSize, view.thumbRadius * 2) + view.paddingTop + view.paddingBottom
        val finalWidth = View.resolveSize(preferredWidth, widthSpec)
        val finalHeight = View.resolveSize(preferredHeight, heightSpec)
        return rect.apply { set(0, 0, finalWidth, finalHeight) }
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
        val cy = view.height / 2
        val left = thumbX - view.thumbRadius
        val right = thumbX + view.thumbRadius
        val top = cy - view.thumbRadius
        val bottom = cy + view.thumbRadius
        return rect.apply { set(left, top, right, bottom) }
    }

    override fun calculateOffsetOnMotionEvent(view: GradientSeekBar, event: MotionEvent, barBounds: Rect): Float {
        val checkedX = ensureNumberWithinRange(event.x.roundToInt(), barBounds.left, barBounds.right)
        val relativeX = (checkedX - barBounds.left).toFloat()
        return relativeX / barBounds.width()
    }
}
