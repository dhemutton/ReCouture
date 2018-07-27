package com.example.recouture.OuterwearGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Outerwear extends Item {


    public static final String CATEGORY = "Shoes";

    public Outerwear() {}


    public Outerwear(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Outerwear(Parcel in) {
        super(in);
    }

    public static final Creator<Outerwear> CREATOR = new Creator<Outerwear>() {
        @Override
        public Outerwear createFromParcel(Parcel in) {
            return new Outerwear(in);
        }

        @Override
        public Outerwear[] newArray(int size) {
            return new Outerwear[size];
        }
    };


}
