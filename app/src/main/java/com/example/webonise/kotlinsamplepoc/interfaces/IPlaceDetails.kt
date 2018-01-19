package com.example.webonise.kotlinsamplepoc.interfaces

import android.location.LocationListener
import com.example.webonise.kotlinsamplepoc.ui.PlaceDetailsActivity

interface IPlaceDetails : LocationListener {
    fun setViewAndData(mActivity: PlaceDetailsActivity, latLng: String?)

    fun loadPlacePhotos()

    fun handleItemClick(position: Int)
}