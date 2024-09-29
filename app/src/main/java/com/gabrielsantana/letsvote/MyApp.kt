package com.gabrielsantana.letsvote

import android.app.Application

class MyApp : Application() {

    //create a singleton of the application
    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}