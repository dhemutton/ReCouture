package com.example.recouture.DressGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Dress extends Item {


    public static final String CATEGORY = "Shoes";

    public Dress() {}


    public Dress(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Dress(Parcel in) {
        super(in);
    }

    public static final Creator<Dress> CREATOR = new Creator<Dress>() {
        @Override
        public Dress createFromParcel(Parcel in) {
            return new Dress(in);
        }

        @Override
        public Dress[] newArray(int size) {
            return new Dress[size];
        }
    };


}
