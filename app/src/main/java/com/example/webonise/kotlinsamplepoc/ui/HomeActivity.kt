package com.example.webonise.kotlinsamplepoc.ui

import android.os.Bundle
import com.example.webonise.kotlinsamplepoc.R

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        addFragmentSupport(SearchPlacesFragment(), true, R.id.container, SearchPlacesFragment::class.java.simpleName, false)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val visibleFragment = getVisibleFragment()
        if (visibleFragment != null && visibleFragment is SearchPlacesFragment) {
            visibleFragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}