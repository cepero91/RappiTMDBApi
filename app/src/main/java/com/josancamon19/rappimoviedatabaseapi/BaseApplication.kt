package com.josancamon19.rappimoviedatabaseapi

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.josancamon19.rappimoviedatabaseapi.di.app.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber


class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector() = DaggerAppComponent.builder().application(this).build()

}