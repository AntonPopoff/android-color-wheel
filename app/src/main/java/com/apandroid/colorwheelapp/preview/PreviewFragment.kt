package com.apandroid.colorwheelapp.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.apandroid.colorwheelapp.R
import com.apandroid.colorwheelapp.databinding.FragmentPreviewBinding
import com.apandroid.colorwheelapp.extensions.getViewModel

class PreviewFragment : Fragment(R.layout.fragment_preview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding(view)
    }

    private fun setupDataBinding(view: View) {
        FragmentPreviewBinding.bind(view).let {
            it.colorIndicator.setBackgroundResource(R.drawable.color_indicator)
            it.viewModel = getViewModel()
        }
    }
}
