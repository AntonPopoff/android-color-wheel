package com.github.antonpopoff.colorwheelapp.preview

import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.github.antonpopoff.colorwheelapp.utils.setColorAlpha

class PreviewViewModel : ViewModel() {

    val indicatorColor = ObservableInt()

    fun onColorChanged(valueGradient: Int, alphaGradientColor: Int) {
        indicatorColor.set(setColorAlpha(valueGradient, alphaGradientColor))
    }
}
