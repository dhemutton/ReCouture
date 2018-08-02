package com.example.recouture.Calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtils {


    public static final SimpleDateFormat FIREBASE_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");


    public static String convertCalendarDayToDate(CalendarDay calendarDay,SimpleDateFormat simpleDateFormat) {
        Date date = calendarDay.getDate();
        return simpleDateFormat.format(date);
    }
}
