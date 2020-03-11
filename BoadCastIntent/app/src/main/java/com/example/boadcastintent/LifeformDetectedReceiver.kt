package com.example.boadcastintent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log


/**
 * Listing 5-11: Implementing a Broadcast Receiver
 */
class LifeformDetectedReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) { // Get the lifeform details from the intent.
        Log.d(EXTRA_LIFEFORM_NAME, "onReceive")
        val data = intent.data
        val type = intent.getStringExtra(EXTRA_LIFEFORM_NAME)
        val lat = intent.getDoubleExtra(EXTRA_LATITUDE, 0.0)
        val lng = intent.getDoubleExtra(EXTRA_LONGITUDE, 0.0)
        val loc = Location("gps")
        loc.latitude = lat
        loc.longitude = lng
        if (type == "facehugger") {
            val startIntent = Intent(ACTION_BURN, data)
            startIntent.putExtra(EXTRA_LATITUDE, lat)
            startIntent.putExtra(EXTRA_LONGITUDE, lng)
            context.startService(startIntent)
        }
    }

    companion object {
        const val EXTRA_LIFEFORM_NAME = "EXTRA_LIFEFORM_NAME"
        const val EXTRA_LATITUDE = "EXTRA_LATITUDE"
        const val EXTRA_LONGITUDE = "EXTRA_LONGITUDE"
        const val ACTION_BURN = "com.techhue.alien.action.BURN_IT_WITH_FIRE"
        const val NEW_LIFEFORM = "com.techhue.alien.action.NEW_LIFEFORM"
    }
}
