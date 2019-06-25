package com.colorwheelapp.colorwheel.extensions

import android.graphics.PointF
import android.view.MotionEvent

fun PointF.set(motionEvent: MotionEvent) = this.set(motionEvent.x, motionEvent.y)