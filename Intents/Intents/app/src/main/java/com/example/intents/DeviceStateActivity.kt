@file:Suppress("DEPRECATION")

package com.example.intents

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DeviceStateActivity : Activity() {
    @SuppressLint("SetTextI18n")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_device)
        val context: Context = this
        val charging = findViewById<View>(R.id.charging) as TextView
        val connected = findViewById<View>(R.id.connected) as TextView
        val mobileNetwork = findViewById<View>(R.id.mobile) as TextView
        val docked = findViewById<View>(R.id.docked) as TextView

        /**
         * Listing 5-18: Determining battery and charge state information
         */
        val batIntentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val battery = context.registerReceiver(null, batIntentFilter)
        val status = battery!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL

        /**
         * Listing 5-19: Determining connectivity state information
         */
        val svcName = Context.CONNECTIVITY_SERVICE
        val cm = context.getSystemService(svcName) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork.isConnectedOrConnecting
        val isMobile = activeNetwork.type ==
                ConnectivityManager.TYPE_MOBILE
        /**
         * Listing 5-20: Determining docking state information
         */
        /*
        IntentFilter dockIntentFilter = new IntentFilter(Intent.ACTION_DOCK_EVENT);
        Intent dock = context.registerReceiver(null, dockIntentFilter);

        int dockState = dock.getIntExtra(Intent.EXTRA_DOCK_STATE,
                Intent.EXTRA_DOCK_STATE_UNDOCKED);
        boolean isDocked = dockState != Intent.EXTRA_DOCK_STATE_UNDOCKED;
        */
        /**
         * Listing 5-21: Dynamically toggling manifest Receivers
         */
        val myReceiverName = ComponentName(this, MyReceiver::class.java)
        val pm = packageManager

        // Enable a manifest receiver
        pm.setComponentEnabledSetting(
            myReceiverName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        // Disable a manifest receiver
        pm.setComponentEnabledSetting(
            myReceiverName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

        // View Output
        Log.d(TAG, "Is Charging? $isCharging")
        charging.text = "Mobile Is Charging $isCharging"
        Log.d(TAG, "Is Connected? $isConnected")
        connected.text = "Mobile Is Connected to Network $isConnected"
        Log.d(TAG, "Is Mobile? $isMobile")
        mobileNetwork.text = "Mobile Is Connected to Mobile Network $isMobile"

        //Log.d(TAG, "Is Docked? " + isDocked);
        //charging.setText("Mobile Is Docked" + isDocked);
    }

    companion object {
        private const val TAG = "DEVICE_STATE_ACTIVITY"
    }
}