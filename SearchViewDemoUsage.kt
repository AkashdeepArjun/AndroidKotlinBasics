package com.example.filteringapp

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.filteringapp.adapter.FruitAdapter
import com.example.filteringapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var glide:RequestManager
    @Inject
    lateinit var myadapter:FruitAdapter
    lateinit var searchManager: SearchManager
    lateinit var link:Uri
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRv()
    }
 
    fun setUpRv(){
        binding.rvVegetables.apply {
            adapter=myadapter
            layoutManager=GridLayoutManager(this@MainActivity,2)
            addItemDecoration(GridManagerAssistant(16,2))
        }}
    override fun onBackPressed() {
        Toast.makeText(this, "backbutton was pressed ", Toast.LENGTH_SHORT).show()

//        binding.sv.clearFocus()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        
        // SEARCH VIEW REFERENCE 
        val sv = (menu?.findItem(R.id.search))?.actionView as SearchView
        
        // MENU ITEM REFERENCE
        val menu_item = menu.findItem(R.id.search)
        
        //ADDING LISTENER TO SEARCH VIEW
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                myadapter.filter.filter(query)
                sv.clearFocus()
                return true
            }

            //CALLBACKS WHEN USER QUERIES 
            override fun onQueryTextChange(newText: String?): Boolean {
                myadapter.filter.filter(newText)
                if (menu?.findItem(R.id.search).isActionViewExpanded) {
                    Toast.makeText(
                        this@MainActivity,
                        "yup search view expanded",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return true
            }
        })
        
        // IF QUERY IS EMPTY LIST RETURNS TO NORMAL STATE 
        sv.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (!hasFocus && sv.query.isEmpty()) {

                sv.setQuery("", false)
                menu_item.collapseActionView()   //  WORKING VIA MENU ITEM REFERENCE 
//                sv.onActionViewCollapsed()        NOT WORKING 

            }
        }
        return true
    }
}






