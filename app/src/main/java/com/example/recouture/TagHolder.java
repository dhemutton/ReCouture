package com.example.recouture;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class TagHolder {

    private String name;
    private String downloadImageUri;
    private String mKey;
    private String tagName;

    public TagHolder() {}


    public TagHolder(String name, String  downloadImageUri,String tagName) {
        this.name = name;
        this.downloadImageUri = downloadImageUri;
        this.tagName = tagName;
    }

    public String getDownloadImageUri() {
        return downloadImageUri;
    }

    public String getName() {
        return name;
    }


    //@Exclude // dont need this in our firebase database
    public String getmKey() {
        return mKey;
    }

    //@Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    @Override
    public String toString() {
        return "name : " + name;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
