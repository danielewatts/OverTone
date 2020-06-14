package com.example.overtone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.overtone.data.JsonDataRetrieval;
import com.example.overtone.data.SingularChordDm;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeBottomNavigationBar();

        String jsonString = null;
        try {
            jsonString = JsonDataRetrieval.getJsonString("chord.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        jsonString = loadJSONFromAsset(this,"chord.json");
        Gson gson = new Gson();
        ArrayList<SingularChordDm> bunchOChords = gson.fromJson(jsonString, new TypeToken<ArrayList<SingularChordDm>>(){}.getType());
        for(SingularChordDm c : bunchOChords){
            Log.d(TAG, c.toString());
        }




    }

    public void initializeBottomNavigationBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    /** this works apparently for returning the json string**/
    public String loadJSONFromAsset(Context context,String filePath) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }






}