package com.example.recouture.Outfit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import com.example.recouture.utils.MultiTouchListener;

import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class CreateOutfit extends AppCompatActivity {

    /*
    when click plus button will cause the activity to go to homepage activity -> homepage activity
    will get Intent() which sets the flag for create outfit to be true. If true it will pass
    the listener to the image adapter. Then define on activity result in homepage activity, since flag
    is true for onClick items in the homepage adapter, we call startActivityForResult, override finish()
    in baseGalleryActivity to pass the list of items selected back to the calling activity in homepage.
    There will be a done on the homepage to indicate to pass the arrayList of items to the create Outfits. ->
    using intent and parceable we pass the data to this activity. 
     */

    private static final String TAG = "CreateOutfit";
    private static final int ACTIVITY_NUM = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forming_outfit);
        Log.d(TAG, "onCreate: started");

        findViewById(R.id.collageBgView).setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return true;
            }
        });

        findViewById(R.id.collageView1).setOnTouchListener(new MultiTouchListener());
        findViewById(R.id.collageView2).setOnTouchListener(new MultiTouchListener());
        findViewById(R.id.collageView3).setOnTouchListener(new MultiTouchListener());
        findViewById(R.id.collageView4).setOnTouchListener(new MultiTouchListener());
    }


}
