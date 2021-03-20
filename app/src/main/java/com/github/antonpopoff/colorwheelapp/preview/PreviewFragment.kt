package com.github.antonpopoff.colorwheelapp.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.antonpopoff.colorwheelapp.R
import com.github.antonpopoff.colorwheelapp.databinding.FragmentPreviewBinding
import com.github.antonpopoff.colorwheelapp.extensions.android.getViewModel

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
