package com.example.bright.Controller

import android.app.Application
import com.example.bright.Utilities.SharedPref

class App : Application() {

    companion object{
        lateinit var prefs: SharedPref
    }

    override fun onCreate() {
        prefs = SharedPref(applicationContext)
        super.onCreate()
    }
}