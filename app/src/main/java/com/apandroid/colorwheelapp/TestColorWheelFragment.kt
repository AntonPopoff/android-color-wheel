package com.apandroid.colorwheelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.extensions.density
import com.apandroid.colorwheelapp.extensions.setOnProgressChangeListener
import com.apandroid.colorwheelapp.utils.randomArgb
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

        randomizeColorButton.setOnClickListener { colorWheel.setRgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)) }
        randomizeThumbColorButton.setOnClickListener { colorWheel.thumbColor = randomArgb(random) }
        randomizeStrokeColorButton.setOnClickListener { colorWheel.thumbStrokeColor = randomArgb(random) }

        colorWheelPaddingSeekBar.setOnProgressChangeListener(this::onColorWheelPaddingSeekBarChange)
        thumbRadiusSeekBar.setOnProgressChangeListener(this::onThumbSeekBarChange)
        colorCircleScaleSeekBar.setOnProgressChangeListener(this::onColorCircleScaleSeekBarChange)

        colorWheelPaddingSeekBar.progress = (colorWheel.paddingTop / density).roundToInt()
        thumbRadiusSeekBar.progress = (colorWheel.thumbRadius / density).roundToInt()
        colorCircleScaleSeekBar.progress = (colorWheel.thumbColorCircleScale * 100).toInt()
    }

    private fun onThumbSeekBarChange(progress: Int) {
        colorWheel.thumbRadius = (progress * density).roundToInt()
        thumbRadiusText.text = getString(R.string.thumb_radius_with_value, progress)
    }

    private fun onColorWheelPaddingSeekBarChange(progress: Int) {
        val padding = (progress * density).roundToInt()
        colorWheel.setPadding(padding, padding, padding, padding)
        colorWheelPadding.text = getString(R.string.color_wheel_padding_with_value, progress)
    }

    private fun onColorCircleScaleSeekBarChange(progress: Int) {
        val scale = progress / 100f
        colorWheel.thumbColorCircleScale = scale
        colorCircleScale.text = getString(R.string.color_circle_scale_with_value, scale)
    }
}
