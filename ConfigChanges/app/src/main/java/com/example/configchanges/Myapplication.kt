package com.example.configchanges

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class Myapplication: AppCompatActivity() {

    private var singleton: Myapplication? = null

    private val tag: String = "com.example.configuration_changes.MyApplication"

//    fun getInstance(): Myapplication? {
//        return singleton
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        singleton = this
        Log.d(tag,"onCreate")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(tag, "onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(tag, "onTrimMemory")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(tag,"onConfigurationChanged")
    }

}