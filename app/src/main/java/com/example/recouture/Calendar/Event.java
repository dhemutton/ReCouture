package com.example.recouture.Calendar;


import com.example.recouture.utils.EventDecorator;

public class Event {

    private EventDecorator eventDecorator;
    private String mDate;
    private String mImageUrl;


    public Event() {}


    public Event(String mDate, String mImageUrl) {
        this.mDate = mDate;
        this.mImageUrl = mImageUrl;
        this.eventDecorator = eventDecorator;
    }


    public String getmDate() {
        return mDate;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }


    public EventDecorator getEventDecorator() {
        return eventDecorator;
    }

    public void setEventDecorator(EventDecorator eventDecorator) {
        this.eventDecorator = eventDecorator;
    }
}
