package com.github.antonpopoff.colorwheel

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.github.antonpopoff.colorwheel.thumb.ThumbDrawable
import com.github.antonpopoff.colorwheel.utils.*
import com.github.antonpopoff.colorwheel.utils.toDegrees
import com.github.antonpopoff.colorwheel.utils.toRadians
import kotlin.math.*

private val HUE_COLORS = intArrayOf(
    Color.RED,
    Color.YELLOW,
    Color.GREEN,
    Color.CYAN,
    Color.BLUE,
    Color.MAGENTA,
    Color.RED
)

private val SATURATION_COLORS = intArrayOf(
    Color.WHITE,
    setAlpha(Color.WHITE, 0)
)

open class ColorWheel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

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
    private var downX = 0f
    private var downY = 0f

    var rgb
        get() = hsvColor.rgb
        set(rgb) {
            hsvColor.rgb = rgb
            hsvColor.set(value = 1f)
            fireColorListener()
            invalidate()
        }

    var thumbRadius
        get() = thumbDrawable.radius
        set(value) {
            thumbDrawable.radius = value
            invalidate()
        }

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

    var colorChangeListener: ((Int) -> Unit)? = null

    var interceptTouchEvent = true

    init {
        parseAttributes(context, attrs)
    }

    private fun parseAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.ColorWheel,
            0,
            R.style.ColorWheelDefaultStyle
        )
        readThumbRadius(array)
        readThumbColor(array)
        readStrokeColor(array)
        readColorCircleScale(array)
        array.recycle()
    }

    private fun readThumbRadius(array: TypedArray) {
        thumbRadius = array.getDimensionPixelSize(R.styleable.ColorWheel_tb_thumbRadius, 0)
    }

    private fun readThumbColor(array: TypedArray) {
        thumbColor = array.getColor(R.styleable.ColorWheel_tb_thumbColor, 0)
    }

    private fun readStrokeColor(array: TypedArray) {
        thumbStrokeColor = array.getColor(R.styleable.ColorWheel_tb_thumbStrokeColor, 0)
    }

    private fun readColorCircleScale(array: TypedArray) {
        thumbColorCircleScale = array.getFloat(R.styleable.ColorWheel_tb_thumbColorCircleScale, 0f)
    }

    fun setRgb(r: Int, g: Int, b: Int) {
        rgb = Color.rgb(r, g, b)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minDimension = minOf(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )

        setMeasuredDimension(
            resolveSize(minDimension, widthMeasureSpec),
            resolveSize(minDimension, heightMeasureSpec)
        )
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
        wheelRadius = maxOf(minOf(hSpace, vSpace) / 2, 0)

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
        val x = cos(hueRadians) * r + wheelCenterX
        val y = sin(hueRadians) * r + wheelCenterY

        thumbDrawable.indicatorColor = hsvColor.rgb
        thumbDrawable.setCoordinates(x, y)
        thumbDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> onActionDown(event)
            MotionEvent.ACTION_MOVE -> updateColorOnMotionEvent(event)
            MotionEvent.ACTION_UP -> {
                updateColorOnMotionEvent(event)
                if (isTap(event, downX, downY)) performClick()
            }
        }

        return true
    }

    private fun onActionDown(event: MotionEvent) {
        parent.requestDisallowInterceptTouchEvent(interceptTouchEvent)
        updateColorOnMotionEvent(event)
        downX = event.x
        downY = event.y
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
        val hypot = minOf(hypot(legX, legY), wheelRadius.toFloat())
        val hue = (toDegrees(atan2(legY, legX)) + 360) % 360
        val saturation = hypot / wheelRadius
        hsvColor.set(hue, saturation, 1f)
    }

    private fun fireColorListener() {
        colorChangeListener?.invoke(hsvColor.rgb)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val thumbState = thumbDrawable.saveState()
        return ColorWheelState(superState, this, thumbState)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is ColorWheelState) {
            super.onRestoreInstanceState(state.superState)
            readColorWheelState(state)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private fun readColorWheelState(state: ColorWheelState) {
        thumbDrawable.restoreState(state.thumbState)
        interceptTouchEvent = state.interceptTouchEvent
        rgb = state.rgb
    }
}
