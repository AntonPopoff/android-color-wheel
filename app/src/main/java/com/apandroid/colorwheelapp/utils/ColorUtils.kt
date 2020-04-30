package com.apandroid.colorwheelapp.utils

import android.graphics.Color
import java.util.*

fun randomArgb(random: Random) = Color.argb(random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(256))

fun randomRgb(random: Random) = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255))

fun setColorAlpha(argb: Int, alpha: Int) = Color.argb(
    alpha,
    Color.red(argb),
    Color.green(argb),
    Color.blue(argb)
)
