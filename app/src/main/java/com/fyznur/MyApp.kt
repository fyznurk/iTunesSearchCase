package com.fyznur

import android.app.Application
import android.content.Context

/**
 * Application class
 */
class MyApp : Application() {

    init {
        instance = this
    }

    companion object {
        var instance: MyApp? = null

        /**
         * use this i.e Sharedpref etc.
         */
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     */
    override fun onCreate() {
        super.onCreate()
    }
}