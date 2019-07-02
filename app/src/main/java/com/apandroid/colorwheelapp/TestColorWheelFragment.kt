package com.apandroid.colorwheelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.extensions.density
import com.apandroid.colorwheelapp.extensions.setOnProgressChangeListener
import kotlinx.android.synthetic.main.fragment_test_color_wheel.*
import java.util.*
import kotlin.math.roundToInt

class TestColorWheelFragment : Fragment() {

    private val random by lazy { Random() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_color_wheel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomizeColorButton.setOnClickListener { randomizeColorWheelColor() }
        colorWheelPaddingSeekBar.setOnProgressChangeListener(this::onColorWheelPaddingSeekBarChange)
        thumbRadiusSeekBar.setOnProgressChangeListener(this::onThumbSeekBarChange)
        colorWheelPaddingSeekBar.progress = (colorWheel.paddingTop / density).roundToInt()
        thumbRadiusSeekBar.progress = (colorWheel.thumbRadius / density).roundToInt()
    }

    private fun onThumbSeekBarChange(progress: Int) {
        colorWheel.thumbRadius = (progress * density).roundToInt()
        thumbRadiusText.text = getString(R.string.thumb_radius, progress)
    }

    private fun onColorWheelPaddingSeekBarChange(progress: Int) {
        val padding = (progress * density).roundToInt()
        colorWheel.setPadding(padding, padding, padding, padding)
        colorWheelPadding.text = getString(R.string.color_wheel_padding, progress)
    }

    private fun randomizeColorWheelColor() {
        colorWheel.setRgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}
