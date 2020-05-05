package com.apandroid.colorwheelapp.preview

import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.apandroid.colorwheelapp.utils.setColorAlpha

class PreviewViewModel : ViewModel() {

    val indicatorColor = ObservableInt()

    fun onColorChanged(valueGradient: Int, alphaGradientColor: Int) {
        indicatorColor.set(setColorAlpha(valueGradient, alphaGradientColor))
    }
}
