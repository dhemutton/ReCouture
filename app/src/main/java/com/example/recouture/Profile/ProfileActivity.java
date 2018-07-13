package com.example.recouture.Profile;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridView;
import java.util.ArrayList;
import com.example.recouture.utils.GridImageAdapter;
import com.example.recouture.utils.UniversalImageLoader;
import android.content.Context;
import android.widget.ImageView;


import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;
    private FragmentManager manager;
    private Context mContext = ProfileActivity.this;
    private ImageView profilePhoto;
    private ProgressBar mProgressBar;
    TextView editProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();
        setupActivityWidgets();
        setProfileImage();

        tempGridSetup();
        TextView editYourProfile = (TextView) findViewById(R.id.edityourprofile);
        manager = getFragmentManager();
        editYourProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to 'edit profile'");
                addFragmentView();
            }
        });
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg");
        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg");
         imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg");
                imgURLs.add("http://i.imgur.com/EwZRpvQ.jpg");
               imgURLs.add("http://i.imgur.com/JTb2pXP.jpg");
                imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg");
                imgURLs.add("https://i.redd.it/pwduhknig00z.jpg");
                imgURLs.add("https://i.redd.it/clusqsm4oxzy.jpg");
                imgURLs.add("https://i.redd.it/svqvn7xs420z.jpg");
                imgURLs.add("http://i.imgur.com/j4AfH6P.jpg");
                imgURLs.add("https://i.redd.it/89cjkojkl10z.jpg");
                imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg");
        setupImageGrid(imgURLs);
    }

    private void setupImageGrid(ArrayList<String> imgURLs){
        GridView gridView = (GridView) findViewById(R.id.gridView);
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs);
        gridView.setAdapter(adapter);
    }



    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile photo.");
        String imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UniversalImageLoader.setImage(imgURL, profilePhoto, mProgressBar, "https://");
    }

    private void setupActivityWidgets(){
        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);
        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
    }

    private void addFragmentView() {
        EditProfileFragment fragment = new EditProfileFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.editProfile,fragment);
        fragmentTransaction.commit();
    }


}
