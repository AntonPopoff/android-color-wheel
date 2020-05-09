package com.apandroid.colorwheelapp.binding.adapters

import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.apandroid.colorwheelapp.extensions.android.views.setOnProgressChangeListener

@BindingAdapter("progress")
fun SeekBar.bindProgress(progress: Int) {
    if (this.progress != progress) this.progress = progress
}

@InverseBindingAdapter(attribute = "progress")
fun SeekBar.receiveProgress() = this.progress

@BindingAdapter("progressAttrChanged")
fun SeekBar.bindProgressAttributeListener(listener: InverseBindingListener) {
    this.setOnProgressChangeListener { listener.onChange() }
}
