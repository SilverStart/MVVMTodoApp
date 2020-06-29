package com.silverstar.mvvmtodoapp

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.start()
        }
    }
}