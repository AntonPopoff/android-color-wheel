package com.apandroid.colorwheelapp.gradientseekbartest

import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.apandroid.colorwheelapp.R

class TestGradientSeekBarViewModel : ViewModel() {

    val barSizeObservable = ObservableInt()
    val cornerRadiusObservable = ObservableFloat()
    val thumbRadiusObservable = ObservableInt()
    val colorCircleScaleObservable = ObservableFloat()
    val offsetObservable = ObservableFloat()

    fun onBarSizeTextChanged(text: CharSequence) {
        barSizeObservable.set(text.toString().toIntOrNull() ?: 0)
    }

    fun onCornerRadiusTextChanged(text: CharSequence) {
        cornerRadiusObservable.set(text.toString().toFloatOrNull() ?: 0f)
    }

    fun onThumbRadiusTextChanged(text: CharSequence) {
        thumbRadiusObservable.set(text.toString().toIntOrNull() ?: 0)
    }

    fun onOrientationRadioChanged(id: Int) {
        if (id == R.id.verticalOrientationButton) {
            // TODO Set vertical orientation
        } else if (id == R.id.horizontalOrientationButton) {
            // TODO Set horizontal orientation
        }
    }
}
