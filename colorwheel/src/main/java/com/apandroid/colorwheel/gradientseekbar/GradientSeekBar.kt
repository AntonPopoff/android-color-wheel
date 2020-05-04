package com.apandroid.colorwheel.gradientseekbar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.apandroid.colorwheel.R
import com.apandroid.colorwheel.extensions.readBooleanCompat
import com.apandroid.colorwheel.extensions.writeBooleanCompat
import com.apandroid.colorwheel.thumb.ThumbDrawable
import com.apandroid.colorwheel.thumb.ThumbDrawableState
import com.apandroid.colorwheel.thumb.readThumbState
import com.apandroid.colorwheel.thumb.writeThumbState
import com.apandroid.colorwheel.utils.MAX_ALPHA
import com.apandroid.colorwheel.utils.ensureNumberWithinRange
import com.apandroid.colorwheel.utils.interpolateColorLinear
import com.apandroid.colorwheel.utils.setColorAlpha
import kotlin.math.hypot

open class GradientSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val viewConfig = ViewConfiguration.get(context)
    private val gradientColors = IntArray(2)
    private val thumbDrawable = ThumbDrawable()
    private val gradientDrawable = GradientDrawable()

    private lateinit var orientationStrategy: OrientationStrategy
    private var motionEventDownX = 0f
    private var motionEventDownY = 0f

    var startColor
        get() = gradientColors[0]
        set(color) { setColors(startColor = color) }

    var endColor
        get() = gradientColors[1]
        set(color) { setColors(endColor = color) }

    var offset = 0f
        set(offset) {
            field = ensureOffsetWithinRange(offset)
            calculateArgb()
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

    var orientation = Orientation.VERTICAL
        set(orientation) {
            field = orientation
            orientationStrategy = createOrientationStrategy()
            requestLayout()
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

    var thumbRadius
        get() = thumbDrawable.radius
        set(radius) {
            thumbDrawable.radius = radius
            requestLayout()
        }

    var argb = 0
        private set

    var colorListener: ((Float, Int) -> Unit)? = null

    var interceptTouchEvent = true

    init {
        parseAttributes(context, attrs, R.style.GradientSeekBarDefaultStyle)
    }

    private fun parseAttributes(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.GradientSeekBar, 0, defStyle).apply {
            readGradientColors(this)
            thumbColor = getColor(R.styleable.GradientSeekBar_asb_thumbColor, 0)
            thumbStrokeColor = getColor(R.styleable.GradientSeekBar_asb_thumbStrokeColor, 0)
            thumbColorCircleScale = getFloat(R.styleable.GradientSeekBar_asb_thumbColorCircleScale, 0f)
            thumbRadius = getDimensionPixelSize(R.styleable.GradientSeekBar_asb_thumbRadius, 0)
            barSize = getDimensionPixelSize(R.styleable.GradientSeekBar_asb_barSize, 0)
            cornersRadius = getDimension(R.styleable.GradientSeekBar_asb_barCornersRadius, 0f)
            offset = ensureOffsetWithinRange(getFloat(R.styleable.GradientSeekBar_asb_offset, 0f))
            orientation = Orientation.values()[getInt(R.styleable.GradientSeekBar_asb_orientation, 0)]
            recycle()
        }
    }

    private fun readGradientColors(array: TypedArray) {
        setColors(
            array.getColor(R.styleable.GradientSeekBar_asb_startColor, Color.TRANSPARENT),
            array.getColor(R.styleable.GradientSeekBar_asb_endColor, Color.BLACK)
        )
    }

    private fun createOrientationStrategy() = when (orientation) {
        Orientation.VERTICAL -> VerticalStrategy()
        Orientation.HORIZONTAL -> HorizontalStrategy()
    }

    fun setColors(startColor: Int = gradientColors[0], endColor: Int = gradientColors[1]) {
        updateGradientColors(startColor, endColor)
        calculateArgb()
    }

    private fun updateGradientColors(startColor: Int, endColor: Int) {
        gradientColors[0] = startColor
        gradientColors[1] = endColor
        gradientDrawable.colors = gradientColors
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dimens = orientationStrategy.measure(this, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(dimens.width(), dimens.height())
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
        val coordinates = orientationStrategy.calculateThumbCoordinates(this, gradientDrawable.bounds)

        thumbDrawable.indicatorColor = argb
        thumbDrawable.setCoordinates(coordinates.x, coordinates.y)
        thumbDrawable.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(interceptTouchEvent)
                calculateOffsetOnMotionEvent(event)
                motionEventDownX = event.x
                motionEventDownY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                calculateOffsetOnMotionEvent(event)
            }
            MotionEvent.ACTION_UP -> {
                calculateOffsetOnMotionEvent(event)
                if (isTap(event)) performClick()
            }
        }

        return true
    }

    override fun performClick() = super.performClick()

    private fun isTap(event: MotionEvent): Boolean {
        val eventDuration = event.eventTime - event.downTime
        val travelDistance = hypot(motionEventDownX - event.x, motionEventDownY - event.y)
        return eventDuration < ViewConfiguration.getTapTimeout() && travelDistance < viewConfig.scaledTouchSlop
    }

    private fun calculateOffsetOnMotionEvent(event: MotionEvent) {
        offset = orientationStrategy.calculateOffsetOnMotionEvent(this, event, gradientDrawable.bounds)
    }

    private fun calculateArgb() {
        argb = interpolateColorLinear(gradientColors[0], gradientColors[1], offset)
        fireListener()
        invalidate()
    }

    private fun fireListener() {
        colorListener?.invoke(offset, argb)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val thumbState = thumbDrawable.saveState()
        return GradientSeekBarState(superState, this, thumbState)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is GradientSeekBarState) {
            super.onRestoreInstanceState(state.superState)
            readGradientSeekBarState(state)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private fun readGradientSeekBarState(state: GradientSeekBarState) {
        updateGradientColors(state.startColor, state.endColor)
        offset = state.offset
        barSize = state.barSize
        cornersRadius = state.cornerRadius
        orientation = Orientation.values()[state.orientation]
        interceptTouchEvent = state.interceptTouchEvent
        thumbDrawable.restoreState(state.thumbState)
    }

    enum class Orientation { VERTICAL, HORIZONTAL }
}

