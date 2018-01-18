package com.example.webonise.kotlinsamplepoc.interfaces

import com.example.webonise.kotlinsamplepoc.response.GetPlacesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by webonise on 17/1/18.
 */
interface IGetPhotosApi {
    @GET("/maps/api/place/nearbysearch/json")
    fun getPlaceDetails(@Query("location") location: String?,
                                 @Query("radius") radius: Int,
                                 @Query("key") key: String): Call<GetPlacesResponse>
}