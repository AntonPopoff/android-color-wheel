package com.colorwheelapp.colorwheel.alphaseekbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.colorwheelapp.colorwheel.R
import com.colorwheelapp.colorwheel.ThumbDrawable
import com.colorwheelapp.colorwheel.utils.interpolateColorLinear
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
    private var internalOffset = 0f
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

    var offset
        get() = internalOffset
        set(offset) {
            internalOffset = ensureOffsetWithinRange(offset)
            fireListener()
            invalidate()
        }

    var thumbRadius = 0
        set(radius) {
            field = radius
            updateThumbInsets()
            requestLayout()
        }

    var offsetChangeListener: ((Float) -> Unit)? = null

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
            internalOrientation = Orientation.values()[getInt(R.styleable.AlphaSeekBar_asb_orientation, 0)]
            readGradientColors(this)
            recycle()
        }
    }

    private fun readGradientColors(array: TypedArray) {
        val start = array.getColor(R.styleable.AlphaSeekBar_asb_startColor, Color.TRANSPARENT)
        val end = array.getColor(R.styleable.AlphaSeekBar_asb_startColor, Color.BLACK)
        setColors(start, end)
    }

    private fun createOrientationStrategy() = when (orientation) {
        Orientation.VERTICAL -> VerticalAlphaSeekBar()
        Orientation.HORIZONTAL -> HorizontalAlphaSeekBar()
    }

    private fun updateThumbInsets() {
        thumbDrawable.applyInsets(thumbRadius.toFloat())
    }

    fun setColors(startColor: Int = gradientColors[0], endColor: Int = gradientColors[1]) {
        gradientColors[0] = startColor
        gradientColors[1] = endColor
        gradientDrawable.colors = gradientColors
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dimens = orientationStrategy.measure(this, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(dimens.width, dimens.height)
    }

    override fun onDraw(canvas: Canvas) {
        gradientDrawable.orientation = orientationStrategy.gradientOrientation
        thumbDrawable.indicatorColor = interpolateColorLinear(gradientColors[0], gradientColors[1], internalOffset)
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

    private fun isTap(event: MotionEvent): Boolean {
        val eventDuration = event.eventTime - event.downTime
        val eventTravelDistance = abs(event.x - motionEventDownX)
        return eventDuration < ViewConfiguration.getTapTimeout() && eventTravelDistance < viewConfig.scaledTouchSlop
    }

    private fun calculateAlphaOnMotionEvent(event: MotionEvent) {
        internalOffset = orientationStrategy.calculateOffsetOnMotionEvent(this, event, gradientDrawable.bounds)
        fireListener()
        invalidate()
    }

    private fun fireListener() {
        offsetChangeListener?.invoke(internalOffset)
    }

    override fun performClick() = super.performClick()

    enum class Orientation { VERTICAL, HORIZONTAL }
}

private fun ensureOffsetWithinRange(offset: Float) = abs(offset % 1f)

fun AlphaSeekBar.setAlphaSilently(alpha: Int) {
//    val listener = this.alphaChangeListener

//    this.alphaChangeListener = null

//    this.alphaValue = alpha

//    this.alphaChangeListener = listener
}
