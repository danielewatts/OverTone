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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeBottomNavigationBar();

        String jsonFilePath = "chord.json";
        String jsonString = JsonDataRetrieval.loadJSONFromAsset(this,jsonFilePath);
        String jsonHardCodeString = "{\n" +
                "  \"chordName\": \"C\",\n" +
                "  \"barChord\": true\n" +
                "}\n";

        if (jsonString.equals(jsonHardCodeString)) {
            Gson gson = new Gson();
            SingularChordDm singChord = gson.fromJson(jsonHardCodeString,SingularChordDm.class);
        }
        else{
            Gson gson = new Gson();
            SingularChordDm singChord = gson.fromJson(jsonHardCodeString,SingularChordDm.class);
            singChord.toString();
        }

//        Gson gson = new Gson();
//        SingularChordDm singChord = gson.fromJson(jsonHardCodeString,SingularChordDm.class);






    }

    public void initializeBottomNavigationBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }






}