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
        colorIndicatorBackground.setColor(alphaSeekBar.currentColor)

        colorWheel.colorChangeListener = this::onColorWheelUpdateListener

        alphaSeekBar.setAlphaRgb(colorWheel.rgb)
        alphaSeekBar.setAlphaListener(this::onAlphaSeekBarUpdate)

        horizontalAlphaSeekBar.setAlphaRgb(colorWheel.rgb)
        horizontalAlphaSeekBar.setAlphaListener(this::onHorizontalAlphaSeekBarUpdate)
    }

    private fun onColorWheelUpdateListener(rgb: Int) {
        alphaSeekBar.setAlphaRgb(rgb)
        horizontalAlphaSeekBar.setAlphaRgb(rgb)
        colorIndicatorBackground.setColor(alphaSeekBar.currentColor)
    }

    private fun onAlphaSeekBarUpdate(offset: Float, color: Int, alpha: Int) {
        colorIndicatorBackground.setColor(color)
        horizontalAlphaSeekBar.setAlphaSilently(alpha)
    }

    private fun onHorizontalAlphaSeekBarUpdate(offset: Float, color: Int, alpha: Int) {
        colorIndicatorBackground.setColor(color)
        alphaSeekBar.setAlphaSilently(alpha)
    }
}
