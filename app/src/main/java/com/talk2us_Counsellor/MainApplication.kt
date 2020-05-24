package com.talk2us_Counsellor

import android.app.Application
import androidx.multidex.MultiDex

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        instance = this
    }
}