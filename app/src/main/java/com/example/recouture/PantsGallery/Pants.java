package com.example.recouture.PantsGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Pants extends Item {


    public static final String CATEGORY = "Pants";

    public Pants() {}


    public Pants(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Pants(Parcel in) {
        super(in);
    }

    public static final Creator<Pants> CREATOR = new Creator<Pants>() {
        @Override
        public Pants createFromParcel(Parcel in) {
            return new Pants(in);
        }

        @Override
        public Pants[] newArray(int size) {
            return new Pants[size];
        }
    };


}
