package com.chiclaim.android.ui.sample

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 *
 * Created by chiclaim@google.com
 */
open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 使 optionMenu 生效
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 点击返回键关闭当前 fragment
            android.R.id.home -> {
                val fm: FragmentManager = requireActivity().supportFragmentManager
                fm.popBackStack(javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}