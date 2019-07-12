package com.apandroid.colorwheel.utils

internal const val PI = Math.PI.toFloat()

internal fun toRadians(degrees: Float) = degrees / 180f * PI

internal fun toDegrees(radians: Float) = radians * 180f / PI
