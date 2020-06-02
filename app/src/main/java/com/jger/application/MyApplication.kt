package com.jger.application

import android.app.Application
import com.facebook.stetho.Stetho

class MyApplication : Application() {
    private var screenHeight = 0
    private var applicationInstance: MyApplication?=null

    companion object{
        private var applicationInstance: MyApplication?=null
        @Synchronized
        fun getInstance(): MyApplication? {
            return Companion.applicationInstance
        }
    }
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        MyApplication.applicationInstance=this
    }

    fun getScreenHeight(): Int {
        return screenHeight
    }

    fun setScreenHeight(screenHeight: Int) {
        this.screenHeight = screenHeight
    }

}