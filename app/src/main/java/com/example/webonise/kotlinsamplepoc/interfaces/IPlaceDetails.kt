package com.example.webonise.kotlinsamplepoc.interfaces

import android.location.LocationListener
import com.example.webonise.kotlinsamplepoc.ui.PlaceDetailsActivity

/**
 * Created by webonise on 17/1/18.
 */
interface IPlaceDetails : LocationListener {
    abstract fun setViewAndData(mActivity: PlaceDetailsActivity, latLng: String?)

    abstract fun loadPlacePhotos()

    abstract fun handleItemClick(position: Int)
}