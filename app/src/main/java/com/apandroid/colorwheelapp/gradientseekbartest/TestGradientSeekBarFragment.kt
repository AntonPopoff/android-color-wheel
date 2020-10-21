package com.apandroid.colorwheelapp.gradientseekbartest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.R
import com.apandroid.colorwheelapp.databinding.FragmentTestGradientSeekBarBinding
import java.util.*
import kotlin.math.roundToInt

class TestGradientSeekBarFragment : Fragment(R.layout.fragment_test_gradient_seek_bar) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding(view)
    }

    private fun setupDataBinding(view: View) {
        FragmentTestGradientSeekBarBinding.bind(view).apply {
            viewModel = createViewModel(this)
            random = Random()
        }
    }

    private fun createViewModel(binding: FragmentTestGradientSeekBarBinding): TestGradientSeekBarViewModel {
        return TestGradientSeekBarViewModel().apply {
            colorCircleScaleObservable.set(binding.gradientSeekBar.thumbColorCircleScale)
            cornerRadiusObservable.set(binding.gradientSeekBar.cornersRadius.roundToInt())
            thumbRadiusObservable.set(binding.gradientSeekBar.thumbRadius)
            barSizeObservable.set(binding.gradientSeekBar.barSize)
            gradientSeekBarOrientation.set(binding.gradientSeekBar.orientation)
        }
    }
}
