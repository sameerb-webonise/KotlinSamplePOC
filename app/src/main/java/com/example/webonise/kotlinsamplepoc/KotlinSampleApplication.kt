package com.example.webonise.kotlinsamplepoc

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by webonise on 1/18/18.
 */
class KotlinSampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //Realm initialisation
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}