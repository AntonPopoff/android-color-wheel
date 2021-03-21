package com.github.antonpopoff.colorwheelapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.antonpopoff.colorwheelapp.colorwheeltest.TestColorWheelFragment
import com.github.antonpopoff.colorwheelapp.gradientseekbartest.TestGradientSeekBarFragment
import com.github.antonpopoff.colorwheelapp.preview.PreviewFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        previewScreenButton.setOnClickListener {
            replaceFragment(PreviewFragment())
        }

        testColorWheelScreenButton.setOnClickListener {
            replaceFragment(TestColorWheelFragment())
        }

        testAlphaSeekBarScreenButton.setOnClickListener {
            replaceFragment(TestGradientSeekBarFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
