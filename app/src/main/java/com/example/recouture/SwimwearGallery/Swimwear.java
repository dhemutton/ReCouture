package com.example.recouture.SwimwearGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Swimwear extends Item {


    public static final String CATEGORY = "Swimwear";

    public Swimwear() {}


    public Swimwear(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Swimwear(Parcel in) {
        super(in);
    }

    public static final Creator<Swimwear> CREATOR = new Creator<Swimwear>() {
        @Override
        public Swimwear createFromParcel(Parcel in) {
            return new Swimwear(in);
        }

        @Override
        public Swimwear[] newArray(int size) {
            return new Swimwear[size];
        }
    };


}
