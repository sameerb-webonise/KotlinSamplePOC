package com.example.webonise.kotlinsamplepoc.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.webonise.kotlinsamplepoc.R
import com.example.webonise.kotlinsamplepoc.presenter.PlaceDetailsPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_place_details.*

class PlaceDetailsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {


    private var mLatLng: LatLng? = null
    private var mPresenter: PlaceDetailsPresenter? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)

        mLatLng = intent.getParcelableExtra("latLng")
        init()
    }

    private fun init() {
        mPresenter = PlaceDetailsPresenter()


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        photos.setLayoutManager(gridLayoutManager)

        mPresenter!!.setViewAndData(this, mLatLng?.latitude.toString() + "," + mLatLng?.longitude)
        photos.setAdapter(mPresenter?.getAdaptor())
        mPresenter!!.loadPlacePhotos()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.setOnMyLocationButtonClickListener(this)

        val currentLocation: LatLng? = mLatLng

            mMap?.addMarker(MarkerOptions().position(currentLocation!!).title("My Location"))
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,15F))

    }

    override fun onMyLocationButtonClick(): Boolean {
       return false
    }

    fun goToNextActivity(photoUrl: String) {
        val intent = Intent(this, PhotoViewActivity::class.java)

        val url = photoUrl

        intent.putExtra("photoUrl",url)
        startActivity(intent)
    }


}