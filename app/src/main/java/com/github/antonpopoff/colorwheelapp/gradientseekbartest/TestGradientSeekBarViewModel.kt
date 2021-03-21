package com.github.antonpopoff.colorwheelapp.gradientseekbartest

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.github.antonpopoff.colorwheel.gradientseekbar.GradientSeekBar

class TestGradientSeekBarViewModel : ViewModel() {

    val barSizeObservable = ObservableInt()
    val cornerRadiusObservable = ObservableInt()
    val thumbRadiusObservable = ObservableInt()
    val colorCircleScaleObservable = ObservableFloat()
    val offsetObservable = ObservableFloat()
    val gradientSeekBarOrientation = ObservableField<GradientSeekBar.Orientation>()
}
