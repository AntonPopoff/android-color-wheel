package com.github.antonpopoff.colorwheelapp.extensions.android

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> Fragment.getViewModel() =
    ViewModelProvider(this).get(T::class.java)