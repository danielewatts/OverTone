package com.example.overtone.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.overtone.R
import com.example.overtone.data.ChordGroup
import com.example.overtone.data.DataCreation
import com.example.overtone.data.SingleChord
import com.example.overtone.utils.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import android.Manifest;
import com.example.overtone.utils.PermissionHandler

class MainActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null
    private var navController: NavController? = null
    private var currentNavController:LiveData<NavController>? = null
    private var permissions:Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
    private var permissionAll:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        allSingleChords = DataCreation.getSingleChords(applicationContext)
        allChordGroups = DataCreation.getCreatedGroups(allSingleChords)
        ///data is created
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionHandler.getNeededPermissions(applicationContext,this,permissions,permissionAll)
        if(savedInstanceState==null){
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    companion object {
        var allChordGroups: ArrayList<ChordGroup>? = null
            private set
        var allSingleChords: ArrayList<SingleChord>? = null
            private set
    }

    private fun setupBottomNavigationBar(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        /// declaring a list of navigation graph xml files
//        val navGraphIds = listOf(R.navigation.home,R.navigation.list,R.navigation.form)
        val navGraphIds = listOf(R.navigation.tuning_nav, R.navigation.game_navigation, R.navigation.chord_lib_nav)
        //set up bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_container,
                intent = intent
        )
        //whenever the selected controller changes, setup the action bar
//        controller.observe(this, Observer { navController ->
//            setupActionBarWithNavController(navController) })
        currentNavController = controller
    }
    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?:false
    }


}