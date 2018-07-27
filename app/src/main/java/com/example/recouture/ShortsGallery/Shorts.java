package com.example.recouture.ShortsGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Shorts extends Item {


    public static final String CATEGORY = "Shoes";

    public Shorts() {}


    public Shorts(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Shorts(Parcel in) {
        super(in);
    }

    public static final Creator<Shorts> CREATOR = new Creator<Shorts>() {
        @Override
        public Shorts createFromParcel(Parcel in) {
            return new Shorts(in);
        }

        @Override
        public Shorts[] newArray(int size) {
            return new Shorts[size];
        }
    };


}
