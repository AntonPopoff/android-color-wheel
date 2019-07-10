package com.colorwheelapp.colorwheel.utils

internal fun <T> ensureNumberWithinRange(value: T, start: T, end: T): T where T : Number, T : Comparable<T> = when {
    value < start -> start
    value > end -> end
    else -> value
}
