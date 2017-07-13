package com.example.gandh.hw6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Async_apps.list_gen {
    ArrayAdapter adapter;
    Adaptor_apps adaptor2;
    ArrayList<Apps_data> array_apps;
    ImageView iv,iv1;
    ListView lv;
    TextView tv;
    Spinner spinner;
    ProgressBar pb;
    static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("iTunes Top Paid Apps");
        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        lv= (ListView) findViewById(R.id.lv1);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        pb.setVisibility(pb.VISIBLE);
        if (isconnected()) {
            String[] list = {"","Refresh List", "Favourites", "Sort Increasingly", "Sort Decreasingly"};

            adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
            new Async_apps(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) item.getActionView();
        spinner.setEnabled(false);
        Drawable image=(Drawable)getResources().getDrawable(R.drawable.ic_view_headline_black_dp);
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 10, 10, true));
        spinner.setBackground(d);
        spinner.setAdapter(adapter); // set the adapter to provide layout of rows and content
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1) {
                    new Async_apps(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
                    spinner.setSelection(0);
                    spinner.setEnabled(false);
                    pb.setVisibility(pb.VISIBLE);
                    lv.removeAllViewsInLayout();
                }
                else if(position==2)
                {
                    Intent ia = new Intent(MainActivity.this,Favourite_main.class);
                    ia.putExtra("key",array_apps);
                    startActivity(ia);
                    spinner.setSelection(0);
                }
                else if(position==3)
                {
                    Collections.sort(array_apps, new Comparator<Apps_data>() {
                        @Override
                        public int compare(Apps_data o1, Apps_data o2) {
                            if (Double.parseDouble(o1.getPrice().substring(1)) > Double.parseDouble(o2.getPrice().substring(1))) {
                                return 1;
                            } else if (Double.parseDouble(o1.getPrice().substring(1)) < Double.parseDouble(o2.getPrice().substring(1))) {
                                return -1;
                            }
                            return 0;
                        }
                    });
                    adaptor2 = new Adaptor_apps(MainActivity.this,R.layout.app_list,array_apps);
                    lv.setAdapter(adaptor2);
                    spinner.setSelection(0);
                }
                else if(position==4)
                {
                    Collections.sort(array_apps, new Comparator<Apps_data>() {
                        @Override
                        public int compare(Apps_data o1, Apps_data o2) {
                            if (Double.parseDouble(o1.getPrice().substring(1)) > Double.parseDouble(o2.getPrice().substring(1))) {
                                return -1;
                            } else if (Double.parseDouble(o1.getPrice().substring(1)) < Double.parseDouble(o2.getPrice().substring(1))) {
                                return 1;
                            }
                            return 0;
                        }
                    });
                    adaptor2 = new Adaptor_apps(MainActivity.this,R.layout.app_list,array_apps);
                    lv.setAdapter(adaptor2);
                    spinner.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }

    boolean isconnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo nw = cm.getActiveNetworkInfo();
        if(nw!= null && nw.isConnected())
            return  true;
        else
            return false;

    }

    @Override
    public void list_generator(ArrayList<Apps_data> arrayapps) {
        this.array_apps = arrayapps;
        pb.setVisibility(pb.INVISIBLE);
        spinner.setEnabled(true);
         adaptor2 = new Adaptor_apps(this,R.layout.app_list,array_apps);
        lv.setAdapter(adaptor2);
        adaptor2.setNotifyOnChange(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo",position+"");


            }
        });
    }


}
