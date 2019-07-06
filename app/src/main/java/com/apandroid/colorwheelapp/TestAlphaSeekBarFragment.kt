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
import com.colorwheelapp.colorwheel.alphaseekbar.AlphaSeekBar
import com.colorwheelapp.colorwheel.alphaseekbar.setAlphaSilently
import kotlinx.android.synthetic.main.fragment_test_alpha_seek_bar.*
import java.util.*
import kotlin.math.roundToInt

class TestAlphaSeekBarFragment : Fragment() {

    private val random by lazy { Random() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_alpha_seek_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barSizeEdit.afterTextChanged(this::onBarSizeChanged)
        barSizeEdit.setText((alphaSeekBar.barSize / density).roundToInt().toString())

        cornerRadiusEdit.afterTextChanged(this::onCornerRadiusChanged)
        cornerRadiusEdit.setText((alphaSeekBar.cornersRadius / density).roundToInt().toString())

        thumbRadiusEdit.afterTextChanged(this::onThumbRadiusChanged)
        thumbRadiusEdit.setText((alphaSeekBar.thumbRadius / density).roundToInt().toString())

        alphaSeekBarController.progress = alphaSeekBar.alphaValue
        alphaSeekBarController.setOnProgressChangeListener(this::onAlphaSeekBarControllerChanged)
        alphaText.text = getString(R.string.alpha_with_value, alphaSeekBar.alphaValue)

        alphaSeekBar.alphaChangeListener = this::onAlphaSeekBarChange

        orientationRadioGroup.check(R.id.verticalOrientationButton)
        orientationRadioGroup.setOnCheckedChangeListener(this::onOrientationChange)
        
        randomizeRgbButton.setOnClickListener { randomizeRgb() }
        randomizeArgbButton.setOnClickListener { randomizeArgb() }
    }

    private fun onBarSizeChanged(s: String) {
        alphaSeekBar.barSize = ((s.toIntOrNull() ?: 0) * density).roundToInt()
    }

    private fun onCornerRadiusChanged(s: String) {
        alphaSeekBar.cornersRadius = (s.toIntOrNull() ?: 0) * density
    }

    private fun onThumbRadiusChanged(s: String) {
        alphaSeekBar.thumbRadius = ((s.toIntOrNull() ?: 0) * density).roundToInt()
    }

    private fun onAlphaSeekBarControllerChanged(progress: Int) {
        alphaText.text = getString(R.string.alpha_with_value, alphaSeekBar.alphaValue)
        alphaSeekBar.setAlphaSilently(progress)
    }

    private fun onAlphaSeekBarChange(alpha: Int) {
        alphaText.text = getString(R.string.alpha_with_value, alphaSeekBar.alphaValue)
        alphaSeekBarController.progress = alpha
    }

    private fun onOrientationChange(group: RadioGroup, checkedId: Int) {
        if (checkedId == R.id.verticalOrientationButton) {
            alphaSeekBar.orientation = AlphaSeekBar.Orientation.VERTICAL
        } else if (checkedId == R.id.horizontalOrientationButton) {
            alphaSeekBar.orientation = AlphaSeekBar.Orientation.HORIZONTAL
        }
    }

    private fun randomizeRgb() {
        alphaSeekBar.rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255))
    }

    private fun randomizeArgb() {
        alphaSeekBar.argb = Color.argb(random.nextInt(255), random.nextInt(255), random.nextInt(255), random.nextInt(255))
    }
}
