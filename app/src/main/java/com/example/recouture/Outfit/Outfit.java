package com.example.recouture.Outfit;

import android.os.Parcelable;

import com.example.recouture.Item;


public class Outfit {
    private String mName;
    private String mImageUrl;


    public Outfit() {}


    public Outfit(String mName, String mImageUrl) {
        this.mName = mName;
        this.mImageUrl = mImageUrl;

    }

    public String getmName() {
        return mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }


}
