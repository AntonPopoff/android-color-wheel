package com.github.antonpopoff.colorwheelapp.extensions

import android.text.Editable
import android.widget.EditText
import com.github.antonpopoff.colorwheelapp.utils.listeners.EmptyTextWatcher

fun EditText.afterTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : EmptyTextWatcher {
        override fun afterTextChanged(s: Editable) {
            listener(s.toString())
        }
    })
}
