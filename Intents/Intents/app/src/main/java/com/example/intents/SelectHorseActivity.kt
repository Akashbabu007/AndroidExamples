package com.example.intents

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class SelectHorseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selector_layout)

        val listView: ListView = findViewById(R.id.listView1)

        val okButton:Button = findViewById(R.id.ok_button)
        okButton.setOnClickListener {
            val selecthorseid =listView.selectedItemId

            val selectedHorse:Uri = Uri.parse("content://horses/$selecthorseid")
            val result = Intent(Intent.ACTION_PICK,selectedHorse)
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