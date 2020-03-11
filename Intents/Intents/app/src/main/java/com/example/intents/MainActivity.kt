package com.example.intents

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

     val tag:String = "INTENT_ACTIVITY"
     private val somethingWeird:Boolean = true
     private val itDontLookGood:Boolean = true

    private fun explicitlyStartingAnActivity() {
        /**
         * Listing 5-1
         */
        val intent = Intent(this@MainActivity, SelectHorseActivity::class.java)
        startActivity(intent)
    }

    private fun implicitlyStartingAnActivity() {
        if(somethingWeird && itDontLookGood) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:555-6267"))
            val pm:PackageManager = packageManager
            val cn:ComponentName = intent.resolveActivity(pm)
            if(cn==null) {
                val marketUri:Uri = Uri.parse("market://search?q=pname:com.example.packagename")
                val marketIntent:Intent= Intent(Intent.ACTION_VIEW).setData(marketUri)
                if(marketIntent.resolveActivity(pm) != null)
                    startActivity(marketIntent)
                else
                    Log.d(tag ,"Market client not available")
            }
            else
                startActivity(intent)
        }
    }

    private val SHOW_SUBACTIVITY:Int = 1

    private fun startSubActivity() {
        val intent = Intent(this@MainActivity, MyOtherActivity::class.java)
        startActivityForResult(intent, SHOW_SUBACTIVITY)
    }

    private val PICK_CONTACT_SUBACTIVITY:Int = 2

    private fun startSubActivityImplicitly() {
        val uri:Uri = Uri.parse("cotent://contacts/people")
        val intent:Intent = Intent(Intent.ACTION_PICK,uri)
        startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY)
    }

    private val SELECTHORSE:Int = 1
    private val SELECTGUN:Int = 2

    private var selectedHorse:Uri? = null
    private var selectedGun:Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECTHORSE -> if (resultCode == Activity.RESULT_OK) selectedHorse =
                data!!.data
            SELECTGUN -> if (resultCode == Activity.RESULT_OK) selectedGun =
                data!!.data
            else -> {
            }
        }
    }

    private fun startSpecificWebsiteUsingAnIntentFilter(){
        val intent:Intent = Intent(this@MainActivity,MyBlogViewerActivity::class.java)
        startActivity(intent)
    }

    private fun listOfPossibleAction() {
        val packageManager:PackageManager = packageManager
        val intent:Intent = Intent()
        intent.setData(MoonBaseProvider.CONTENT_URI)
        intent.addCategory(Intent.CATEGORY_SELECTED_ALTERNATIVE)
        val flags:Int = PackageManager.MATCH_DEFAULT_ONLY
        val actions:List<ResolveInfo> = packageManager.queryIntentActivities(intent,flags)
        val labels:ArrayList<String> = ArrayList()
        val r:Resources = resources
        for (action in actions) labels.add(r.getString(action.labelRes))

        for (label in labels) Log.d(tag, label)
    }

    private fun startDeviceStateActivity() {
        val intent:Intent = Intent(this@MainActivity,DeviceStateActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        // Create the intent used to resolve which actions
        // should appear in the menu.
        val intent = Intent()
        intent.setData(MoonBaseProvider.CONTENT_URI)
        intent.addCategory(Intent.CATEGORY_SELECTED_ALTERNATIVE)

        // Normal menu options to let you set a group and ID
        // values for the menu items you're adding.
        val menuGroup = 0
        val menuItemId = 0
        val menuItemOrder = Menu.NONE

        // Provide the name of the component that's calling
        // the action -- generally the current Activity.
        val caller = componentName

        // Define intents that should be added first.
        val specificIntents: Array<Intent>? = null
        // The menu items created from the previous Intents
        // will populate this array.
        val outSpecificItems: Array<MenuItem>? = null

        // Set any optional flags.
        val flags = Menu.FLAG_APPEND_TO_GROUP

        // Populate the menu
        menu.addIntentOptions(
            menuGroup,
            menuItemId,
            menuItemOrder,
            caller,
            specificIntents,
            intent,
            flags,
            outSpecificItems
        )
        return true
    }

    //***

    //***
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonExplicitStart =
            findViewById<View>(R.id.button1) as Button
        buttonExplicitStart.setOnClickListener { explicitlyStartingAnActivity() }
        val buttonImplicitStart =
            findViewById<View>(R.id.button2) as Button
        buttonImplicitStart.setOnClickListener { implicitlyStartingAnActivity() }
        val buttonSubActivity =
            findViewById<View>(R.id.button3) as Button
        buttonSubActivity.setOnClickListener { startSubActivity() }
        val buttonSubActivityImplicitly =
            findViewById<View>(R.id.button4) as Button
        buttonSubActivityImplicitly.setOnClickListener { startSubActivityImplicitly() }
        val buttonThirdPartyActions =
            findViewById<View>(R.id.button5) as Button
        buttonThirdPartyActions.setOnClickListener { listOfPossibleAction() }
        val specificWebsiteUsingIntentFilter =
            findViewById<View>(R.id.button6) as Button
        specificWebsiteUsingIntentFilter.setOnClickListener { startSpecificWebsiteUsingAnIntentFilter() }
        val deviceStateActivity =
            findViewById<View>(R.id.button7) as Button
        deviceStateActivity.setOnClickListener { startDeviceStateActivity() }
    }

    override fun onSaveInstanceState(
        outState: Bundle?,
        outPersistentState: PersistableBundle?
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
    }
}
