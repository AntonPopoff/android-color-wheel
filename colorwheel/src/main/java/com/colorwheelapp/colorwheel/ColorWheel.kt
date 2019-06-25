package com.colorwheelapp.colorwheel

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.colorwheelapp.colorwheel.utils.HsvColor
import com.colorwheelapp.colorwheel.utils.toDegrees
import com.colorwheelapp.colorwheel.utils.toRadians
import kotlin.math.*

class ColorWheel(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {

    private val viewConfig = ViewConfiguration.get(context)

    private val hueColors = intArrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.RED)
    private val saturationColors = intArrayOf(Color.WHITE, Color.TRANSPARENT)

    private val huePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val saturationPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val wheelCenter = PointF()
    private var wheelRadius = 0f

    private val thumbDrawable = ThumbDrawable()
    private val hsvColor = HsvColor(value = 1f)

    private var motionEventDownX = 0f

    var colorChangeListener: ((Int) -> Unit)? = null

    val argb get() = hsvColor.toArgb()

    var thumbRadius = 0f
        set(value) {
            thumbDrawable.applyInsets(value)
            field = value
            invalidate()
        }

    init {
        parseAttributes(context, attrs)
        thumbDrawable.applyInsets(thumbRadius)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.ColorWheelDefaultStyle)

    constructor(context: Context) : this(context, null)

    private fun parseAttributes(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ColorWheel, 0, R.style.ColorWheelDefaultStyle).apply {
            thumbRadius = getDimension(R.styleable.ColorWheel_cw_thumbRadius, 0f)
            recycle()
        }
    }

    fun setColor(argb: Int) {
        hsvColor.set(argb)
        fireColorListener()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        createGradients(w, h)
    }

    private fun createGradients(w: Int, h: Int) {
        val hSpace = h - paddingLeft - paddingRight
        val vSpace = w - paddingTop - paddingBottom

        wheelRadius = minOf(hSpace, vSpace) / 2f

        if (wheelRadius >= 0) {
            wheelCenter.set(w / 2f, h / 2f)
            huePaint.shader = SweepGradient(wheelCenter.x, wheelCenter.y, hueColors, null)
            saturationPaint.shader = RadialGradient(wheelCenter.x, wheelCenter.y, wheelRadius, saturationColors, null, Shader.TileMode.CLAMP)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(wheelCenter.x, wheelCenter.y, wheelRadius, huePaint)
        canvas.drawCircle(wheelCenter.x, wheelCenter.y, wheelRadius, saturationPaint)
        calculateThumbRect()
        drawThumb(canvas)
    }

    private fun calculateThumbRect() {
        val r = hsvColor.saturation * wheelRadius
        val hueRadians = toRadians(hsvColor.hue)
        val thumbX = cos(hueRadians) * r + wheelCenter.x
        val thumbY = sin(hueRadians) * r + wheelCenter.y

        thumbDrawable.setBounds(
                (thumbX - thumbRadius).toInt(),
                (thumbY - thumbRadius).toInt(),
                (thumbX + thumbRadius).toInt(),
                (thumbY + thumbRadius).toInt()
        )
    }

    private fun drawThumb(canvas: Canvas) {
        thumbDrawable.indicatorColor = hsvColor.toArgb()
        thumbDrawable.draw(canvas)
    }

    private fun fireColorListener() {
        colorChangeListener?.invoke(hsvColor.toArgb())
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
        val legX = event.x - wheelCenter.x
        val legY = event.y - wheelCenter.y
        val r = calculateRadius(legX, legY)
        val angle = atan2(legY, legX)
        val x = cos(angle) * r + wheelCenter.x
        val y = sin(angle) * r + wheelCenter.y

        calculateColor(x, y, wheelCenter.x, wheelCenter.y, wheelRadius)
        fireColorListener()
        invalidate()
    }

    private fun calculateRadius(legX: Float, legY: Float): Float {
        val radius = hypot(legX, legY)
        return if (radius > wheelRadius) wheelRadius else radius
    }

    private fun calculateColor(x: Float, y: Float, cx: Float, cy: Float, r: Float) {
        val dx = x - cx
        val dy = y - cy
        val hue = toDegrees(atan2(dy, dx)) + 360
        val saturation = hypot(dx, dy) / wheelRadius

        hsvColor.set(hue, saturation, 1f)
    }

    private fun isTap(event: MotionEvent): Boolean {
        val eventDuration = event.eventTime - event.downTime
        val eventTravelDistance = abs(event.x - motionEventDownX)
        return eventDuration < ViewConfiguration.getTapTimeout() && eventTravelDistance < viewConfig.scaledTouchSlop
    }
}
