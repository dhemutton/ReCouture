package com.example.recouture.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.recouture.R;
import com.example.recouture.ShirtGallery.ShirtActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Base abstract activity instantiates common UI between similar activites such as the bottom
     * navigation tool bar.
     */

    // custom bottom navigation toolbar.
    protected BottomNavigationViewEx bottomNavigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBottomNavigationView(getApplicationContext());
    }


    /**
     * abstract method that initializes each layout for the different activities.
     * @return resourceId for the layout.
     */
    public abstract int setView();


    /**
     * Sets up bottom navigation view for all the activities with this navigation toolbar.
     * @param context The context of the activity that the app is in.
     */
    protected void setupBottomNavigationView(Context context) {
        bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(context, bottomNavigationViewEx);
    }


}
