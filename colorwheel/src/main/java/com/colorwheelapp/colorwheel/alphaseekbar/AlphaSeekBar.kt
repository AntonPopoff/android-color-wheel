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

    private var orientationStrategy: AlphaSeekBarOrientationStrategy
    private var internalOrientation = Orientation.VERTICAL
    private var internalAlpha = MAX_ALPHA
    private var motionEventDownX = 0f

    var orientation
        get() = internalOrientation
        set(orientation) {
            internalOrientation = orientation
            orientationStrategy = createOrientationStrategy()
            requestLayout()
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

    var argb
        get() = setAlpha(gradientColors[1], internalAlpha)
        set(argb) {
            rgb = argb
            internalAlpha = getAlpha(argb)
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

    var interceptTouchEvent = true

    init {
        parseAttributes(context, attrs, R.style.AlphaSeekBarDefaultStyle)
        orientationStrategy = createOrientationStrategy()
        updateThumbInsets()
    }

    private fun parseAttributes(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.AlphaSeekBar, 0, defStyle).apply {
            thumbRadius = getDimensionPixelSize(R.styleable.AlphaSeekBar_asb_thumbRadius, 0)
            barSize = getDimensionPixelSize(R.styleable.AlphaSeekBar_asb_barSize, 0)
            cornersRadius = getDimension(R.styleable.AlphaSeekBar_asb_barCornersRadius, 0f)
            internalAlpha = getInteger(R.styleable.AlphaSeekBar_asb_alpha, MAX_ALPHA)
            rgb = getColor(R.styleable.AlphaSeekBar_asb_color, Color.BLACK)
            internalOrientation = Orientation.values()[getInt(R.styleable.AlphaSeekBar_asb_orientation, 0)]
            recycle()
        }
    }

    private fun createOrientationStrategy() = when (orientation) {
        Orientation.VERTICAL -> VerticalAlphaSeekBar()
        Orientation.HORIZONTAL -> HorizontalAlphaSeekBar()
    }

    private fun updateThumbInsets() {
        thumbDrawable.applyInsets(thumbRadius.toFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dimens = orientationStrategy.measure(this, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(dimens.width, dimens.height)
    }

    override fun onDraw(canvas: Canvas) {
        gradientDrawable.orientation = orientationStrategy.gradientOrientation
        updateIndicatorColor()
        drawGradientRect(canvas)
        drawThumb(canvas)
    }

    private fun drawGradientRect(canvas: Canvas) {
        gradientDrawable.bounds = orientationStrategy.calculateGradientBounds(this)
        gradientDrawable.cornerRadius = cornersRadius
        gradientDrawable.draw(canvas)
    }

    private fun drawThumb(canvas: Canvas) {
        thumbDrawable.bounds = orientationStrategy.calculateThumbBounds(this, gradientDrawable.bounds)
        thumbDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                motionEventDownX = event.x
                parent.requestDisallowInterceptTouchEvent(interceptTouchEvent)
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
        internalAlpha = orientationStrategy.calculateAlphaOnMotionEvent(this, event, gradientDrawable.bounds)
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

    enum class Orientation { VERTICAL, HORIZONTAL }
}

fun AlphaSeekBar.setAlphaSilently(alpha: Int) {
    val listener = this.alphaChangeListener
    this.alphaChangeListener = null
    this.alphaValue = alpha
    this.alphaChangeListener = listener
}
