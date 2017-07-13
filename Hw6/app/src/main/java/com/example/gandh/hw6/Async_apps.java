package com.example.gandh.hw6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by gandh on 2/23/2017.
 */

public class Async_apps extends AsyncTask<String,Void,ArrayList<Apps_data>> {
    Json_apps jsonp = new Json_apps();
    list_gen activity;

    public Async_apps(list_gen activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Apps_data> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String add = "";
            while((add=bfr.readLine())!=null)
            {
                sb.append(add);
            }

         ArrayList< Apps_data> arrayapps = jsonp.parser(sb.toString());
            for (Apps_data a :
                    arrayapps) {
                URL url1 = new URL(a.getThumb());
                HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();
                con1.setRequestMethod("GET");

                Bitmap bit = BitmapFactory.decodeStream(con1.getInputStream());
                a.setBit(bit);
            }

            return arrayapps;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Apps_data> apps_datas) {
        activity.list_generator(apps_datas);
    }

    interface list_gen{

        void list_generator(ArrayList<Apps_data> arrayapps);
    }
}
