package com.example.boadcastintent

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle

class MyActivity : Activity() {
    /**
     * Listing 5-12: Registering and unregistering a Broadcast Receiver in code
     */
    private val filter: IntentFilter = IntentFilter(LifeformDetectedReceiver.NEW_LIFEFORM)
    private val receiver = LifeformDetectedReceiver()
    @Synchronized
    public override fun onResume() {
        super.onResume()
        // Register the broadcast receiver.
        registerReceiver(receiver, filter)
    }

    @Synchronized
    public override fun onPause() { // Unregister the receiver
        unregisterReceiver(receiver)
        super.onPause()
    }

    //
    private fun detectedLifeform(
        detectedLifeform: String,
        currentLongitude: Double,
        currentLatitude: Double
    ) {
        val intent = Intent(LifeformDetectedReceiver.NEW_LIFEFORM)
        intent.putExtra(
            LifeformDetectedReceiver.EXTRA_LIFEFORM_NAME,
            detectedLifeform
        )
        intent.putExtra(
            LifeformDetectedReceiver.EXTRA_LONGITUDE,
            currentLongitude
        )
        intent.putExtra(
            LifeformDetectedReceiver.EXTRA_LATITUDE,
            currentLatitude
        )
        sendBroadcast(intent)
    }

    //
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(receiver, filter)
        detectedLifeform("facehugger", 12.000, -56.000)
    }
}