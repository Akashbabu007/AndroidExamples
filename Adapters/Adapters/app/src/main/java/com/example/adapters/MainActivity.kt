package com.example.adapters

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myListView = findViewById<ListView>(R.id.my_list_view)
        val layoutID = android.R.layout.simple_list_item_1

        val myStringArray:ArrayList<String> = ArrayList()
        val myAdapterInstance:ArrayAdapter<String> = ArrayAdapter(this, layoutID, myStringArray)
        myListView.adapter = myAdapterInstance

        myStringArray.add("The")
        myStringArray.add("quick")
        myStringArray.add("green")
        myStringArray.add("android")
        myStringArray.add("jumped")
        myStringArray.add("over")
        myStringArray.add("the")
        myStringArray.add("lazy")
        myAdapterInstance.notifyDataSetChanged()

        val myClassesArray: ArrayList<MyClass> = ArrayList()
        myClassesArray.add(MyClass("Khojo"))
        myClassesArray.add(MyClass("Mojo"))
        myClassesArray.add(MyClass("Tojo"))
        myClassesArray.add(MyClass("Dojo"))
        val myArrayAdapter = MyArrayAdapter(this, layoutID, myClassesArray)
        myListView.adapter = myArrayAdapter


    }
}
