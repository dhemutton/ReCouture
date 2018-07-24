package com.example.recouture.SleevelessGallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ClickShirt extends AppCompatActivity {




    private ImageView PostImage;
    private TextView PostDescription;
    private Button DeletePostButton, EditPostButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_shirt_activity);

        PostImage = (ImageView) findViewById(R.id.post_image);
        DeletePostButton = (Button) findViewById(R.id.click_deletePost);

    }


    private void setupBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ClickShirt.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
    }





}
