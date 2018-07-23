package com.example.recouture.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recouture.R;

public class ViewPlanned extends AppCompatActivity {

    private TextView theDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_planned);
        theDate = (TextView) findViewById(R.id.date);
        Intent incomingIntent = getIntent();

        String date = incomingIntent.getStringExtra("date");
        theDate.setText(date);

    }
}
