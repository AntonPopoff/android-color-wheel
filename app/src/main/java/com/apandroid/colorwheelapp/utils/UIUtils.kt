package com.apandroid.colorwheelapp.utils

import android.content.Context
import kotlin.math.roundToInt

fun dpToPixels(context: Context, dp: Int) = (context.resources.displayMetrics.density * dp).roundToInt()

fun pixelsToDp(context: Context, pixels: Int) = (pixels / context.resources.displayMetrics.density).roundToInt()