private class GradientSeekBarState : View.BaseSavedState {

    val startColor: Int
    val endColor: Int
    val offset: Float
    val barSize: Int
    val cornerRadius: Float
    val orientation: Int
    val interceptTouchEvent: Boolean
    val thumbState: ThumbDrawableState

    constructor(superState: Parcelable?, view: GradientSeekBar, thumbDrawableState: ThumbDrawableState) : super(superState) {
        startColor = view.startColor
        endColor = view.endColor
        offset = view.offset
        barSize = view.barSize
        cornerRadius = view.cornersRadius
        orientation = view.orientation.ordinal
        interceptTouchEvent = view.interceptTouchEvent
        thumbState = thumbDrawableState
    }

    constructor(source: Parcel) : super(source) {
        startColor = source.readInt()
        endColor = source.readInt()
        offset = source.readFloat()
        barSize = source.readInt()
        cornerRadius = source.readFloat()
        orientation = source.readInt()
        interceptTouchEvent = source.readBooleanCompat()
        thumbState = source.readThumbState()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(startColor)
        out.writeInt(endColor)
        out.writeFloat(offset)
        out.writeInt(barSize)
        out.writeFloat(cornerRadius)
        out.writeInt(orientation)
        out.writeBooleanCompat(interceptTouchEvent)
        out.writeThumbState(thumbState, flags)
    }

    companion object CREATOR : Parcelable.Creator<GradientSeekBarState> {

        override fun createFromParcel(source: Parcel) = GradientSeekBarState(source)

        override fun newArray(size: Int) = arrayOfNulls<GradientSeekBarState>(size)
    }
}

val GradientSeekBar.currentColorAlpha get() = Color.alpha(argb)

fun GradientSeekBar.setTransparentToColor(color: Int, respectAlpha: Boolean = true) {
    if (respectAlpha) this.offset = Color.alpha(color) / MAX_ALPHA.toFloat()
    this.setColors(setColorAlpha(color, 0), setColorAlpha(color, MAX_ALPHA))
}

inline fun GradientSeekBar.setAlphaListener(crossinline listener: (Float, Int, Int) -> Unit) {
    this.colorListener = { offset, color -> listener(offset, color, this.currentColorAlpha) }
}

fun GradientSeekBar.setBlackToColor(color: Int) {
    this.setColors(Color.BLACK, color)
}

private fun ensureOffsetWithinRange(offset: Float) = ensureNumberWithinRange(offset, 0f, 1f)
