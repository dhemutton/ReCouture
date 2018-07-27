package com.example.recouture.AccessoriesGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Accessories extends Item {


    public static final String CATEGORY = "Shoes";

    public Accessories() {}


    public Accessories(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Accessories(Parcel in) {
        super(in);
    }

    public static final Creator<Accessories> CREATOR = new Creator<Accessories>() {
        @Override
        public Accessories createFromParcel(Parcel in) {
            return new Accessories(in);
        }

        @Override
        public Accessories[] newArray(int size) {
            return new Accessories[size];
        }
    };


}
