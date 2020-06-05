package com.apandroid.colorwheel.utils

import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.hypot

internal fun isTap(lastEvent: MotionEvent, initialX: Float, initialY: Float, config: ViewConfiguration): Boolean {
    val duration = lastEvent.eventTime - lastEvent.downTime
    val distance = hypot(lastEvent.x - initialX, lastEvent.y - initialY)
    return duration < ViewConfiguration.getTapTimeout() && distance < config.scaledTouchSlop
}
