package com.example.recouture.ShirtGallery;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Shirt {

    private String mName;
    private String mColor;
    private String mImageUrl;
    private List<String> tags = new ArrayList<>();
    private String mKey;

    public Shirt() {}


    public Shirt(String mName, String mColor, String mImageUrl) {
        this.mName = mName;
        this.mColor = mColor;
        this.mImageUrl = mImageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getmColor() {
        return mColor;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }

    public String getmName() {
        return mName;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Exclude // dont need this in our firebase database
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setMkey(String key) {
        mKey = key;
    }

}
