package com.example.webonise.kotlinsamplepoc.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.webonise.kotlinsamplepoc.R
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.fragment_search_places.*
import java.util.ArrayList

/**
 * Created by webonise on 1/16/18.
 */
class SearchPlacesFragment : Fragment() {
    private val PLACES_AUTOCOMPLETE_REQUEST_CODE: Int = 1
    private val PERMISSION_REQUEST = 100

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_search_places, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener{checkPermission()}
        //checkPermission()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                startPlaceSelector()
            }
        } else {
            startPlaceSelector()
        }
    }

    private fun checkPermissions(permissions: Array<String>): Boolean {
        var result: Int
        val listPermissionsNeeded = ArrayList<String>()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(activity, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toTypedArray(), PERMISSION_REQUEST)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var result = true
        if (grantResults.isNotEmpty()) {
            for (permission in grantResults) {
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    result = false
                    break
                }
            }
        }
        if (grantResults.isNotEmpty() && result) {
            startPlaceSelector()
        } else {
            var permissionResult = true
            for (permission in permissions) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    permissionResult = false
                    break
                }
            }
            if (!permissionResult) {
                Toast.makeText(activity, getString(R.string.msg_permission_denied), Toast.LENGTH_SHORT).show()
            } else {
                val snackBar = Snackbar.make(getView()!!.rootView, getString(R.string.enable_permission),
                        Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.action_enable, View.OnClickListener { launchApplicationSetting() })
                snackBar.show()
            }
        }
    }

    private fun startPlaceSelector() {
        val intentBuilder = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(activity)
        startActivityForResult(intentBuilder, PLACES_AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        handleActivityResult(requestCode, resultCode, data)
    }

    private fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACES_AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = PlaceAutocomplete.getPlace(activity, data)
                    val intent = Intent(view?.context, PlaceDetailsActivity::class.java)
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    val latLng = place.latLng

                    //val location: Location = Location(latLng.latitude.toString() + "," + latLng.longitude)

                    //intent.putExtra("lat", latLng.latitude.toString() + "," + latLng.longitude)
                    intent.putExtra("latLng",latLng)
                    view?.context?.startActivity(intent)
                } PlaceAutocomplete.RESULT_ERROR -> {
                    val status = PlaceAutocomplete.getStatus(activity, data)
                } Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            /*if (resultCode == Activity.RESULT_OK) {

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(activity, data)
                // TODO: Handle the error.
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }*/
        }
    }

    private fun launchApplicationSetting() {
        val appIntent = Intent()
        appIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        appIntent.addCategory(Intent.CATEGORY_DEFAULT)
        appIntent.data = Uri.parse("package:" + activity.packageName)
        appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        appIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        activity.startActivity(appIntent)
    }



}