package com.example.overtone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.overtone.data.ChordGroup
import com.example.overtone.data.DataCreation
import com.example.overtone.data.SingleChord
import com.example.overtone.utils.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var bottomNavigationView: BottomNavigationView? = null
    private var navController: NavController? = null
    private var currentNavController:LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        allSingleChords = DataCreation.getSingleChords(applicationContext)
        allChordGroups = DataCreation.getCreatedGroups(allSingleChords)
        ///data is created
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            setupBottomNavigationBar()
        }
//        initializeBotNavBar()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

//    fun initializeBotNavBar() {
////        bottomNavigationView = findViewById(R.id.bottomNavigationView)
////        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
////        //        Bundle bundle = new Bundle();
//////        /** the key that gets put in the bundle needs 2 be the same as the args name in
//////         * navigation
//////         * do not really need what is below
//////         */
//////        String key = getString(R.string.initalBundleKey);
//////        bundle.putString(key,"PRAY THIS WORKS");
////        navController?.setGraph(R.navigation.bot_nav_graph)
////        NavigationUI.setupWithNavController(bottomNavigationView!!, navController!!)
////    }
    companion object {
        private const val TAG = " inside MainActivity"
        var allChordGroups: ArrayList<ChordGroup>? = null
            private set
        var allSingleChords: ArrayList<SingleChord>? = null
            private set

    }

    private fun setupBottomNavigationBar(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        /// declaring a list of navigation graph xml files
//        val navGraphIds = listOf(R.navigation.home,R.navigation.list,R.navigation.form)
        val navGraphIds = listOf(R.navigation.tune_nav,R.navigation.game_nav,R.navigation.chord_lib_nav)
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