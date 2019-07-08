package com.colorwheelapp.colorwheel.utils

fun <T> ensureNumberWithinRange(value: T, start: T, end: T): T where T : Number, T : Comparable<T> {
    if (start > end) throw IllegalArgumentException("Start must be lower than end. (start = $start, end = $end)")

    return when {
        value < start -> start
        value > end -> end
        else -> value
    }
}
