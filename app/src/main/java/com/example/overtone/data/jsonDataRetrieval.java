package com.example.overtone.data;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class jsonDataRetrieval {
    private static Object actuallyT;


    static Gson gson;

    public static String loadJSONFromAsset(Context context,String jsonFilePath) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFilePath);

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
