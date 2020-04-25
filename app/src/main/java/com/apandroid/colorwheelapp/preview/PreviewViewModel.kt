package com.apandroid.colorwheelapp.preview

import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.apandroid.colorwheel.utils.setColorAlpha

class PreviewViewModel : ViewModel() {

    private var alpha = 0
    private var blackColor = 0

    val indicatorColor = ObservableInt()

    val colorWheelRgb = ObservableInt().apply {
        this.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {

            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                indicatorColor.set(setColorAlpha(blackColor, alpha))
            }
        })
    }

    fun setAlphaColor(color: Int) {
        alpha = color
        indicatorColor.set(setColorAlpha(blackColor, alpha))
    }

    fun setBlackColor(color: Int) {
        blackColor = color
        indicatorColor.set(setColorAlpha(color, alpha))
    }
}
