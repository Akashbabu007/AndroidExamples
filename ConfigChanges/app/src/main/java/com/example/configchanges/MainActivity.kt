package com.example.configchanges

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val tag:String = "com.example.configuration_changes.MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()
        Log.d("MyStateChangeActivity","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MyStateChangeActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyStateChangeActivity","onDestroy")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(tag,"onConfigurationChanged")

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(tag, "Orientation Landscape")
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // [ ... React to different orientation ... ]\
            Log.d(tag, "Orientation Landscape")
        }
        if(newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            Log.d(tag, "Keyboard hidden no")
        }
    }
}
