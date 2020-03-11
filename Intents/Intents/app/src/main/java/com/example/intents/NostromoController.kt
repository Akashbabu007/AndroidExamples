package com.example.intents

import androidx.appcompat.app.AppCompatActivity
import android.content.ContentUris
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class NostromoController: AppCompatActivity() {

    override fun onCreate(icile: Bundle?) {
        super.onCreate(icile)
        setContentView(R.layout.selector_layout)
        val listView: ListView = findViewById(R.id.listView1)

        val okButton:Button = findViewById(R.id.ok_button)
        okButton.setOnClickListener {
            val selectedid:Long = listView.selectedItemId
            val result = Intent(Intent.ACTION_PICK,
            ContentUris.withAppendedId(MoonBaseProvider.CONTENT_URI,selectedid))
            setResult(RESULT_OK, result)
            finish()
        }

        val cancelButton:Button = findViewById(R.id.cancel_button)
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}