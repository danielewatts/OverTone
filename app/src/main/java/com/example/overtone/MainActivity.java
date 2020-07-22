package com.example.overtone;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.overtone.data.ChordGroup;
import com.example.overtone.data.DataCreation;
import com.example.overtone.data.SingleChord;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = " inside MainActivity";
    private static ArrayList<ChordGroup> chordGroupsList;
    private static ArrayList<SingleChord> singleChordList;
    private BottomNavigationView bottomNavigationView;
    final Fragment fragment1 = new HomeTuneFrag();
    final Fragment fragment2 = new ChordLibraryFrag();
    final Fragment fragment3 = new PracticeModeFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("inside onCreate");
        singleChordList = DataCreation.getSingleChords(getApplicationContext());
        chordGroupsList = DataCreation.getCreatedGroups(singleChordList);
        ///data is created
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeBotNavBar();
//        fm.beginTransaction().add(R.id.nav_host_fragment, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment,fragment1,"1").hide(fragment1).commit();

        /**line breaks program */
//        fm.beginTransaction().add(R.id.nav_host_fragment,fragment1, "1").commit();
        ////testing to see if drawable IDs can be grabbed, so json file can be set

    }

//    public void initializeBottomNavigationBar(){
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        NavController navController = findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupWithNavController(bottomNavigationView,navController);
//    }

    public void initializeBotNavBar(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        NavController navController = findNavController(this, R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        /** the key that gets put in the bundle needs 2 be the same as the args name in
         * navigation
         * do not really need what is below
         */
        String key = getString(R.string.initalBundleKey);
        bundle.putString(key,"PRAY THIS WORKS");
        navController.setGraph(R.navigation.bot_nav_graph,bundle);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }
    public static ArrayList<ChordGroup> getAllChordGroups(){
        return chordGroupsList;
    }
    public static ArrayList<SingleChord> getAllSingleChords(){
        return singleChordList;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        /*
        *  this is broken / not even getting called figure out how to fix this
        * */
        System.out.println("Inside the onNavSel");
        switch (item.getItemId()){
            case R.id.chordLibraryFrag:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;
            case R.id.homeTuneFrag:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;
            case R.id.practiceModeFragment:
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                return true;
        }
        return false;
    }


}