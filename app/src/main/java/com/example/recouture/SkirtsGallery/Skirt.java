package com.example.recouture.SkirtsGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Skirt extends Item {


    public static final String CATEGORY = "Shoes";

    public Skirt() {}


    public Skirt(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Skirt(Parcel in) {
        super(in);
    }

    public static final Creator<Skirt> CREATOR = new Creator<Skirt>() {
        @Override
        public Skirt createFromParcel(Parcel in) {
            return new Skirt(in);
        }

        @Override
        public Skirt[] newArray(int size) {
            return new Skirt[size];
        }
    };


}
