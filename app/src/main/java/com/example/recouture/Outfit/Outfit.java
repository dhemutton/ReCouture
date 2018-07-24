package com.example.recouture.Outfit;

import android.os.Parcelable;

import com.example.recouture.Item;


public abstract class Outfit implements Parcelable {
    private String mName;
    private String mImageUrl;


    public Outfit() {}


    public Outfit(String mName, String mImageUrl) {
        this.mName = mName;
        this.mImageUrl = mImageUrl;

    }

}
