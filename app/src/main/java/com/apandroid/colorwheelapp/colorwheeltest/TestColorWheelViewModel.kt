package com.apandroid.colorwheelapp.colorwheeltest

import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel

class TestColorWheelViewModel : ViewModel() {

    val thumbRadiusObservable = ObservableInt()
    val colorWheelPaddingObservable = ObservableInt()
    val colorCircleScaleObservable = ObservableFloat()
}
