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

public class MainActivity extends AppCompatActivity{
    private static final String TAG = " inside MainActivity";
    private static ArrayList<ChordGroup> chordGroupsList;
    private static ArrayList<SingleChord> singleChordList;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        singleChordList = DataCreation.getSingleChords(getApplicationContext());
        chordGroupsList = DataCreation.getCreatedGroups(singleChordList);
        ///data is created
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeBotNavBar();
    }



    public void initializeBotNavBar(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = findNavController(this, R.id.nav_host_fragment);
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




}