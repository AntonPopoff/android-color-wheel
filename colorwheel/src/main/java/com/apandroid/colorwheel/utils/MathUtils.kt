package com.apandroid.colorwheel.utils

internal const val PI = Math.PI.toFloat()

internal fun toRadians(degrees: Float) = degrees / 180f * PI

internal fun toDegrees(radians: Float) = radians * 180f / PI

internal fun <T> ensureWithinRange(
    value: T,
    start: T,
    end: T
): T where T : Number, T : Comparable<T> {
    return when {
        value < start -> start
        value > end -> end
        else -> value
    }
}
