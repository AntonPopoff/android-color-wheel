package com.github.antonpopoff.colorwheel.extensions

import android.os.Build
import android.os.Parcel

internal fun Parcel.writeBooleanCompat(value: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.writeBoolean(value)
    } else {
        this.writeInt(if (value) 1 else 0)
    }
}

internal fun Parcel.readBooleanCompat(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.readBoolean()
    } else {
        this.readInt() == 1
    }
}
