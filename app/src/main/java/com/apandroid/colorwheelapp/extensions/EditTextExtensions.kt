package com.apandroid.colorwheelapp.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

interface EmptyTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable) { }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
}

fun EditText.afterTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : EmptyTextWatcher {
        override fun afterTextChanged(s: Editable) {
            listener(s.toString())
        }
    })
}
