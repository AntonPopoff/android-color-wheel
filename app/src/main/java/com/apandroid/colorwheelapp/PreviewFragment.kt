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
import com.colorwheelapp.colorwheel.gradientseekbar.setAlphaSilently
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
        colorIndicator.background = colorIndicatorBackground
        colorIndicatorBackground.setColor(setAlphaComponent(colorWheel.rgb, alphaSeekBar.currentAlpha))
        colorWheel.colorChangeListener = this::onColorWheelUpdateListener
        alphaSeekBar.setAlphaListener(this::onAlphaSeekBarUpdate)
        horizontalAlphaSeekBar.setAlphaListener(this::onHorizontalAlphaSeekBarUpdate)
        alphaSeekBar.setAlphaRgb(colorWheel.rgb)
        horizontalAlphaSeekBar.setAlphaRgb(colorWheel.rgb)
    }

    private fun onColorWheelUpdateListener(rgb: Int) {
        alphaSeekBar.setAlphaRgb(rgb)
        horizontalAlphaSeekBar.setAlphaRgb(colorWheel.rgb)
        colorIndicatorBackground.setColor(setAlphaComponent(rgb, alphaSeekBar.currentAlpha))
    }

    private fun onAlphaSeekBarUpdate(alpha: Int) {
        colorIndicatorBackground.setColor(setAlphaComponent(colorWheel.rgb, alpha))
        horizontalAlphaSeekBar.setAlphaSilently(alpha)
    }

    private fun onHorizontalAlphaSeekBarUpdate(alpha: Int) {
        colorIndicatorBackground.setColor(setAlphaComponent(colorWheel.rgb, alpha))
        alphaSeekBar.setAlphaSilently(alpha)
    }
}
