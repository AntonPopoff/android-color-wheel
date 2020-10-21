package com.apandroid.colorwheelapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.apandroid.colorwheelapp.colorwheeltest.TestColorWheelFragment
import com.apandroid.colorwheelapp.gradientseekbartest.TestGradientSeekBarFragment
import com.apandroid.colorwheelapp.preview.PreviewFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previewScreenButton.setOnClickListener { replaceFragment(PreviewFragment()) }
        testColorWheelScreenButton.setOnClickListener { replaceFragment(TestColorWheelFragment()) }
        testAlphaSeekBarScreenButton.setOnClickListener { replaceFragment(TestGradientSeekBarFragment()) }
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
