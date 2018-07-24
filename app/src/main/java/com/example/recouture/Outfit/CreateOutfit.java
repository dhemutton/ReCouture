package com.example.recouture.Outfit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recouture.Calendar.ViewPlanned;
import com.example.recouture.HomePage.HomepageActivity;
import com.example.recouture.utils.MultiTouchListener;

import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


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
    private ImageView addMore;
    private TextView done;
    private ImageView imageView;
    private RelativeLayout rootContent;
    private RelativeLayout toMakeInvi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forming_outfit);
        Log.d(TAG, "onCreate: started");
        addMore = (ImageView) findViewById(R.id.addmore);
        done = (TextView) findViewById(R.id.donetext);
        imageView = (ImageView) findViewById(R.id.image_view);
        rootContent = (RelativeLayout) findViewById(R.id.root_content);
        toMakeInvi = (RelativeLayout) findViewById(R.id.relLayout1);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateOutfit.this, HomepageActivity.class);
                Log.d(TAG, "going to homepage to select clothes");
                startActivity(intent);
            }
        });



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


    private void takeScreenshot() {
        Log.d(TAG, "attempting screenshot");
        toMakeInvi.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        Bitmap b = null;
        b = ScreenshotUtils.getScreenShot(rootContent);
        showScreenShotImage(b);//show bitmap over imageview

    }

    private void showScreenShotImage(Bitmap b) {
        imageView.setImageBitmap(b);
        imageView.setVisibility(View.VISIBLE);
    }
    
}
