package com.example.recouture.SleevelessGallery;

import android.os.Parcel;

import com.example.recouture.Item;

public class Sleeveless extends Item {


    public static final String CATEGORY = "Shoes";

    public Sleeveless() {}


    public Sleeveless(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    protected Sleeveless(Parcel in) {
        super(in);
    }

    public static final Creator<Sleeveless> CREATOR = new Creator<Sleeveless>() {
        @Override
        public Sleeveless createFromParcel(Parcel in) {
            return new Sleeveless(in);
        }

        @Override
        public Sleeveless[] newArray(int size) {
            return new Sleeveless[size];
        }
    };


}
