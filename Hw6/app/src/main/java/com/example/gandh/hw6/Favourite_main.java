package com.example.gandh.hw6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by gandh on 2/24/2017.
 */

public class Favourite_main extends AppCompatActivity {
    ArrayAdapter adapter;
    ArrayList<Apps_data> array_apps,array;
    Adaptor_apps adaptor2;
    Spinner spinner;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. favourite);
        setTitle("Favourite Apps");
        String[] li = {"none","Sort Increasingly", "Sort Decreasingly"};
        adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,li);
        lv = (ListView) findViewById(R.id.lv2);
        array_apps = new ArrayList<>();
        array = (ArrayList<Apps_data>) getIntent().getExtras().getSerializable("key");
        for (Apps_data data :
                array) {
            if (data.getFavourite().equals( "yes"))
                array_apps.add(data);
        }
        if(array_apps.size()==0)
        {
            String[] l1 = {"No Favourites"};
            ArrayAdapter adapter2 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,l1);
            lv.setAdapter(adapter2);
            adapter2.setNotifyOnChange(true);
        }
        else
        {
            adaptor2 = new Adaptor_apps(this, R.layout.app_list, array_apps);
            lv.setAdapter(adaptor2);
            adaptor2.setNotifyOnChange(true);
        }


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_layout, menu);
        MenuItem item = menu.findItem(R.id.spinner);
         spinner = (Spinner) item.getActionView();

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if(position==1)
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
                    adaptor2 = new Adaptor_apps(Favourite_main.this,R.layout.app_list,array_apps);
                    lv.setAdapter(adaptor2);
                    spinner.setSelection(0);
                }
                else if(position==2)
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

                    spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        return true;
    }
}
