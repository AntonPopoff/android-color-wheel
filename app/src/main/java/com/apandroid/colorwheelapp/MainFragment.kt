package com.apandroid.colorwheelapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previewScreenButton.setOnClickListener { replaceFragment(PreviewFragment()) }
        testColorWheelScreenButton.setOnClickListener { replaceFragment(TestColorWheelFragment()) }
        testAlphaSeekBarScreen.setOnClickListener {  }
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}
