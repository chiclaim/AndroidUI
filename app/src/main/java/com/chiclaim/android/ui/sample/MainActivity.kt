package com.chiclaim.android.ui.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // reset actionBar when fragment popup
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportActionBar?.title = getString(R.string.app_name)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }

    }

    fun goStampView(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, StampViewFragment())
        transaction.addToBackStack(StampViewFragment::class.java.simpleName)
        transaction.commitAllowingStateLoss()
    }
}