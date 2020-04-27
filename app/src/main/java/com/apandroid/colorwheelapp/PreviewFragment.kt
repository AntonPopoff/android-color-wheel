package com.apandroid.colorwheelapp

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
import kotlinx.android.synthetic.main.fragment_preview.*

class PreviewFragment : Fragment() {

    private lateinit var colorIndicatorBackground: GradientDrawable

    override fun onAttach(context: Context) {
        super.onAttach(context)
        colorIndicatorBackground = GradientDrawable().apply { cornerRadius = density * 16 }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorWheel.colorChangeListener = this::onColorWheelUpdateListener

        gradientSeekBar.setTransparentToColor(colorWheel.rgb, false)
        gradientSeekBar.setAlphaListener(this::onAlphaSeekBarUpdate)

        valueSeekBar.setBlackToColor(colorWheel.rgb)
        valueSeekBar.colorListener = this::onValueSeekBarUpdate

        colorIndicator.background = colorIndicatorBackground
        colorIndicatorBackground.setColor(setColorAlpha(valueSeekBar.argb, gradientSeekBar.currentColorAlpha))
    }

    private fun onColorWheelUpdateListener(rgb: Int) {
        gradientSeekBar.setTransparentToColor(rgb, false)
        valueSeekBar.setBlackToColor(rgb)
        colorIndicatorBackground.setColor(setColorAlpha(valueSeekBar.argb, gradientSeekBar.currentColorAlpha))
    }

    private fun onAlphaSeekBarUpdate(offset: Float, color: Int, alpha: Int) {
        colorIndicatorBackground.setColor(setColorAlpha(valueSeekBar.argb, alpha))
    }

    private fun onValueSeekBarUpdate(offset: Float, color: Int) {
        colorIndicatorBackground.setColor(setColorAlpha(color, gradientSeekBar.currentColorAlpha))
    }
}
