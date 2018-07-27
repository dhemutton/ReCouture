package com.example.recouture.SweaterGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Sweater extends Item {


    public static final String CATEGORY = "Shoes";

    public Sweater() {}


    public Sweater(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Sweater(Parcel in) {
        super(in);
    }

    public static final Creator<Sweater> CREATOR = new Creator<Sweater>() {
        @Override
        public Sweater createFromParcel(Parcel in) {
            return new Sweater(in);
        }

        @Override
        public Sweater[] newArray(int size) {
            return new Sweater[size];
        }
    };


}
