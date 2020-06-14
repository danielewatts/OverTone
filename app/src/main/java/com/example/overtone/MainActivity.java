package com.example.overtone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.overtone.data.JsonDataRetrieval;
import com.example.overtone.data.SingularChordDm;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeBottomNavigationBar();

        String jsonHardCodeString = JsonDataRetrieval.getJsonChordString();
        Gson gson = new Gson();
        ArrayList<SingularChordDm> bunchOChords = gson.fromJson(jsonHardCodeString, new TypeToken<ArrayList<SingularChordDm>>(){}.getType());
        for(SingularChordDm c : bunchOChords){
            Log.d(TAG, c.toString());
        }




    }

    public void initializeBottomNavigationBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }






}