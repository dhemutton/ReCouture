package com.example.recouture.ShirtGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Shirt extends Item {


    public static final String CATEGORY = "Shirts";

    public Shirt() {}


    public Shirt(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Shirt(Parcel in) {
        super(in);
    }

    public static final Creator<Shirt> CREATOR = new Creator<Shirt>() {
        @Override
        public Shirt createFromParcel(Parcel in) {
            return new Shirt(in);
        }

        @Override
        public Shirt[] newArray(int size) {
            return new Shirt[size];
        }
    };


}
