package com.example.overtone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.overtone.data.ChordGroup;
import com.example.overtone.data.DataCreation;
import com.example.overtone.data.SingleChord;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    private static ArrayList<ChordGroup> chordGroupsList;
    private static ArrayList<SingleChord> singleChordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //code needs to go in here
        singleChordList = DataCreation.getSingleChords(getApplicationContext());
        chordGroupsList = DataCreation.getCreatedGroups(singleChordList);
        ///data is created
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initializeBottomNavigationBar();
        navRevised();

    }

    public void initializeBottomNavigationBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
    public void navRevised(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = findNavController(this, R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        /** the key that gets put in the bundle needs 2 be the same as the args name in
         * navigation
         */
        String key = getString(R.string.initalBundleKey);
        bundle.putString(key,"PRAY THIS WORKS");
        navController.setGraph(R.navigation.bot_nav_graph,bundle);
//        args.put
//        navController.setGraph(R.navigation.bot_nav_graph,);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }
    public static ArrayList<ChordGroup> getAllChordGroups(){
        return chordGroupsList;
    }




}