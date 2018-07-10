package com.example.recouture.ShirtGallery;

import com.example.recouture.Item;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Shirt extends Item {



    public Shirt() {}


    public Shirt(String mName, String mColor, String mImageUrl) {
        super(mName,mColor,mImageUrl);
    }



}
