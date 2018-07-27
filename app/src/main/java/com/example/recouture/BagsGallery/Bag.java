package com.example.recouture.BagsGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Bag extends Item {


    public static final String CATEGORY = "Shoes";

    public Bag() {}


    public Bag(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Bag(Parcel in) {
        super(in);
    }

    public static final Creator<Bag> CREATOR = new Creator<Bag>() {
        @Override
        public Bag createFromParcel(Parcel in) {
            return new Bag(in);
        }

        @Override
        public Bag[] newArray(int size) {
            return new Bag[size];
        }
    };


}
