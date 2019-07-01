package com.apandroid.colorwheelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addMainFragmentIfNotAttached()
    }

    private fun addMainFragmentIfNotAttached() {
        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment())
                .commit()
        }
    }
}
