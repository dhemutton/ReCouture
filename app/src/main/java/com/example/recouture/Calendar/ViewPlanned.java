package com.example.recouture.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.R;
import com.example.recouture.utils.UniversalImageLoader;

public class ViewPlanned extends AppCompatActivity {

    private TextView theDate;
    private Intent intent;
    private String imgUrl;
    private String mAppend = "file:/";
    private static final String TAG = "ViewPlanned";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_planned);
        theDate = (TextView) findViewById(R.id.date);
        Intent incomingIntent = getIntent();

        String date = incomingIntent.getStringExtra("date");
        theDate.setText(date);
        setImage();

    }

    private void setImage() {
        intent = getIntent();
        ImageView image = (ImageView) findViewById(R.id.imageShare);

        if (intent.hasExtra(getString(R.string.selected_image))) {
            imgUrl = intent.getStringExtra(getString(R.string.selected_image));
            Log.d(TAG, "setImage: got new image url: " + imgUrl);
            UniversalImageLoader.setImage(imgUrl, image, null, mAppend);
        }
    }
}
