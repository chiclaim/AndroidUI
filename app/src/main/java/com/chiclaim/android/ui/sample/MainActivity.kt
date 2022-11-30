package com.chiclaim.android.ui.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goStampView(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, StampViewFragment())
        transaction.addToBackStack("StampViewFragment")
        transaction.commitAllowingStateLoss()
    }
}