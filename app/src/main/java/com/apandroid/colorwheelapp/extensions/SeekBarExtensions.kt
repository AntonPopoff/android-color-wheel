package com.apandroid.colorwheelapp.extensions

import android.widget.SeekBar

interface EmptySeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) { }

    override fun onStartTrackingTouch(seekBar: SeekBar) { }

    override fun onStopTrackingTouch(seekBar: SeekBar) { }
}

fun SeekBar.setOnProgressChangeListener(listener: (Int) -> Unit) {
    this.setOnSeekBarChangeListener(object : EmptySeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            listener(progress)
        }
    })
}
