package com.apandroid.colorwheelapp.extensions.android.views

import android.widget.SeekBar
import com.apandroid.colorwheelapp.utils.listeners.EmptySeekBarChangeListener

fun SeekBar.setOnProgressChangeListener(listener: (Int) -> Unit) {
    this.setOnSeekBarChangeListener(object : EmptySeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            listener(progress)
        }
    })
}
