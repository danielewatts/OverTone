package com.example.overtone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.overtone.data.FakeChord;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeBottomNavigationBar();
        jsonTest();







    }

    public void initializeBottomNavigationBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }


    public void jsonTest(){
        Gson gson = new Gson();
        String json = "[\n" +
                "    {\n" +
                "    \"name\": \"C\",\n" +
                "    \"difficulty\": 1\n" +
                "    },\n" +
                "    {\n" +
                "    \"name\": \"A\",\n" +
                "    \"difficulty\": 2\n" +
                "    },\n" +
                "    {\n" +
                "    \"name\": \"D\",\n" +
                "    \"difficulty\": 3\n" +
                "    }\n" +
                "  ]\n" +
                "   ";
        FakeChord[] fc = gson.fromJson(json,FakeChord[].class);
        int jho = 4;

    }




}