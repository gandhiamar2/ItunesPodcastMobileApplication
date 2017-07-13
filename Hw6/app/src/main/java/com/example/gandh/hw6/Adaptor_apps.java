package com.example.gandh.hw6;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandh on 2/23/2017.
 */

public class Adaptor_apps extends ArrayAdapter{

   List<Apps_data> arrayapps;
    Context context;
    AlertDialog alert;
    SharedPreferences sharedpreferences;
    static  class viewholder
    {
        TextView tv;
        ImageView iv;
        ImageButton ib;
    }

    public Adaptor_apps(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayapps = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_list,parent,false);
            viewholder holder = new viewholder();
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            holder.tv = (TextView) convertView.findViewById(R.id.textView);
            holder.ib = (ImageButton) convertView.findViewById(R.id.imageButton);
            convertView.setTag(holder);

        }
            final viewholder holder =  (viewholder) convertView.getTag();
        holder.iv.setImageBitmap(arrayapps.get(position).getBit());
        holder.tv.setText(arrayapps.get(position).getName()+"\n"+"Price: "+arrayapps.get(position).getPrice());
        holder.iv.setClickable(false);
        holder.tv.setClickable(false);
        final SharedPreferences.Editor edit = MainActivity.sharedpreferences.edit();
        holder.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayapps.get(position).getFavourite().equals("no")) {
                    new AlertDialog.Builder(context)
                            .setTitle("Add to Favourites")
                            .setMessage("Are you sure that you want to add this App to favorites?")
                            .setNeutralButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    arrayapps.get(position).setFavourite("yes");
                                    holder.ib.setImageResource(R.drawable.blackstar);
                                    edit.putString(arrayapps.get(position).getName(),"favourite");
                                    edit.apply();
                                }
                            })
                            .show();
                }
                else
                {
                    new AlertDialog.Builder(context)
                            .setTitle("Add to Favourites")
                            .setMessage("Are you sure that you want to remove this App to favorites?")
                            .setNeutralButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    arrayapps.get(position).setFavourite("no");
                                    holder.ib.setImageResource(R.drawable.whitestar);
                                    edit.remove(arrayapps.get(position).getName());
                                    edit.apply();
                                }
                            })
                            .show();
                }


                Log.d("clicked",position+"");
            }
        });
        return convertView;
    }
}
