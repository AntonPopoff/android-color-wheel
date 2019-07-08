package com.apandroid.colorwheelapp

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.extensions.density
import com.colorwheelapp.colorwheel.gradientseekbar.currentAlpha
import com.colorwheelapp.colorwheel.gradientseekbar.setAlphaListener
import com.colorwheelapp.colorwheel.gradientseekbar.setAlphaRgb
import com.colorwheelapp.colorwheel.gradientseekbar.setValueColor
import com.colorwheelapp.colorwheel.utils.setAlphaComponent
import kotlinx.android.synthetic.main.fragment_preview.*

class PreviewFragment : Fragment() {

    private lateinit var colorIndicatorBackground: GradientDrawable

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        colorIndicatorBackground = GradientDrawable().apply { cornerRadius = density * 16 }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorWheel.colorChangeListener = this::onColorWheelUpdateListener

        alphaSeekBar.setAlphaRgb(colorWheel.rgb)
        alphaSeekBar.setAlphaListener(this::onAlphaSeekBarUpdate)

        valueSeekBar.setValueColor(colorWheel.rgb)
        valueSeekBar.listener = this::onValueSeekBarUpdate

        colorIndicator.background = colorIndicatorBackground
        colorIndicatorBackground.setColor(setAlphaComponent(valueSeekBar.currentColor, alphaSeekBar.currentAlpha))
    }

    private fun onColorWheelUpdateListener(rgb: Int) {
        alphaSeekBar.setAlphaRgb(rgb)
        valueSeekBar.setValueColor(rgb)
        colorIndicatorBackground.setColor(setAlphaComponent(valueSeekBar.currentColor, alphaSeekBar.currentAlpha))
    }

    private fun onAlphaSeekBarUpdate(offset: Float, color: Int, alpha: Int) {
        colorIndicatorBackground.setColor(setAlphaComponent(valueSeekBar.currentColor, alpha))
    }

    private fun onValueSeekBarUpdate(offset: Float, color: Int) {
        colorIndicatorBackground.setColor(setAlphaComponent(color, alphaSeekBar.currentAlpha))
    }
}
