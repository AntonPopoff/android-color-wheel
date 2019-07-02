package com.apandroid.colorwheelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.extensions.density
import com.apandroid.colorwheelapp.extensions.afterTextChanged
import kotlinx.android.synthetic.main.fragment_test_alpha_seek_bar.*
import kotlin.math.roundToInt

class TestAlphaSeekBarColor : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test_alpha_seek_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barSizeEdit.afterTextChanged(this::onBarSizeChanged)
        barSizeEdit.setText((libAlphaSeekBar.barSize / density).roundToInt().toString())

        cornerRadiusEdit.afterTextChanged(this::onCornerRadiusChanged)
        cornerRadiusEdit.setText((libAlphaSeekBar.cornersRadius / density).roundToInt().toString())

        thumbRadiusEdit.afterTextChanged(this::onThumbRadiusChanged)
        thumbRadiusEdit.setText((libAlphaSeekBar.thumbRadius / density).roundToInt().toString())

        alphaSeekBar.progress = libAlphaSeekBar.alphaValue
        alphaText.text = getString(R.string.alpha_template, libAlphaSeekBar.alphaValue)

        orientationRadioGroup.check(R.id.verticalOrientationButton)
        
        randomizeRgbButton.setOnClickListener {  }
        randomizeArgbButton.setOnClickListener {  }
    }

    private fun onBarSizeChanged(s: String) {
        libAlphaSeekBar.barSize = ((s.toIntOrNull() ?: 0) * density).roundToInt()
    }

    private fun onCornerRadiusChanged(s: String) {
        libAlphaSeekBar.cornersRadius = (s.toIntOrNull() ?: 0) * density
    }

    private fun onThumbRadiusChanged(s: String) {
        libAlphaSeekBar.thumbRadius = ((s.toIntOrNull() ?: 0) * density).roundToInt()
    }
}
