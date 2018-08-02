package com.example.recouture.Calendar;


import com.example.recouture.Outfit.Outfit;
import com.example.recouture.utils.EventDecorator;

public class Event {

    private String mDate;
    private String mImageUrl;
    private Outfit outfit;



    public Event() {}


    public Event(String mDate, String mImageUrl,Outfit outfit) {
        this.mDate = mDate;
        this.mImageUrl = mImageUrl;
        this.outfit = outfit;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }


    public Outfit getOutfit() {
        return outfit;
    }
}
