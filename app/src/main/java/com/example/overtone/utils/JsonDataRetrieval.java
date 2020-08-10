/* utility Class with the purpose of retrieving a json string from a given json file*/
package com.example.overtone.utils;
import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

public class JsonDataRetrieval {

    private static final String ENCODING = "UTF-8";
    /** this works apparently for returning the json string**/
    public static String loadJSONFromAsset(Context context,String filePath) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, ENCODING);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
