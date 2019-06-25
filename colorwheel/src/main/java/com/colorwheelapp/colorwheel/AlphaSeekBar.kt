package com.colorwheelapp.colorwheel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.colorwheelapp.colorwheel.utils.clearAlpha
import com.colorwheelapp.colorwheel.utils.setAlpha
import kotlin.math.abs
import kotlin.math.roundToInt

private const val MAX_ALPHA = 255

class AlphaSeekBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    private val viewConfig = ViewConfiguration.get(context)

    private val gradientRect = Rect()
    private val gradientColors = IntArray(2)
    private val gradient = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, gradientColors)

    private var thumbRadius = 0
    private val thumbDrawable = ThumbDrawable()

    private var motionEventDownX = 0f
    private var barWidth = 0

    val originColor get() = gradientColors[1]

    val argb get() = setAlpha(originColor, colorAlpha)

    var colorAlpha = MAX_ALPHA
        private set

    var alphaChangeListener: ((Int) -> Unit)? = null

    init {
        parseAttributes(context, attrs, R.style.AlphaSeekBarDefaultStyle)
        thumbDrawable.applyInsets(thumbRadius.toFloat())
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private fun parseAttributes(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.AlphaSeekBar, 0, defStyle).apply {
            thumbRadius = getDimensionPixelSize(R.styleable.AlphaSeekBar_asb_thumbRadius, 0)
            barWidth = getDimensionPixelSize(R.styleable.AlphaSeekBar_asb_barWidth, 0)
            setAlpha(getInteger(R.styleable.AlphaSeekBar_asb_alpha, MAX_ALPHA))
            setOriginColor(getColor(R.styleable.AlphaSeekBar_asb_color, Color.BLACK))
            recycle()
        }
    }

    fun setAlpha(alpha: Int) {
        colorAlpha = ensureAlphaWithinRange(alpha)
        fireListener()
        invalidate()
    }

    private fun ensureAlphaWithinRange(alpha: Int) = when {
        alpha < 0 -> 0
        alpha > MAX_ALPHA -> MAX_ALPHA
        else -> alpha
    }

    fun setOriginColor(argb: Int) {
        gradientColors[0] = clearAlpha(argb)
        gradientColors[1] = setAlpha(argb, MAX_ALPHA)
        gradient.colors = gradientColors
        invalidate()
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
        calculateGradientRect()
        calculateThumbRect()
        updateIndicatorColor()
        drawGradientRect(canvas)
        thumbDrawable.draw(canvas)
    }

    private fun calculateGradientRect() {
        gradientRect.apply {
            left = paddingLeft + (width - paddingLeft - paddingRight - barWidth) / 2
            right = left + barWidth
            top = paddingTop + thumbRadius
            bottom = height - paddingBottom - thumbRadius
        }
    }

    private fun drawGradientRect(canvas: Canvas) {
        gradient.apply {
            bounds = gradientRect
            cornerRadius = gradientRect.width() / 2f
            draw(canvas)
        }
    }

    private fun calculateThumbRect() {
        val thumbY = convertAlphaToThumbPosition(colorAlpha)
        val thumbDiameter = thumbRadius * 2

        val left = gradientRect.centerX() - thumbRadius
        val right = left + thumbDiameter
        val top = thumbY - thumbRadius
        val bottom = top + thumbDiameter

        thumbDrawable.setBounds(left, top, right, bottom)
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
        colorAlpha = calculateAlphaByMotionEventY(ensureMotionEventYInBounds(event))
        fireListener()
        invalidate()
    }

    private fun ensureMotionEventYInBounds(event: MotionEvent) = when {
        event.y > gradientRect.bottom -> gradientRect.bottom
        event.y < gradientRect.top -> gradientRect.top
        else -> event.y.roundToInt()
    }

    private fun calculateAlphaByMotionEventY(y: Int): Int {
        val relativeThumbY = (y - gradientRect.top).toFloat()
        return MAX_ALPHA - ((relativeThumbY / gradientRect.height()) * MAX_ALPHA).roundToInt()
    }

    private fun updateIndicatorColor() {
        thumbDrawable.indicatorColor = setAlpha(originColor, colorAlpha)
    }

    private fun isTap(event: MotionEvent): Boolean {
        val eventDuration = event.eventTime - event.downTime
        val eventTravelDistance = abs(event.x - motionEventDownX)
        return eventDuration < ViewConfiguration.getTapTimeout() && eventTravelDistance < viewConfig.scaledTouchSlop
    }

    private fun convertAlphaToThumbPosition(alpha: Int): Int {
        val alphaNormalized = 1 - (alpha.toFloat() / MAX_ALPHA)
        return (gradientRect.top + alphaNormalized * gradientRect.height()).roundToInt()
    }

    private fun fireListener() {
        alphaChangeListener?.invoke(colorAlpha)
    }

    override fun performClick() = super.performClick()
}
