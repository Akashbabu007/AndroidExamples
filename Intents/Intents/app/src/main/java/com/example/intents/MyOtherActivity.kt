package com.example.intents

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView

class MyOtherActivity : Activity() {
    /**
     * Listing 5-14: Finding the launch Intent in an Activity
     */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_main)
        val intent = intent
        val action = intent.action
        val data = intent.data
    }

    public override fun onStart() {
        super.onStart()
        setContentView(R.layout.selector_layout)
        val listView =
            findViewById<View>(R.id.listView1) as ListView
        val okButton =
            findViewById<View>(R.id.ok_button) as Button
        okButton.setOnClickListener {
            val selected_id = listView.selectedItemId
            val result = Intent(
                Intent.ACTION_PICK,
                Uri.parse(java.lang.Long.toString(selected_id))
            )
            setResult(RESULT_OK, result)
            finish()
        }
        val cancelButton =
            findViewById<View>(R.id.cancel_button) as Button
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}