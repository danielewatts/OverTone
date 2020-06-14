package com.example.overtone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.overtone.data.ChordGroup;
import com.example.overtone.data.DifficultyLevel;
import com.example.overtone.data.JsonDataRetrieval;
import com.example.overtone.data.SingularMusicItemDm;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeBottomNavigationBar();

//        String jsonString = null;
//        try {
//            jsonString = JsonDataRetrieval.getJsonString("chord.json");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        String jsonString = JsonDataRetrieval.loadJSONFromAsset(getApplicationContext(),"chord.json");
        Gson gson = new Gson();
        ArrayList<SingularMusicItemDm> bunchOChords = gson.fromJson(jsonString, new TypeToken<ArrayList<SingularMusicItemDm>>(){}.getType());
        for(SingularMusicItemDm c : bunchOChords){
            Log.d(TAG, c.toString());
        }


        String[] levels = {"Easy","Medium","Hard","Advanced"};
        ArrayList<ChordGroup> allChordDifficultyGroups = new ArrayList<>();
        for(DifficultyLevel d : DifficultyLevel.values()){
            ChordGroup c = new ChordGroup(d.getStrName());
            allChordDifficultyGroups.add(c);
        }
        TreeMap<String, ArrayList<ChordGroup>> cgMap = new TreeMap<>();









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