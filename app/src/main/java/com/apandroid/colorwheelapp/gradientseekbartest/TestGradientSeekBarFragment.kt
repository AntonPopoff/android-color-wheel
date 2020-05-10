package com.apandroid.colorwheelapp.gradientseekbartest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.R
import com.apandroid.colorwheelapp.databinding.FragmentTestGradientSeekBarBinding
import java.util.*

class TestGradientSeekBarFragment : Fragment(R.layout.fragment_test_gradient_seek_bar) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding(view)
    }

    private fun setupDataBinding(view: View) {
        FragmentTestGradientSeekBarBinding.bind(view).let {
            it.random = Random()
            it.viewModel = TestGradientSeekBarViewModel().apply {
                colorCircleScaleObservable.set(it.gradientSeekBar.thumbColorCircleScale)
                colorCircleScaleObservable.set(it.gradientSeekBar.thumbColorCircleScale)
                cornerRadiusObservable.set(it.gradientSeekBar.cornersRadius)
                barSizeObservable.set(it.gradientSeekBar.barSize)
                gradientSeekBarOrientation.set(it.gradientSeekBar.orientation)
            }
        }
    }

}
