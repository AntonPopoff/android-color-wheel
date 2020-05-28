package com.apandroid.colorwheelapp.colorwheeltest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apandroid.colorwheelapp.R
import com.apandroid.colorwheelapp.databinding.FragmentTestColorWheelBinding
import com.apandroid.colorwheelapp.utils.pixelsToDp
import java.util.*

class TestColorWheelFragment : Fragment(R.layout.fragment_test_color_wheel) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentTestColorWheelBinding.bind(view).also {
            it.random = Random()
            it.viewModel = ViewModelProvider(this).get(TestColorWheelViewModel::class.java).apply {
                colorCircleScaleObservable.set(it.colorWheel.thumbColorCircleScale)
                it.thumbRadiusSeekBar.progress = pixelsToDp(requireContext(), it.colorWheel.thumbRadius)
                it.colorWheelPaddingSeekBar.progress = 16
            }
        }
    }
}
