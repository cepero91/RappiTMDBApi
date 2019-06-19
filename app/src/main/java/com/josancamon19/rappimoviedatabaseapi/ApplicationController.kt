package com.josancamon19.rappimoviedatabaseapi

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import timber.log.Timber


class ApplicationController : Application() {


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


}