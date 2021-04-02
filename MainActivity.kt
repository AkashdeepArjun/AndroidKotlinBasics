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
        handleIntent(intent)
//             binding.sv.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String): Boolean {
//                    myadapter.filter.filter(query)
//                    binding.sv.clearFocus()
//                    return true
//
//            }
//                 override fun onQueryTextChange(newText: String): Boolean {
//                    Toast.makeText(this@MainActivity,"${binding.sv.isIconified}",Toast.LENGTH_SHORT).show()
//                    myadapter.filter.filter(newText)
//                return true
//            }
//        })
//        binding.sv.queryHint="search vegetables"
//        binding.sv.isIconified=false
//        binding.sv.setOnQueryTextFocusChangeListener { v, hasFocus ->
//
//            if(hasFocus){
//                v.isEnabled=fals
//            }
//        }
//        binding.sv.setOnCloseListener {
//            Toast.makeText(this,"search view closed",Toast.LENGTH_SHORT).show()
//            binding.sv.setQuery("",false)
//        binding.sv.onActionViewCollapsed()
//        true
//        }
//
//        val closebtn: View?=binding.sv.findViewById(androidx.appcompat.R.id.search_close_btn)
//        closebtn?.setOnClickListener {
//            binding.sv.setQuery("",false)
//            binding.sv.onActionViewCollapsed()
//      }


    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
    private fun handleIntent(intent: Intent) {

        if(Intent.ACTION_SEARCH==intent.action)
        {
            intent.getStringExtra(SearchManager.QUERY)?.also {
                myadapter.filter.filter(it)
            }
        }

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
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val sv = (menu?.findItem(R.id.search))?.actionView as SearchView
        val menu_item = menu.findItem(R.id.search)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                myadapter.filter.filter(query)
                sv.clearFocus()
                return true
            }

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
        sv.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (!hasFocus && sv.query.isEmpty()) {

                sv.setQuery("", false)
                menu_item.collapseActionView()   //  WORKING
//                sv.onActionViewCollapsed()        NOT WORKING

            }
        }
        return true
    }



//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when(item.itemId){
//            R.id.search->{
//                val sv=item.actionView as SearchView
////                sv.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
////                    override fun onQueryTextSubmit(query: String?): Boolean {
////                       myadapter.filter.filter(query)
////                        return true
////                    }
////
////                    override fun onQueryTextChange(newText: String?): Boolean {
////
////                        myadapter.filter.filter(newText)
////                        return true
////                    }
////                })
////                sv.setOnCloseListener {
////                    sv.setQuery("",false)
////                    sv.onActionViewCollapsed()
////                    false
////                }
//
////                val closebtn: View?=sv.findViewById(androidx.appcompat.R.id.search_close_btn)
////        closebtn?.setOnClickListener {
////            sv.setQuery("",false)
////            sv.onActionViewCollapsed()
////      }
//                return true}
//            else->{return super.onOptionsItemSelected(item)}
//        }
//
//
//    }

}






