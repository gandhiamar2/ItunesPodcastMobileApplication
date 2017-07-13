package com.example.gandh.hw6;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by gandh on 2/23/2017.
 */

public class Apps_data implements Serializable{
    String name,price,thumb,favourite;
    transient  Bitmap bit;

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public Bitmap getBit() {
        return bit;
    }

    public void setBit(Bitmap bit) {
        this.bit = bit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }


}
