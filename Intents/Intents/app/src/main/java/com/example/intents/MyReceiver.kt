package com.example.intents

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver: BroadcastReceiver() {
    private val tag:String="My Receiver"
    val s:String = "com.example.Intents"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(tag, "My Receiver Received Broadcast")
    }
}