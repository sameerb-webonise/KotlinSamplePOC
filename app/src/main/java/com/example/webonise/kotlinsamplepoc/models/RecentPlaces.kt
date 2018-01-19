package com.example.webonise.kotlinsamplepoc.models

import io.realm.RealmObject

open class RecentPlaces : RealmObject() {
    var name: String? = null
    var latlng: String? = null
}