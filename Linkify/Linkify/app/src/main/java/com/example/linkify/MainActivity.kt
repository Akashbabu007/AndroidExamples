package com.example.linkify

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.text.util.Linkify.MatchFilter
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myTextView: TextView = findViewById(R.id.text_view)
        val baseUri = "content://com.example.earthquake/earthquakes/"
        val pm: PackageManager = packageManager
        val testIntent = Intent(Intent.ACTION_VIEW, Uri.parse(baseUri))
        val activityExists: Boolean = testIntent.resolveActivity(pm) != null

        if (activityExists) {
            Log.d("Checking", "True")
            val flags: Int = Pattern.CASE_INSENSITIVE
            val p: Pattern = Pattern.compile("\\bquake[\\s]?[0-9]+\\b", flags)
            Linkify.addLinks(myTextView, p, baseUri)
        } else {
            Log.d("Checking", "False")
        }
    }

    class MyMatchFilter : MatchFilter {
        override fun acceptMatch(s: CharSequence, start: Int, end: Int): Boolean {
            return start == 0 || s[start - 1] != '!'
        }
    }

    /**
     * Listing 5-9: Using a Linkify Transform Filter
     */
    class MyTransformFilter : Linkify.TransformFilter {
        override fun transformUrl(match: Matcher, url: String): String {
            return url.toLowerCase().replace(" ", "")
        }
    }
}
