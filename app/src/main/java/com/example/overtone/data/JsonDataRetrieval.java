package com.example.overtone.data;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonDataRetrieval {

    static Gson gson;
    public static String readFileIntoString(String path) throws FileNotFoundException {
        String fileInString = new Scanner(new File(path)).useDelimiter("\\Z").next();
        return fileInString;
    }

    public static String getJsonString(String filePath) throws FileNotFoundException {
        String jsonString = readFileIntoString(filePath);
        return jsonString;
    }



}
