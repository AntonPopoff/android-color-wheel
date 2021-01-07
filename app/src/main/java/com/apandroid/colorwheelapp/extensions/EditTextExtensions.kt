package com.apandroid.colorwheelapp.extensions

import android.text.Editable
import android.widget.EditText
import com.apandroid.colorwheelapp.utils.listeners.EmptyTextWatcher

fun EditText.afterTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : EmptyTextWatcher {
        override fun afterTextChanged(s: Editable) {
            listener(s.toString())
        }
    })
}
