package com.apandroid.colorwheelapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.extensions.density
import com.apandroid.colorwheelapp.extensions.afterTextChanged
import com.apandroid.colorwheelapp.extensions.setOnProgressChangeListener
import com.colorwheelapp.colorwheel.gradientseekbar.*
import kotlinx.android.synthetic.main.fragment_test_alpha_seek_bar.*
import java.util.*
import kotlin.math.roundToInt

class TestGradientSeekBarFragment : Fragment() {

    private val random by lazy { Random() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_alpha_seek_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barSizeEdit.afterTextChanged(this::onBarSizeChanged)
        barSizeEdit.setText((gradientSeekBar.barSize / density).roundToInt().toString())

        cornerRadiusEdit.afterTextChanged(this::onCornerRadiusChanged)
        cornerRadiusEdit.setText((gradientSeekBar.cornersRadius / density).roundToInt().toString())

        thumbRadiusEdit.afterTextChanged(this::onThumbRadiusChanged)
        thumbRadiusEdit.setText((gradientSeekBar.thumbRadius / density).roundToInt().toString())

        offsetSeekBar.progress = gradientSeekBar.currentAlpha
        offsetSeekBar.setOnProgressChangeListener(this::onOffsetSeekBarChange)
        alphaText.text = getString(R.string.offset_with_value, gradientSeekBar.offset)

        gradientSeekBar.listener = this::onGradientSeekBarChange

        orientationRadioGroup.check(R.id.verticalOrientationButton)
        orientationRadioGroup.setOnCheckedChangeListener(this::onOrientationChange)
        
        randomizeRgbButton.setOnClickListener { randomizeRgb() }
        randomizeArgbButton.setOnClickListener { randomizeArgb() }
    }

    private fun onBarSizeChanged(s: String) {
        gradientSeekBar.barSize = ((s.toIntOrNull() ?: 0) * density).roundToInt()
    }

    private fun onCornerRadiusChanged(s: String) {
        gradientSeekBar.cornersRadius = (s.toIntOrNull() ?: 0) * density
    }

    private fun onThumbRadiusChanged(s: String) {
        gradientSeekBar.thumbRadius = ((s.toIntOrNull() ?: 0) * density).roundToInt()
    }

    private fun onOffsetSeekBarChange(progress: Int) {
        val offset = progress / 100f
        alphaText.text = getString(R.string.offset_with_value, offset)
        setOffsetSilently(gradientSeekBar, offset)
    }

    private fun setOffsetSilently(seekBar: GradientSeekBar, offset: Float) {
        val listener = seekBar.listener
        seekBar.listener = null
        seekBar.offset = offset
        seekBar.listener = listener
    }

    private fun onGradientSeekBarChange(offset: Float, color: Int) {
        offsetSeekBar.progress = (offset * 100f).roundToInt()
        alphaText.text = getString(R.string.offset_with_value, offset)
    }

    private fun onOrientationChange(group: RadioGroup, checkedId: Int) {
        if (checkedId == R.id.verticalOrientationButton) {
            gradientSeekBar.orientation = GradientSeekBar.Orientation.VERTICAL
        } else if (checkedId == R.id.horizontalOrientationButton) {
            gradientSeekBar.orientation = GradientSeekBar.Orientation.HORIZONTAL
        }
    }

    private fun randomizeRgb() {
        gradientSeekBar.setAlphaRgb(Color.rgb(
            random.nextInt(255),
            random.nextInt(255),
            random.nextInt(255))
        )
    }

    private fun randomizeArgb() {
        gradientSeekBar.setAlphaArgb(Color.argb(
            random.nextInt(255),
            random.nextInt(255),
            random.nextInt(255),
            random.nextInt(255))
        )
    }
}
