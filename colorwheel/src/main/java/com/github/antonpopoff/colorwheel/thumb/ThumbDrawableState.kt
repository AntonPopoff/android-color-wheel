package com.github.antonpopoff.colorwheel.thumb

import android.os.Parcel
import android.os.Parcelable

internal class ThumbDrawableState private constructor(
    val radius: Int,
    val thumbColor: Int,
    val strokeColor: Int,
    val colorCircleScale: Float
) : Parcelable {

    constructor(thumbDrawable: ThumbDrawable) : this(
        thumbDrawable.radius,
        thumbDrawable.thumbColor,
        thumbDrawable.strokeColor,
        thumbDrawable.colorCircleScale
    )

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(radius)
        parcel.writeInt(thumbColor)
        parcel.writeInt(strokeColor)
        parcel.writeFloat(colorCircleScale)
    }

    override fun describeContents() = 0

    companion object {

        val EMPTY_STATE = ThumbDrawableState(0, 0, 0, 0f)

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

internal fun Parcel.readThumbState(): ThumbDrawableState {
    return this.readParcelable(ThumbDrawableState::class.java.classLoader)
        ?: ThumbDrawableState.EMPTY_STATE
}
