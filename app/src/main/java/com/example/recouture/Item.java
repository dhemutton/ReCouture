package com.example.recouture;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Item implements Parcelable {


    public static final String CATEGORY = "Item";


    private String mName;

    private String mColor;

    private String mImageUrl;

    private List<TagHolder> tags = new ArrayList<>();

    private String mKey;




    public Item(){

    }



    public Item(String mName, String mColor, String mImageUrl) {
        this.mName = mName;
        this.mColor = mColor;
        this.mImageUrl = mImageUrl;
    }


    protected Item(Parcel in) {
        mName = in.readString();
        mColor = in.readString();
        mImageUrl = in.readString();
        tags = in.createTypedArrayList(TagHolder.CREATOR);
        mKey = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mColor);
        dest.writeString(mImageUrl);
        dest.writeTypedList(tags);
        dest.writeString(mKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public List<TagHolder> getTags() {
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

    public void setTags(List<TagHolder> tags) {
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


    public String getCategory() {
        return "Item";
    }




}
