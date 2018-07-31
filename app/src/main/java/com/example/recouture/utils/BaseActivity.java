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

    protected BottomNavigationViewEx bottomNavigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBottomNavigationView(getApplicationContext());
    }

    public abstract int setView();


    protected void setupBottomNavigationView(Context context) {
        bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(context, bottomNavigationViewEx);
    }


}
