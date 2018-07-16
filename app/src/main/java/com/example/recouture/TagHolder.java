package com.example.recouture;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class TagHolder {

    private String name;
    private String downloadImageUri;
    private String mKey;

    public TagHolder() {}


    public TagHolder(String name, String  downloadImageUri) {
        this.name = name;
        this.downloadImageUri = downloadImageUri;
    }

    public String getDownloadImageUri() {
        return downloadImageUri;
    }

    public String getName() {
        return name;
    }


    @Exclude // dont need this in our firebase database
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

}
