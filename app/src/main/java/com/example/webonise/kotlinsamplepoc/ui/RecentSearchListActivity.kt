package com.example.webonise.kotlinsamplepoc.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.webonise.kotlinsamplepoc.R
import com.example.webonise.kotlinsamplepoc.models.RecentPlaceListModel
import com.example.webonise.kotlinsamplepoc.presenter.RecentSearchListPresenter
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_recent_place_list.*

/**
 * Created by webonise on 1/18/18.
 */
class RecentSearchListActivity : BaseActivity() {
    var presenter: RecentSearchListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_place_list)

        init()
    }

    private fun init() {
        presenter = RecentSearchListPresenter()
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        //recentPlaces.setLayoutManager(linearLayoutManager)
        recentPlaces.layoutManager = linearLayoutManager
        presenter?.setView(this)
    }

    fun updateUI(){
        //recentPlaces.setAdapter(presenter?.getAdaptor())
        recentPlaces.adapter = presenter?.getAdaptor()
        presenter?.getAdaptor()!!.notifyDataSetChanged()
    }

    fun showPlaceDetailsSreen(recentPlace: RecentPlaceListModel?) {
        var intent = Intent(this, PlaceDetailsActivity::class.java)
        val latLngStr = recentPlace?.latLng?.split(",".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        var latlng = LatLng(latLngStr!![0].toDouble(), latLngStr[1].toDouble())
        intent.putExtra("latLng", latlng)
        startActivity(intent)
    }
}