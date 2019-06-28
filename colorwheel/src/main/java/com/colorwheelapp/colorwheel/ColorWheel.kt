package com.colorwheelapp.colorwheel

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.colorwheelapp.colorwheel.utils.HsvColor
import com.colorwheelapp.colorwheel.utils.toDegrees
import com.colorwheelapp.colorwheel.utils.toRadians
import kotlin.math.*

private val HUE_COLORS = intArrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.RED)
private val SATURATION_COLORS = intArrayOf(Color.WHITE, Color.TRANSPARENT)

class ColorWheel(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    private val viewConfig = ViewConfiguration.get(context)

    private val hueGradient = GradientDrawable().apply {
        gradientType = GradientDrawable.SWEEP_GRADIENT
        shape = GradientDrawable.OVAL
        colors = HUE_COLORS
    }

    private val saturationGradient = GradientDrawable().apply {
        gradientType = GradientDrawable.RADIAL_GRADIENT
        shape = GradientDrawable.OVAL
        colors = SATURATION_COLORS
    }

    private val thumbDrawable = ThumbDrawable()
    private val hsvColor = HsvColor(value = 1f)

    private var wheelCenterX = 0
    private var wheelCenterY = 0
    private var wheelRadius = 0
    private var motionEventDownX = 0f

    var colorChangeListener: ((Int) -> Unit)? = null

    val rgb get() = hsvColor.toRgb()

    var thumbRadius = 0
        set(value) {
            field = value
            updateThumbInsets()
            invalidate()
        }

    init {
        parseAttributes(context, attrs)
        updateThumbInsets()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.ColorWheelDefaultStyle)

    constructor(context: Context) : this(context, null)

    private fun parseAttributes(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ColorWheel, 0, R.style.ColorWheelDefaultStyle).apply {
            thumbRadius = getDimensionPixelSize(R.styleable.ColorWheel_cw_thumbRadius, 0)
            recycle()
        }
    }

    private fun updateThumbInsets() {
        thumbDrawable.applyInsets(thumbRadius.toFloat())
    }

    fun setColor(rgb: Int) {
        hsvColor.set(rgb)
        fireColorListener()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        drawColorWheel(canvas)
        drawThumb(canvas)
    }

    private fun drawColorWheel(canvas: Canvas) {
        val hSpace = width - paddingLeft - paddingRight
        val vSpace = height - paddingTop - paddingBottom

        wheelCenterX = paddingLeft + hSpace / 2
        wheelCenterY = paddingTop + vSpace / 2
        wheelRadius = minOf(hSpace, vSpace) / 2

        val left = wheelCenterX - wheelRadius
        val top = wheelCenterY - wheelRadius
        val right = wheelCenterX + wheelRadius
        val bottom = wheelCenterY + wheelRadius

        hueGradient.setBounds(left, top, right, bottom)
        saturationGradient.setBounds(left, top, right, bottom)
        saturationGradient.gradientRadius = wheelRadius.toFloat()

        hueGradient.draw(canvas)
        saturationGradient.draw(canvas)
    }

    private fun drawThumb(canvas: Canvas) {
        val r = hsvColor.saturation * wheelRadius
        val hueRadians = toRadians(hsvColor.hue)
        val thumbX = (cos(hueRadians) * r + wheelCenterX).toInt()
        val thumbY = (sin(hueRadians) * r + wheelCenterY).toInt()

        thumbDrawable.setBounds(
            thumbX - thumbRadius,
            thumbY - thumbRadius,
            thumbX + thumbRadius,
            thumbY + thumbRadius
        )

        thumbDrawable.indicatorColor = hsvColor.toRgb()
        thumbDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                motionEventDownX = event.x
                updateColorOnMotionEvent(event)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                updateColorOnMotionEvent(event)
            }
            MotionEvent.ACTION_UP -> {
                updateColorOnMotionEvent(event)
                if (isTap(event)) performClick()
            }
        }

        return super.onTouchEvent(event)
    }

    override fun performClick() = super.performClick()

    private fun updateColorOnMotionEvent(event: MotionEvent) {
        calculateColor(event)
        fireColorListener()
        invalidate()
    }

    private fun calculateColor(event: MotionEvent) {
        val legX = event.x - wheelCenterX
        val legY = event.y - wheelCenterY
        val r = calculateRadius(legX, legY)
        val angle = atan2(legY, legX)
        val x = cos(angle) * r + wheelCenterX
        val y = sin(angle) * r + wheelCenterY
        val dx = x - wheelCenterX
        val dy = y - wheelCenterY
        val hue = toDegrees(atan2(dy, dx)) + 360
        val saturation = hypot(dx, dy) / wheelRadius

        hsvColor.set(hue, saturation, 1f)
    }

    private fun calculateRadius(legX: Float, legY: Float): Float {
        val radius = hypot(legX, legY)
        return if (radius > wheelRadius) wheelRadius.toFloat() else radius
    }

    private fun isTap(event: MotionEvent): Boolean {
        val eventDuration = event.eventTime - event.downTime
        val eventTravelDistance = abs(event.x - motionEventDownX)
        return eventDuration < ViewConfiguration.getTapTimeout() && eventTravelDistance < viewConfig.scaledTouchSlop
    }

    private fun fireColorListener() {
        colorChangeListener?.invoke(hsvColor.toRgb())
    }
}
