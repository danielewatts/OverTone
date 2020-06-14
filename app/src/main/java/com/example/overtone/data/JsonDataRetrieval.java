package com.example.overtone.data;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonDataRetrieval {

    static Gson gson;
    public static String loadJSONFromAsset(Context context,String FilePath) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(FilePath);
//            int size = is.available();
            int bufferSize = 8*1024;
            byte[] buffer = new byte[bufferSize];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }




    public static String getJsonChordString(){
        String jsonChordString = "[\n" +
                "  {\n" +
                "      \"chordName\": \"C\",\n" +
                "      \"barChord\": false,\n" +
                "      \"openChord\":true,\n" +
                "      \"popularChord\":true,\n" +
                "      \"diffLevel\": \"1\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"A\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":true,\n" +
                "    \"diffLevel\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"A7\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":false,\n" +
                "    \"diffLevel\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "  \"chordName\": \"Am\",\n" +
                "  \"barChord\": false,\n" +
                "  \"openChord\":true,\n" +
                "  \"popularChord\":true,\n" +
                "  \"diffLevel\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"Bb\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":false,\n" +
                "    \"diffLevel\": \"1\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"E\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":true,\n" +
                "    \"diffLevel\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"Dm\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":false,\n" +
                "    \"diffLevel\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"G\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":true,\n" +
                "    \"diffLevel\": \"1\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"Am7\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":false,\n" +
                "    \"diffLevel\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"Em\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":true,\n" +
                "    \"diffLevel\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"chordName\": \"D\",\n" +
                "    \"barChord\": false,\n" +
                "    \"openChord\":true,\n" +
                "    \"popularChord\":false,\n" +
                "    \"diffLevel\": \"0\"\n" +
                "  }\n" +
                "\n" +
                "]\n";
        return jsonChordString;
    }


}
