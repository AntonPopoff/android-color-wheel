package com.github.antonpopoff.colorwheel.gradientseekbar

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.github.antonpopoff.colorwheel.extensions.readBooleanCompat
import com.github.antonpopoff.colorwheel.extensions.writeBooleanCompat
import com.github.antonpopoff.colorwheel.thumb.ThumbDrawableState
import com.github.antonpopoff.colorwheel.thumb.readThumbState
import com.github.antonpopoff.colorwheel.thumb.writeThumbState

internal class GradientSeekBarState : View.BaseSavedState {

    val startColor: Int
    val endColor: Int
    val offset: Float
    val barSize: Int
    val cornerRadius: Float
    val orientation: Int
    val interceptTouchEvent: Boolean
    val thumbState: ThumbDrawableState

    constructor(
        superState: Parcelable?,
        view: GradientSeekBar,
        thumbState: ThumbDrawableState
    ) : super(superState) {
        startColor = view.startColor
        endColor = view.endColor
        offset = view.offset
        barSize = view.barSize
        cornerRadius = view.cornersRadius
        orientation = view.orientation.ordinal
        interceptTouchEvent = view.interceptTouchEvent
        this.thumbState = thumbState
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
