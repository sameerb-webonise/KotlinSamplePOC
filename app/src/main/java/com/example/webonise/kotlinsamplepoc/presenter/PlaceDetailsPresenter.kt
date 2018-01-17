package com.example.webonise.kotlinsamplepoc.presenter

import android.app.Activity
import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.webonise.kotlinsamplepoc.R
import com.example.webonise.kotlinsamplepoc.adapter.PhotoViewAdapter
import com.example.webonise.kotlinsamplepoc.interfaces.IGetPhotosApi
import com.example.webonise.kotlinsamplepoc.interfaces.IPlaceDetails
import com.example.webonise.kotlinsamplepoc.response.GetPlacesResponse
import com.example.webonise.kotlinsamplepoc.ui.PlaceDetailsActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by webonise on 17/1/18.
 */
class PlaceDetailsPresenter : IPlaceDetails {
    private val BASE_URL = "https://maps.googleapis.com"
    private var mView: Activity? = null
    private var mLatLng: String? = null
    private var mAdaptor: PhotoViewAdapter? = null
    private var mPhotoUris: ArrayList<String> = ArrayList()

    override fun setViewAndData(mActivity: PlaceDetailsActivity, latLng: String?) {
        mView = mActivity
        mLatLng = latLng
        mAdaptor = PhotoViewAdapter(mView as PlaceDetailsActivity, this, mPhotoUris)
    }

    fun getAdaptor(): PhotoViewAdapter? {
        return mAdaptor
    }

    override fun loadPlacePhotos() {
        //lambda expression
        val okHttp3ClientBuilder = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()

            chain.proceed(request)
        }

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp3ClientBuilder.build())
                .build()

        val getPhotosApi = retrofit.create<IGetPhotosApi>(IGetPhotosApi::class.java)
        val call = getPhotosApi.getPlaceDetails(mLatLng, 500, "AIzaSyBv4xXX_J_xMBykdkqYsujToBPFzltc1jM")
        call.enqueue(object : Callback<GetPlacesResponse> {
            override fun onResponse(call: Call<GetPlacesResponse>, response: Response<GetPlacesResponse>?) {
                if (response != null) {
                    Log.d("PlaceDetailsPresenter", response.body().toString())
                    val results = response.body().results
                    //if (results != null) {
                        mPhotoUris.clear()
                        for (result in results) {
                            val photos = result.photos
                            if (photos != null) {
                                for (photo in photos) {
                                    val photoReference = photo.photo_reference
                                    if (!TextUtils.isEmpty(photoReference)) {
                                        addItemToPhotoList("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference +
                                                "&key=" +"AIzaSyBv4xXX_J_xMBykdkqYsujToBPFzltc1jM")
                                    }
                                }
                            }
                        }
                    //}
                }
            }

            override fun onFailure(call: Call<GetPlacesResponse>, t: Throwable) {
                Log.d("PlaceDetailsPresenter", t.message)
            }
        })
    }

    private fun addItemToPhotoList(photoUri: String) {
        mPhotoUris.add(photoUri)
        //saveImageToFile(photoUri);
        mView!!.runOnUiThread(Runnable { mAdaptor!!.notifyDataSetChanged() })
    }

    fun saveImageToFile(photoUri: String) {
        Thread(Runnable {
            var theBitmap: Bitmap
            try {
                theBitmap = Glide.with(mView).load(photoUri).asBitmap().into(-1, -1).get()
                val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
                val filename = System.currentTimeMillis().toString() + ".png"
                var file = File(extStorageDirectory, filename)
                if (file.exists()) {
                    file.delete()
                    file = File(extStorageDirectory, filename)
                }
                val outStream = FileOutputStream(file)
                theBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                outStream.flush()
                outStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
        Toast.makeText(mView, mView?.getString(R.string.photo_downloaded), Toast.LENGTH_SHORT).show()
    }

    override fun handleItemClick(position: Int) {
        //saveImageToFile(mPhotoUris.get(position))

        (mView as PlaceDetailsActivity).goToNextActivity(mPhotoUris.get(position))
    }

    override fun onLocationChanged(p0: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}