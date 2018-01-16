package com.example.webonise.kotlinsamplepoc.UI

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by webonise on 1/16/18.
 */
open class BaseActivity : AppCompatActivity() {

    fun addFragmentSupport(fragment: Fragment, addToBackStack: Boolean, containerId: Int, tag: String, showTransition: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (showTransition) {
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val currentFragment = supportFragmentManager.findFragmentById(containerId)
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        transaction.add(containerId, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }

        transaction.commitAllowingStateLoss()
    }

    fun getVisibleFragment(): Fragment? {
        val fragmentManager = this.supportFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible)
                    return fragment
            }
        }
        return null
    }

    override fun onBackPressed() {
        this.finish()
    }
}