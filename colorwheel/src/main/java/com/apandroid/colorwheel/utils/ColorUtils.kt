package com.apandroid.colorwheel.utils

import android.graphics.Color
import kotlin.math.roundToInt

internal fun setAlpha(argb: Int, alpha: Int) =
    Color.argb(alpha, Color.red(argb), Color.green(argb), Color.blue(argb))
