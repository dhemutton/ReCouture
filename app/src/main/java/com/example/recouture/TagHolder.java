package com.example.recouture;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

public class TagHolder implements Parcelable{

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

    protected TagHolder(Parcel in) {
        name = in.readString();
        downloadImageUri = in.readString();
        mKey = in.readString();
        tagName = in.readString();
    }

    public static final Creator<TagHolder> CREATOR = new Creator<TagHolder>() {
        @Override
        public TagHolder createFromParcel(Parcel in) {
            return new TagHolder(in);
        }

        @Override
        public TagHolder[] newArray(int size) {
            return new TagHolder[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(downloadImageUri);
        parcel.writeString(mKey);
        parcel.writeString(tagName);
    }
}
