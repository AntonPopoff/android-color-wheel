package com.colorwheelapp.colorwheel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.colorwheelapp.colorwheel.utils.clearAlpha
import com.colorwheelapp.colorwheel.utils.getAlpha
import com.colorwheelapp.colorwheel.utils.setAlpha
import kotlin.math.abs
import kotlin.math.roundToInt

private const val MAX_ALPHA = 255

class AlphaSeekBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    private val viewConfig = ViewConfiguration.get(context)

    private val gradientColors = IntArray(2)
    private val thumbDrawable = ThumbDrawable()
    private val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, gradientColors)

    private var internalAlpha = MAX_ALPHA
    private var motionEventDownX = 0f
    private var barWidth = 0

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

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private fun parseAttributes(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.AlphaSeekBar, 0, defStyle).apply {
            thumbRadius = getDimensionPixelSize(R.styleable.AlphaSeekBar_asb_thumbRadius, 0)
            barWidth = getDimensionPixelSize(R.styleable.AlphaSeekBar_asb_barWidth, 0)
            internalAlpha = getInteger(R.styleable.AlphaSeekBar_asb_alpha, MAX_ALPHA)
            rgb = getColor(R.styleable.AlphaSeekBar_asb_color, Color.BLACK)
            recycle()
        }
    }

    private fun updateThumbInsets() {
        thumbDrawable.applyInsets(thumbRadius.toFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val preferredWidth = maxOf(barWidth, thumbRadius * 2)
        val preferredHeight = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(
                resolveSize(preferredWidth, widthMeasureSpec),
                resolveSize(preferredHeight, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        updateIndicatorColor()
        drawGradientRect(canvas)
        drawThumb(canvas)
    }

    private fun drawGradientRect(canvas: Canvas) {
        val left = paddingLeft + (width - paddingLeft - paddingRight - barWidth) / 2
        val right = left + barWidth
        val top = paddingTop + thumbRadius
        val bottom = height - paddingBottom - thumbRadius

        gradientDrawable.cornerRadius = gradientDrawable.bounds.width() / 2f
        gradientDrawable.setBounds(left, top, right, bottom)
        gradientDrawable.draw(canvas)
    }

    private fun drawThumb(canvas: Canvas) {
        val thumbY = convertAlphaToThumbPosition()
        val thumbDiameter = thumbRadius * 2
        val left = gradientDrawable.bounds.centerX() - thumbRadius
        val right = left + thumbDiameter
        val top = thumbY - thumbRadius
        val bottom = top + thumbDiameter

        thumbDrawable.setBounds(left, top, right, bottom)
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
        internalAlpha = calculateAlphaByMotionEventY(ensureMotionEventYInBounds(event))
        fireListener()
        invalidate()
    }

    private fun ensureMotionEventYInBounds(event: MotionEvent) = when {
        event.y > gradientDrawable.bounds.bottom -> gradientDrawable.bounds.bottom
        event.y < gradientDrawable.bounds.top -> gradientDrawable.bounds.top
        else -> event.y.roundToInt()
    }

    private fun calculateAlphaByMotionEventY(y: Int): Int {
        val relativeThumbY = (y - gradientDrawable.bounds.top).toFloat()
        return MAX_ALPHA - ((relativeThumbY / gradientDrawable.bounds.height()) * MAX_ALPHA).roundToInt()
    }

    private fun updateIndicatorColor() {
        thumbDrawable.indicatorColor = setAlpha(gradientColors[1], internalAlpha)
    }

    private fun isTap(event: MotionEvent): Boolean {
        val eventDuration = event.eventTime - event.downTime
        val eventTravelDistance = abs(event.x - motionEventDownX)
        return eventDuration < ViewConfiguration.getTapTimeout() && eventTravelDistance < viewConfig.scaledTouchSlop
    }

    private fun convertAlphaToThumbPosition(): Int {
        val alphaNormalized = 1 - (internalAlpha.toFloat() / MAX_ALPHA)
        return (gradientDrawable.bounds.top + alphaNormalized * gradientDrawable.bounds.height()).roundToInt()
    }

    private fun fireListener() {
        alphaChangeListener?.invoke(internalAlpha)
    }

    override fun performClick() = super.performClick()
}

private fun ensureAlphaWithinRange(alpha: Int) = when {
    alpha < 0 -> 0
    alpha > MAX_ALPHA -> MAX_ALPHA
    else -> alpha
}
