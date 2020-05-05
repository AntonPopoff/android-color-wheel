package com.apandroid.colorwheelapp.preview

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apandroid.colorwheelapp.R
import com.apandroid.colorwheelapp.databinding.FragmentPreviewBinding
import com.apandroid.colorwheelapp.extensions.density

class PreviewFragment : Fragment(R.layout.fragment_preview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataBinding(view)
    }

    private fun setupDataBinding(view: View) {
        FragmentPreviewBinding.bind(view).let {
            it.colorIndicator.background = GradientDrawable().apply { cornerRadius = density * 16 }
            it.viewModel = ViewModelProvider(this).get(PreviewViewModel::class.java)
        }
    }
}
