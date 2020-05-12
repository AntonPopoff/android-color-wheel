package com.apandroid.colorwheelapp.gradientseekbartest

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.apandroid.colorwheel.gradientseekbar.GradientSeekBar
import com.apandroid.colorwheelapp.R

class TestGradientSeekBarViewModel : ViewModel() {

    val barSizeObservable = ObservableInt()
    val cornerRadiusObservable = ObservableInt()
    val thumbRadiusObservable = ObservableInt()
    val colorCircleScaleObservable = ObservableFloat()
    val offsetObservable = ObservableFloat()
    val gradientSeekBarOrientation = ObservableField<GradientSeekBar.Orientation>()

    fun onOrientationRadioChanged(id: Int) {
        if (id == R.id.verticalOrientationButton) {
            gradientSeekBarOrientation.set(GradientSeekBar.Orientation.VERTICAL)
        } else if (id == R.id.horizontalOrientationButton) {
            gradientSeekBarOrientation.set(GradientSeekBar.Orientation.HORIZONTAL)
        }
    }
}
