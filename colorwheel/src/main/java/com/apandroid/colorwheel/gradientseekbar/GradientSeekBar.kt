package com.apandroid.colorwheel.gradientseekbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.apandroid.colorwheel.R
import com.apandroid.colorwheel.ThumbDrawable
import com.apandroid.colorwheel.utils.MAX_ALPHA
import com.apandroid.colorwheel.utils.ensureNumberWithinRange
import com.apandroid.colorwheel.utils.interpolateColorLinear
import com.apandroid.colorwheel.utils.setColorAlpha
import kotlin.math.abs

open class GradientSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val viewConfig = ViewConfiguration.get(context)
    private val gradientColors = IntArray(2)
    private val thumbDrawable = ThumbDrawable()
    private val gradientDrawable = GradientDrawable()

    private var orientationStrategy: OrientationStrategy
    private var internalOrientation = Orientation.VERTICAL
    private var internalOffset = 0f
    private var motionEventDownX = 0f

    var thumbColor
        get() = thumbDrawable.thumbColor
        set(value) {
            thumbDrawable.thumbColor = value
            invalidate()
        }

    var thumbStrokeColor
        get() = thumbDrawable.strokeColor
        set(value) {
            thumbDrawable.strokeColor = value
            invalidate()
        }

    var thumbColorCircleScale
        get() = thumbDrawable.colorCircleScale
        set(value) {
            thumbDrawable.colorCircleScale = value
            invalidate()
        }

    var startColor
        get() = gradientColors[0]
        set(color) { setColors(startColor = color) }

    var endColor
        get() = gradientColors[1]
        set(color) { setColors(endColor = color) }

    var orientation
        get() = internalOrientation
        set(orientation) {
            internalOrientation = orientation
            orientationStrategy = createOrientationStrategy()
            requestLayout()
        }

    var offset
        get() = internalOffset
        set(offset) {
            internalOffset = ensureOffsetWithinRange(offset)
            updateCurrentColor()
        }

    var barSize = 0
        set(width) {
            field = width
            requestLayout()
        }

    var cornersRadius = 0f
        set(radius) {
            field = radius
            invalidate()
        }

    var thumbRadius = 0
        set(radius) {
            field = radius
            requestLayout()
        }

    var argb = 0
        private set

    var listener: ((Float, Int) -> Unit)? = null

    var interceptTouchEvent = true

    init {
        parseAttributes(context, attrs, R.style.GradientSeekBarDefaultStyle)
        orientationStrategy = createOrientationStrategy()
    }

    private fun parseAttributes(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.GradientSeekBar, 0, defStyle).apply {
            thumbColor = getColor(R.styleable.GradientSeekBar_asb_thumbColor, 0)
            thumbStrokeColor = getColor(R.styleable.GradientSeekBar_asb_thumbStrokeColor, 0)
            thumbColorCircleScale = getFloat(R.styleable.GradientSeekBar_asb_thumbColorCircleScale, 0f)
            thumbRadius = getDimensionPixelSize(R.styleable.GradientSeekBar_asb_thumbRadius, 0)
            barSize = getDimensionPixelSize(R.styleable.GradientSeekBar_asb_barSize, 0)
            cornersRadius = getDimension(R.styleable.GradientSeekBar_asb_barCornersRadius, 0f)
            internalOffset = ensureOffsetWithinRange(getFloat(R.styleable.GradientSeekBar_asb_offset, 0f))
            internalOrientation = Orientation.values()[getInt(R.styleable.GradientSeekBar_asb_orientation, 0)]
            readGradientColors(this)
            recycle()
        }
    }

    private fun readGradientColors(array: TypedArray) {
        val start = array.getColor(R.styleable.GradientSeekBar_asb_startColor, Color.TRANSPARENT)
        val end = array.getColor(R.styleable.GradientSeekBar_asb_endColor, Color.BLACK)
        setColors(start, end)
    }

    private fun createOrientationStrategy() = when (orientation) {
        Orientation.VERTICAL -> VerticalStrategy()
        Orientation.HORIZONTAL -> HorizontalStrategy()
    }

    fun setColors(startColor: Int = gradientColors[0], endColor: Int = gradientColors[1]) {
        gradientColors[0] = startColor
        gradientColors[1] = endColor
        gradientDrawable.colors = gradientColors
        updateCurrentColor()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dimens = orientationStrategy.measure(this, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(dimens.width, dimens.height)
    }

    override fun onDraw(canvas: Canvas) {
        drawGradientRect(canvas)
        drawThumb(canvas)
    }

    private fun drawGradientRect(canvas: Canvas) {
        gradientDrawable.orientation = orientationStrategy.gradientOrientation
        gradientDrawable.bounds = orientationStrategy.calculateGradientBounds(this)
        gradientDrawable.cornerRadius = cornersRadius
        gradientDrawable.draw(canvas)
    }

    private fun drawThumb(canvas: Canvas) {
        thumbDrawable.indicatorColor = argb
        thumbDrawable.setBounds(orientationStrategy.calculateThumbBounds(this, gradientDrawable.bounds), thumbRadius)
        thumbDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                motionEventDownX = event.x
                parent.requestDisallowInterceptTouchEvent(interceptTouchEvent)
                calculateOffsetOnMotionEvent(event)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                calculateOffsetOnMotionEvent(event)
            }
            MotionEvent.ACTION_UP -> {
                calculateOffsetOnMotionEvent(event)
                if (isTap(event)) performClick()
            }
        }

        return super.onTouchEvent(event)
    }

    override fun performClick() = super.performClick()

    private fun isTap(event: MotionEvent): Boolean {
        val eventDuration = event.eventTime - event.downTime
        val eventTravelDistance = abs(event.x - motionEventDownX)
        return eventDuration < ViewConfiguration.getTapTimeout() && eventTravelDistance < viewConfig.scaledTouchSlop
    }

    private fun calculateOffsetOnMotionEvent(event: MotionEvent) {
        internalOffset = orientationStrategy.calculateOffsetOnMotionEvent(this, event, gradientDrawable.bounds)
        updateCurrentColor()
    }

    private fun updateCurrentColor() {
        argb = interpolateColorLinear(gradientColors[0], gradientColors[1], internalOffset)
        fireListener()
        invalidate()
    }

    private fun fireListener() {
        listener?.invoke(internalOffset, argb)
    }

    enum class Orientation { VERTICAL, HORIZONTAL }
}

val GradientSeekBar.argbAlpha get() = Color.alpha(argb)

fun GradientSeekBar.setAlphaArgb(argb: Int) {
    this.offset = Color.alpha(argb) / MAX_ALPHA.toFloat()
    this.setColors(setColorAlpha(argb, 0), setColorAlpha(argb, MAX_ALPHA))
}

fun GradientSeekBar.setAlphaRgb(rgb: Int) {
    this.setColors(setColorAlpha(rgb, 0), setColorAlpha(rgb, MAX_ALPHA))
}

inline fun GradientSeekBar.setAlphaListener(crossinline listener: (Float, Int, Int) -> Unit) {
    this.listener = { offset, color -> listener(offset, color, this.argbAlpha) }
}

fun GradientSeekBar.setBlackToColor(color: Int) {
    this.setColors(Color.BLACK, color)
}

private fun ensureOffsetWithinRange(offset: Float) = ensureNumberWithinRange(offset, 0f, 1f)
