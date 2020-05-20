package com.apandroid.colorwheel.thumb

import android.os.Parcel
import android.os.Parcelable

internal class ThumbDrawableState private constructor(
    val radius: Int,
    val thumbColor: Int,
    val strokeColor: Int,
    val colorCircleScale: Float,
    val colorShadow: Int,
    val shadowRadius: Float
) : Parcelable {

    constructor(thumbDrawable: ThumbDrawable) : this(
        thumbDrawable.radius,
        thumbDrawable.thumbColor,
        thumbDrawable.strokeColor,
        thumbDrawable.colorCircleScale,
        thumbDrawable.shadowColor,
        thumbDrawable.shadowRadius
    )

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(radius)
        parcel.writeInt(thumbColor)
        parcel.writeInt(strokeColor)
        parcel.writeFloat(colorCircleScale)
        parcel.writeInt(colorShadow)
        parcel.writeFloat(shadowRadius)
    }

    override fun describeContents() = 0

    companion object {

        val EMPTY_STATE = ThumbDrawableState(0, 0, 0, 0f, 0, 0f)

        @JvmField
        val CREATOR = object : Parcelable.Creator<ThumbDrawableState> {

            override fun createFromParcel(parcel: Parcel) = ThumbDrawableState(parcel)

            override fun newArray(size: Int) = arrayOfNulls<ThumbDrawableState>(size)
        }
    }
}

internal fun Parcel.writeThumbState(state: ThumbDrawableState, flags: Int) {
    this.writeParcelable(state, flags)
}

internal fun Parcel.readThumbState() = this.readParcelable(ThumbDrawableState::class.java.classLoader) ?: ThumbDrawableState.EMPTY_STATE
