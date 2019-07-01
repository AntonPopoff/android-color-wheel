package com.colorwheelapp.colorwheel.alphaseekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.colorwheelapp.colorwheel.R
import com.colorwheelapp.colorwheel.ThumbDrawable
import com.colorwheelapp.colorwheel.utils.*
import kotlin.math.abs

class AlphaSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val viewConfig = ViewConfiguration.get(context)

    private val gradientColors = IntArray(2)
    private val thumbDrawable = ThumbDrawable()
    private val gradientDrawable = GradientDrawable()

    private var internalAlpha = MAX_ALPHA
    private var motionEventDownX = 0f
    private var strategy: AlphaSeekBarOrientationStrategy = Orientation.VERTICAL.strategy

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

    var argb
        get() = setAlpha(gradientColors[1], internalAlpha)
        set(argb) {
            internalAlpha = ensureAlphaWithinRange(getAlpha(argb))
            rgb = argb
            fireListener()
        }

    var rgb
        get() = gradientColors[1]
        set(rgb) {
            gradientColors[0] = clearAlpha(rgb)
            gradientColors[1] = setAlpha(rgb, MAX_ALPHA)
            gradientDrawable.colors = gradientColors
            invalidate()
        }

    var alphaValue
        get() = internalAlpha
        set(alpha) {
            internalAlpha = ensureAlphaWithinRange(alpha)
            fireListener()
            invalidate()
        }

    var thumbRadius = 0
        set(radius) {
            field = radius
            updateThumbInsets()
            requestLayout()
        }

    var alphaChangeListener: ((Int) -> Unit)? = null

    init {
        parseAttributes(context, attrs, R.style.AlphaSeekBarDefaultStyle)
        updateThumbInsets()
    }

    private fun parseAttributes(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.AlphaSeekBar, 0, defStyle).apply {
            thumbRadius = getDimensionPixelSize(R.styleable.AlphaSeekBar_asb_thumbRadius, 0)
            barSize = getDimensionPixelSize(R.styleable.AlphaSeekBar_asb_barSize, 0)
            cornersRadius = getDimension(R.styleable.AlphaSeekBar_asb_barCornersRadius, 0f)
            internalAlpha = getInteger(R.styleable.AlphaSeekBar_asb_alpha, MAX_ALPHA)
            rgb = getColor(R.styleable.AlphaSeekBar_asb_color, Color.BLACK)
            strategy = Orientation.values()[getInt(R.styleable.AlphaSeekBar_asb_orientation, 0)].strategy
            recycle()
        }
    }

    private fun updateThumbInsets() {
        thumbDrawable.applyInsets(thumbRadius.toFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dimens = strategy.measure(this, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(dimens.width, dimens.height)
    }

    override fun onDraw(canvas: Canvas) {
        gradientDrawable.orientation = strategy.gradientOrientation
        updateIndicatorColor()
        drawGradientRect(canvas)
        drawThumb(canvas)
    }

    private fun drawGradientRect(canvas: Canvas) {
        gradientDrawable.bounds = strategy.calculateGradientBounds(this)
        gradientDrawable.cornerRadius = cornersRadius
        gradientDrawable.draw(canvas)
    }

    private fun drawThumb(canvas: Canvas) {
        thumbDrawable.bounds = strategy.calculateThumbBounds(this, gradientDrawable.bounds)
        thumbDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                motionEventDownX = event.x
                calculateAlphaOnMotionEvent(event)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                calculateAlphaOnMotionEvent(event)
            }
            MotionEvent.ACTION_UP -> {
                calculateAlphaOnMotionEvent(event)
                if (isTap(event)) performClick()
            }
        }

        return super.onTouchEvent(event)
    }

    private fun calculateAlphaOnMotionEvent(event: MotionEvent) {
        internalAlpha = strategy.calculateAlphaOnMotionEvent(this, event, gradientDrawable.bounds)
        fireListener()
        invalidate()
    }

    private fun updateIndicatorColor() {
        thumbDrawable.indicatorColor = setAlpha(gradientColors[1], internalAlpha)
    }

    private fun isTap(event: MotionEvent): Boolean {
        val eventDuration = event.eventTime - event.downTime
        val eventTravelDistance = abs(event.x - motionEventDownX)
        return eventDuration < ViewConfiguration.getTapTimeout() && eventTravelDistance < viewConfig.scaledTouchSlop
    }

    private fun fireListener() {
        alphaChangeListener?.invoke(internalAlpha)
    }

    override fun performClick() = super.performClick()

    private enum class Orientation(val strategy: AlphaSeekBarOrientationStrategy) {
        VERTICAL(VerticalAlphaSeekBar()),
        HORIZONTAL(HorizontalAlphaSeekBar())
    }
}

fun AlphaSeekBar.setAlphaSilently(alpha: Int) {
    val listener = this.alphaChangeListener
    this.alphaChangeListener = null
    this.alphaValue = alpha
    this.alphaChangeListener = listener
}
