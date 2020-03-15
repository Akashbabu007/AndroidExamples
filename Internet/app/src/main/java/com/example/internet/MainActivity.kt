package com.example.internet

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection

class MainActivity : AppCompatActivity() {

    private val tag:String = "Internet"

    private fun openingInternetDataStream() {
        val myFeed:String = getString(R.string.my_feed)
        try {
            val url = URL(myFeed)
            val connection:URLConnection = url.openConnection()
            val httpConnection:HttpURLConnection = (connection as HttpURLConnection)

            val responseCode:Int = httpConnection.responseCode
            if(responseCode == HttpURLConnection.HTTP_OK) {
                val inputstream:InputStream = httpConnection.inputStream
                processStream(inputstream)
            }
        }
        catch(e: MalformedURLException) {
            Log.d(tag,"Malformed URL Exception.", e)
        }
        catch (e:IOException) {
            Log.d(tag,"IO Exception", e)
        }
    }

    private fun processStream(inputStream:InputStream) {
        val factory:XmlPullParserFactory
        try {
            factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp:XmlPullParser = factory.newPullParser()

            xpp.setInput(inputStream, null)
            var eventType:Int = xpp.eventType

            while(eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_TAG && xpp.name == "result") {
                    eventType = xpp.next()
                    var name:String = ""
                    while (!(eventType == XmlPullParser.END_TAG && xpp.name == "result")) { // Check for the name tag within the results tag.
                        if (eventType == XmlPullParser.START_TAG && xpp.name == "name") // Extract the POI name.
                            name = xpp.nextText()
                        eventType = xpp.next()
                    }
                }
                eventType = xpp.next()
            }
        }
        catch(e:XmlPullParserException) {
            Log.d("PULLPARSER", "XML Pull Parser Exception", e)
        }
        catch (e:IOException)
        {
            Log.d("PULLPARSER", "IO Exception", e)
        }
    }

    private fun downloadFileUsingDownloadManager() {

        val serviceString = Context.DOWNLOAD_SERVICE
        val downloadManager: DownloadManager
        downloadManager = getSystemService(serviceString) as DownloadManager
        val uri =
            Uri.parse("http://developer.android.com/shareables/icon_templates-v4.0.zip")
        val request = DownloadManager.Request(uri)
        val reference = downloadManager.enqueue(request)
        //
        myDownloadReference = reference
        Log.d(tag, "Download Reference: $reference")
    }

    private fun monitorDownloads() {
        val downloadManager =
            getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val reference =
                    intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (myDownloadReference == reference) {

                    val myDownloadQuery = DownloadManager.Query()
                    myDownloadQuery.setFilterById(reference)
                    val myDownload = downloadManager.query(myDownloadQuery)
                    if (myDownload.moveToFirst()) {
                        val fileNameIdx =
                            myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME)
                        val fileUriIdx =
                            myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                        val fileName = myDownload.getString(fileNameIdx)
                        val fileUri = myDownload.getString(fileUriIdx)
                        // TODO Do something with the file.
                        Log.d(
                            tag,
                            "$fileName : $fileUri"
                        )
                    }
                    myDownload.close()
                }
            }
        }
        registerReceiver(receiver, filter)
    }

    private fun downloadNotificationClick() {
        /**
         * Listing 6-5: Responding to download notification clicks
         */
        val filter = IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED)
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val extraID = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS
                val references = intent.getLongArrayExtra(extraID)
                for (reference in references) if (reference == myDownloadReference) { // Do something with downloading file.
                }
            }
        }
        registerReceiver(receiver, filter)
    }

    private fun detailsOfPauseDownloads() {

        val serviceString = Context.DOWNLOAD_SERVICE
        val downloadManager: DownloadManager
        downloadManager = getSystemService(serviceString) as DownloadManager

        val pausedDownloadQuery = DownloadManager.Query()
        pausedDownloadQuery.setFilterByStatus(DownloadManager.STATUS_PAUSED)

        val pausedDownloads = downloadManager.query(pausedDownloadQuery)

        val reasonIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_REASON)
        val titleIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_TITLE)
        val fileSizeIdx =
            pausedDownloads.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
        val bytesDLIdx =
            pausedDownloads.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)

        while (pausedDownloads.moveToNext()) {
            val title = pausedDownloads.getString(titleIdx)
            val fileSize = pausedDownloads.getInt(fileSizeIdx)
            val bytesDL = pausedDownloads.getInt(bytesDLIdx)

            val reason = pausedDownloads.getInt(reasonIdx)
            var reasonString = "Unknown"
            when (reason) {
                DownloadManager.PAUSED_QUEUED_FOR_WIFI -> reasonString = "Waiting for WiFi"
                DownloadManager.PAUSED_WAITING_FOR_NETWORK -> reasonString =
                    "Waiting for connectivity"
                DownloadManager.PAUSED_WAITING_TO_RETRY -> reasonString = "Waiting to retry"
                else -> {
                }
            }
            val sb = StringBuilder()
            sb.append(title).append("\n")
            sb.append(reasonString).append("\n")
            sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize)
            // Display the status
            Log.d("DOWNLOAD", sb.toString())
        }

        pausedDownloads.close()
    }

    private var myDownloadReference: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonOpen =
            findViewById<View>(R.id.button1) as Button
        buttonOpen.setOnClickListener { openingInternetDataStream() }

        val buttonDownload =
            findViewById<View>(R.id.button2) as Button
        buttonDownload.setOnClickListener {
            downloadNotificationClick()
            monitorDownloads()
            downloadFileUsingDownloadManager()
        }

        val buttonPaused =
            findViewById<View>(R.id.button3) as Button
        buttonPaused.setOnClickListener { detailsOfPauseDownloads() }
    }
}
