package com.example.recouture.Posts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.Profile.ProfileActivity;
import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.Heart;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ViewPost extends AppCompatActivity {

    private static final String TAG = "Click Post";
    private static final int ACTIVITY_NUM = 4;

    private String name;
    private String url;
    private String datecreated;
    private TextView mTimestamp;
    private ImageView mHeartRed,mHeartWhite;
    private GestureDetector mGestureDetector;
    private Heart mHeart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_post);
        setupBottomNavigationView();

        Log.d(TAG, "starting click");

        Intent i = getIntent();

        ImageView imageView = (ImageView) findViewById(R.id.post_image);
        TextView picName = (TextView) findViewById(R.id.image_caption);
        mHeartRed = (ImageView) findViewById(R.id.image_heart_red);
        mHeartWhite = (ImageView) findViewById(R.id.image_heart);

        mHeartRed.setVisibility(View.GONE);
        mHeartWhite.setVisibility(View.VISIBLE);
        mHeart = new Heart(mHeartWhite, mHeartRed);
        mGestureDetector = new GestureDetector(ViewPost.this, new GestureListener());

        name = i.getStringExtra("name");
        url = i.getStringExtra("viewing");
        datecreated = i.getStringExtra("date");

        Glide.with(this).load(url).into(imageView);
        mTimestamp = (TextView) findViewById(R.id.image_time_posted);

        setupWidgets();
        testToggle();
        Log.d(TAG, "name :" + url);

        picName.setText(name);


    }

    /**
     * Returns a string representing the number of days ago the post was made
     * @return
     */
    private String getTimestampDifference(){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss''", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = datecreated;
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }


    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ViewPost.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    private void setupWidgets(){
        String timestampDiff = getTimestampDifference();
        if(!timestampDiff.equals("0")){
            mTimestamp.setText(timestampDiff + " DAYS AGO");
        }else{
            mTimestamp.setText("TODAY");
        }
    }

    private void testToggle(){
        mHeartRed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: red heart touch detected.");

                return mGestureDetector.onTouchEvent(event);
            }
        });
        mHeartWhite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: white heart touch detected.");
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mHeart.toggleLike();
            return true;
        }
    }


}
