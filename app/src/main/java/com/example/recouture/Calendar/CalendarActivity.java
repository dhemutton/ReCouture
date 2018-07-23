package com.example.recouture.Calendar;

import android.content.Intent;
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

import com.example.recouture.Outfit.PlanOutfit;
import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private static final int ACTIVITY_NUM = 1;
    private CalendarView mCalendarView;
    private TextView viewplanned;
    private TextView newplan;

    private String date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendar);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        viewplanned = (TextView) findViewById(R.id.viewoutfittext);
        newplan = (TextView) findViewById(R.id.plan);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = (dayOfMonth) + "/" + (month+1) + "/" + year;
                Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy : " + date);
            }
        });


        viewplanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, ViewPlanned.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });


        newplan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CalendarActivity.this, PlanOutfit.class);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }

        });



    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(CalendarActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
