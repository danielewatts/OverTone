package com.example.overtone.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionHandler {

    public static void getNeededPermissions(Context cntx, Activity activ, String[] PERMISSIONS, int PERMISSION_CODE_ALL){
        if (hasPermissions(cntx, PERMISSIONS)!=true) {
            ActivityCompat.requestPermissions(activ, PERMISSIONS, PERMISSION_CODE_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }






}
