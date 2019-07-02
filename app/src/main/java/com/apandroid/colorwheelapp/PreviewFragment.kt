package com.apandroid.colorwheelapp

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.colorwheelapp.colorwheel.alphaseekbar.setAlphaSilently
import com.colorwheelapp.colorwheel.utils.setAlpha
import kotlinx.android.synthetic.main.fragment_preview.*

class PreviewFragment : Fragment() {

    private lateinit var colorIndicatorBackground: GradientDrawable

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        colorIndicatorBackground = GradientDrawable().apply { cornerRadius = resources.displayMetrics.density * 16 }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorIndicator.background = colorIndicatorBackground
        colorIndicatorBackground.setColor(setAlpha(colorWheel.rgb, alphaSeekBar.alphaValue))
        colorWheel.colorChangeListener = this::onColorWheelUpdateListener
        alphaSeekBar.alphaChangeListener = this::onAlphaSeekBarUpdate
        horizontalAlphaSeekBar.alphaChangeListener = this::onHorizontalAlphaSeekBarUpdate
        alphaSeekBar.rgb = colorWheel.rgb
        horizontalAlphaSeekBar.rgb = colorWheel.rgb
    }

    private fun onColorWheelUpdateListener(rgb: Int) {
        alphaSeekBar.rgb = rgb
        horizontalAlphaSeekBar.rgb = colorWheel.rgb
        colorIndicatorBackground.setColor(setAlpha(rgb, alphaSeekBar.alphaValue))
    }

    private fun onAlphaSeekBarUpdate(alpha: Int) {
        colorIndicatorBackground.setColor(setAlpha(colorWheel.rgb, alpha))
        horizontalAlphaSeekBar.setAlphaSilently(alpha)
    }

    private fun onHorizontalAlphaSeekBarUpdate(alpha: Int) {
        colorIndicatorBackground.setColor(setAlpha(colorWheel.rgb, alpha))
        alphaSeekBar.setAlphaSilently(alpha)
    }
}