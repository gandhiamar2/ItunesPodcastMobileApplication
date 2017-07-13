package com.example.gandh.hw6;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gandh on 2/23/2017.
 */

public class Json_apps {
    Apps_data adata;
    ArrayList<Apps_data> arrayapps;

    ArrayList<Apps_data> parser(String data) throws JSONException {
        JSONObject sonobject1 = new JSONObject(data);

        arrayapps = new ArrayList<>();
        JSONObject sonobject2 = sonobject1.getJSONObject("feed");
        JSONArray sonarray1 = sonobject2.getJSONArray("entry");
        for(int i=0; i<sonarray1.length();i++)
        {
            adata = new Apps_data();
            JSONObject sonobject_temp = sonarray1.getJSONObject(i);
            JSONObject sonobject3 = sonobject_temp.getJSONObject("im:name");
            adata.setName(sonobject3.getString("label"));
            JSONObject sonobject4 = sonobject_temp.getJSONObject("im:price");
            adata.setPrice(sonobject4.getString("label"));
            JSONArray sonarray2 = sonobject_temp.getJSONArray("im:image");
            JSONObject sonobject5 = sonarray2.getJSONObject(sonarray2.length()-1);
            adata.setThumb(sonobject5.getString("label"));
            adata.setFavourite("no");
            arrayapps.add(adata);

        }
        return  arrayapps;
    }

}
