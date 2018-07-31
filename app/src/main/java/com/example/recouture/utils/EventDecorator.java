package com.example.recouture.utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

public class EventDecorator implements DayViewDecorator {

    private CalendarDay date;
    private int color;


    public EventDecorator(CalendarDay day,int color) {
        this.date = day;
        this.color = color;
    }



    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        return date != null && calendarDay.equals(date);
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.addSpan(new DotSpan(5, color));
    }

}
