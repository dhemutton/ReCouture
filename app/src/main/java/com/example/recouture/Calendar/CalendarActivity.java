package com.example.recouture.Calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recouture.Outfit.PlanOutfit;
import com.example.recouture.R;
import com.example.recouture.utils.BaseActivity;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.EventDecorator;
import com.example.recouture.utils.FirebaseMethods;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.recouture.Calendar.CalendarUtils.FIREBASE_DATE_FORMATTER;
import static com.example.recouture.Calendar.CalendarUtils.FORMATTER;
import static com.example.recouture.Calendar.CalendarUtils.convertCalendarDayToDate;

public class CalendarActivity extends BaseActivity {
    private static final String TAG = "CalendarActivity";
    private static final int ACTIVITY_NUM = 1;
    private MaterialCalendarView mCalendarView;
    private TextView viewplanned;
    private TextView newplan;



    private DatabaseReference eventDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Events");

    private ValueEventListener valueEventListener = eventDatabaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                Event event = childSnapShot.getValue(Event.class);
                try {
                    Date date = FIREBASE_DATE_FORMATTER.parse(event.getmDate());
                    CalendarDay calendarDay = CalendarDay.from(date);
                    EventDecorator eventDecorator = new EventDecorator(calendarDay, Color.RED);
                    mCalendarView.addDecorator(eventDecorator);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //mCalendarView.addDecorator(event.getEventDecorator());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    private String date;

    private String textViewDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");

        mCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        viewplanned = (TextView) findViewById(R.id.viewoutfittext);
        newplan = (TextView) findViewById(R.id.plan);

        setMenuChecked();




        viewplanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = convertCalendarDayToDate(mCalendarView.getSelectedDate(),FIREBASE_DATE_FORMATTER);
                Intent intent = new Intent(CalendarActivity.this, ViewPlanned.class);
                textViewDate = convertCalendarDayToDate(mCalendarView.getSelectedDate(),FORMATTER);
                intent.putExtra("date", date);
                intent.putExtra("textViewDate",textViewDate);
                startActivity(intent);
            }
        });

        newplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(CalendarActivity.this, PlanOutfit.class);
//                    intent.putExtra("date", date);
//                    startActivity(intent);
                    CalendarDay day = mCalendarView.getSelectedDate();
                    if (day == null) {
                        Toast.makeText(CalendarActivity.this, "please select a date", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(CalendarActivity.this,PlanOutfit.class);
                        intent.putExtra("calendarDay",day);
                        startActivity(intent);
                    }
                }
        });
    }



    @Override
    public int setView() {
        return R.layout.layout_calendar;
    }

    private void setMenuChecked(){
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
