package com.github.antonpopoff.colorwheel

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.github.antonpopoff.colorwheel.extensions.readBooleanCompat
import com.github.antonpopoff.colorwheel.extensions.writeBooleanCompat
import com.github.antonpopoff.colorwheel.thumb.ThumbDrawableState
import com.github.antonpopoff.colorwheel.thumb.readThumbState
import com.github.antonpopoff.colorwheel.thumb.writeThumbState

internal class ColorWheelState : View.BaseSavedState {

    val thumbState: ThumbDrawableState
    val interceptTouchEvent: Boolean
    val rgb: Int

    constructor(
        superState: Parcelable?,
        view: ColorWheel,
        thumbState: ThumbDrawableState
    ) : super(superState) {
        this.thumbState = thumbState
        interceptTouchEvent = view.interceptTouchEvent
        rgb = view.rgb
    }

    constructor(source: Parcel) : super(source) {
        thumbState = source.readThumbState()
        interceptTouchEvent = source.readBooleanCompat()
        rgb = source.readInt()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeThumbState(thumbState, flags)
        out.writeBooleanCompat(interceptTouchEvent)
        out.writeInt(rgb)
    }

    companion object CREATOR : Parcelable.Creator<ColorWheelState> {

        override fun createFromParcel(source: Parcel) = ColorWheelState(source)

        override fun newArray(size: Int) = arrayOfNulls<ColorWheelState>(size)
    }
}
