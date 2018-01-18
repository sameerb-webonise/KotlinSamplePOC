package com.example.webonise.kotlinsamplepoc

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by webonise on 1/18/18.
 */
class KotlinSampleApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: KotlinSampleApplication? = null
        fun getAppContext(): Context {
            return instance!!.applicationContext
        }
    }

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