package com.apandroid.colorwheelapp.preview

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apandroid.colorwheel.gradientseekbar.*
import com.apandroid.colorwheelapp.extensions.density
import com.apandroid.colorwheel.utils.setColorAlpha
import com.apandroid.colorwheelapp.R
import kotlinx.android.synthetic.main.fragment_preview.*

class PreviewFragment : Fragment(R.layout.fragment_preview) {

    private lateinit var colorIndicatorBackground: GradientDrawable

    override fun onAttach(context: Context) {
        super.onAttach(context)
        colorIndicatorBackground = GradientDrawable().apply { cornerRadius = density * 16 }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorWheel.colorChangeListener = this::onColorWheelUpdateListener

        gradientSeekBar.setAlphaRgb(colorWheel.rgb)
        gradientSeekBar.setAlphaListener(this::onAlphaSeekBarUpdate)

        valueSeekBar.setBlackToColor(colorWheel.rgb)
        valueSeekBar.listener = this::onValueSeekBarUpdate

        colorIndicator.background = colorIndicatorBackground
        colorIndicatorBackground.setColor(setColorAlpha(valueSeekBar.argb, gradientSeekBar.argbAlpha))
    }

    private fun onColorWheelUpdateListener(rgb: Int) {
        gradientSeekBar.setAlphaRgb(rgb)
        valueSeekBar.setBlackToColor(rgb)
        colorIndicatorBackground.setColor(setColorAlpha(valueSeekBar.argb, gradientSeekBar.argbAlpha))
    }

    private fun onAlphaSeekBarUpdate(offset: Float, color: Int, alpha: Int) {
        colorIndicatorBackground.setColor(setColorAlpha(valueSeekBar.argb, alpha))
    }

    private fun onValueSeekBarUpdate(offset: Float, color: Int) {
        colorIndicatorBackground.setColor(setColorAlpha(color, gradientSeekBar.argbAlpha))
    }
}
