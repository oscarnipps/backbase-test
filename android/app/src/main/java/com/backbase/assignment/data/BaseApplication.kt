package com.backbase.assignment.data

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //since app is not required in release
        Timber.plant(Timber.DebugTree())
    }
}