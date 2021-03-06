package com.apandroid.colorwheelapp.colorwheeltest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.R
import com.apandroid.colorwheelapp.databinding.FragmentTestColorWheelBinding
import com.apandroid.colorwheelapp.extensions.getViewModel
import com.apandroid.colorwheelapp.utils.pixelsToDp
import java.util.*

class TestColorWheelFragment : Fragment(R.layout.fragment_test_color_wheel) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding(view)
    }

    private fun setupDataBinding(view: View) {
        FragmentTestColorWheelBinding.bind(view).apply {
            viewModel = createViewModel(this)
            random = Random()
        }
    }

    private fun createViewModel(binding: FragmentTestColorWheelBinding): TestColorWheelViewModel {
        return getViewModel<TestColorWheelViewModel>().apply {
            binding.apply {
                colorWheelPaddingSeekBar.progress = 16
                thumbRadiusSeekBar.progress = pixelsToDp(requireContext(), colorWheel.thumbRadius)
                colorCircleScaleObservable.set(colorWheel.thumbColorCircleScale)
            }
        }
    }
}
