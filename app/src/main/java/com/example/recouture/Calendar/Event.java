package com.example.recouture.Calendar;


public class Event {
    private String mDate;
    private String mImageUrl;


    public Event() {}


    public Event(String mDate, String mImageUrl) {
        this.mDate = mDate;
        this.mImageUrl = mImageUrl;

    }
    public String getmDate() {
        return mDate;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }


}
