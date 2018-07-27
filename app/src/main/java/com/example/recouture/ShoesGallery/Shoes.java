package com.example.recouture.ShoesGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Shoes extends Item {


    public static final String CATEGORY = "Shoes";

    public Shoes() {}


    public Shoes(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Shoes(Parcel in) {
        super(in);
    }

    public static final Creator<Shoes> CREATOR = new Creator<Shoes>() {
        @Override
        public Shoes createFromParcel(Parcel in) {
            return new Shoes(in);
        }

        @Override
        public Shoes[] newArray(int size) {
            return new Shoes[size];
        }
    };


}
