package com.apandroid.colorwheelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.apandroid.colorwheel.gradientseekbar.*
import com.apandroid.colorwheelapp.extensions.afterTextChanged
import com.apandroid.colorwheelapp.extensions.density
import com.apandroid.colorwheelapp.extensions.setOnProgressChangeListener
import com.apandroid.colorwheelapp.utils.randomArgb
import com.apandroid.colorwheelapp.utils.randomRgb
import kotlinx.android.synthetic.main.fragment_test_gradient_seek_bar.*
import java.util.*
import kotlin.math.roundToInt

class TestGradientSeekBarFragment : Fragment() {

    private val random by lazy { Random() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_gradient_seek_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barSizeEdit.afterTextChanged(this::onBarSizeChanged)
        barSizeEdit.setText((gradientSeekBar.barSize / density).roundToInt().toString())

        cornerRadiusEdit.afterTextChanged(this::onCornerRadiusChanged)
        cornerRadiusEdit.setText((gradientSeekBar.cornersRadius / density).roundToInt().toString())

        thumbRadiusEdit.afterTextChanged(this::onThumbRadiusChanged)
        thumbRadiusEdit.setText((gradientSeekBar.thumbRadius / density).roundToInt().toString())

        offsetSeekBar.setOnProgressChangeListener(this::onOffsetSeekBarChange)
        offsetSeekBar.progress = gradientSeekBar.currentColorAlpha

        colorCircleScaleSeekBar.setOnProgressChangeListener(this::onColorCircleScaleSeekBarChange)
        colorCircleScaleSeekBar.progress = (gradientSeekBar.thumbColorCircleScale * 100).toInt()

        alphaText.text = getString(R.string.offset_with_value, gradientSeekBar.offset)

        gradientSeekBar.colorListener = this::onGradientSeekBarChange

        orientationRadioGroup.check(R.id.verticalOrientationButton)
        orientationRadioGroup.setOnCheckedChangeListener(this::onOrientationChange)
        
        randomizeRgbButton.setOnClickListener { gradientSeekBar.setTransparentToColor(randomRgb(random), false) }
        randomizeArgbButton.setOnClickListener { gradientSeekBar.setTransparentToColor(randomArgb(random)) }
        randomizeStartColor.setOnClickListener { gradientSeekBar.startColor = randomRgb(random) }
        randomizeEndColor.setOnClickListener { gradientSeekBar.endColor = randomRgb(random) }
        randomizeThumbColorButton.setOnClickListener { gradientSeekBar.thumbColor = randomArgb(random) }
        randomizeThumbStrokeColorButton.setOnClickListener { gradientSeekBar.thumbStrokeColor = randomArgb(random) }
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

    private fun onColorCircleScaleSeekBarChange(progress: Int) {
        val scale = progress / 100f
        gradientSeekBar.thumbColorCircleScale = scale
        colorCircleScale.text = getString(R.string.color_circle_scale_with_value, scale)
    }

    private fun onOffsetSeekBarChange(progress: Int) {
        val offset = progress / 100f
        alphaText.text = getString(R.string.offset_with_value, offset)
        setOffsetSilently(gradientSeekBar, offset)
    }

    private fun setOffsetSilently(seekBar: GradientSeekBar, offset: Float) {
        val listener = seekBar.colorListener
        seekBar.colorListener = null
        seekBar.offset = offset
        seekBar.colorListener = listener
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
}
