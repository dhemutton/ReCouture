package com.example.recouture.Outfit;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.recouture.Item;

import java.util.ArrayList;
import java.util.List;


public class Outfit implements Parcelable{

    private List<Item> itemList;
    private String mName;
    private String mImageUrl;


    public Outfit() {}


    public Outfit(String mName, String mImageUrl,List<Item> items) {
        this.mName = mName;
        this.mImageUrl = mImageUrl;
        this.itemList = items;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    protected Outfit(Parcel in) {
        in.readList(itemList,Item.class.getClassLoader());
        mName = in.readString();
        mImageUrl = in.readString();
    }

    public static final Creator<Outfit> CREATOR = new Creator<Outfit>() {
        @Override
        public Outfit createFromParcel(Parcel in) {
            return new Outfit(in);
        }

        @Override
        public Outfit[] newArray(int size) {
            return new Outfit[size];
        }
    };

    public String getmName() {
        return mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mName + " ");
        for (Item item : itemList) {
            stringBuilder.append(item.toString() + " ");
        }
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(itemList);
        parcel.writeString(mName);
        parcel.writeString(mImageUrl);
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
