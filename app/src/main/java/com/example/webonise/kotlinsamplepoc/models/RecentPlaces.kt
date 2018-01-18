package com.example.webonise.kotlinsamplepoc.models

import io.realm.RealmObject

/**
 * Created by webonise on 1/18/18.
 */
open class RecentPlaces : RealmObject() {
    var name: String? = null
    var latlng: String? = null
}