package com.example.webonise.kotlinsamplepoc.response

/**
 * Created by webonise on 17/1/18.
 */
 data class GetPlacesResponse(val htmlAttributions:List<Any>,val results:List<Result>, val status:String) {

    /**
     * make inner classes as data classes
     */
    inner class Result {
        var icon: String? = null
        var id: String? = null
        var name: String? = null
        var photos: List<Photo>? = null
        var placeId: String? = null
        var rating: Float = 0.toFloat()
        var reference: String? = null
        var scope: String? = null
        var types: List<String>? = null
        var vicinity: String? = null
    }

    inner class Photo {

        var height: Int = 0
        var html_attributions: List<String>? = null
        var photo_reference: String? = null
        var width: Int = 0
    }
}