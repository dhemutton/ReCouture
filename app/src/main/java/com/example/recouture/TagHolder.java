package com.example.recouture;

import android.net.Uri;

public class TagHolder {

    private String name;
    private String downloadImageUri;

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


}
