package com.example.recouture.Profile;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridView;
import java.util.ArrayList;

import com.example.recouture.Friends.AddFriend;
import com.example.recouture.Outfit.Click_Outfit;
import com.example.recouture.Outfit.Outfit;
import com.example.recouture.Outfit.ViewOutfits;
import com.example.recouture.Posts.ViewPost;
import com.example.recouture.utils.GridImageAdapter;
import com.example.recouture.utils.Post;
import com.example.recouture.utils.UniversalImageLoader;
import android.content.Context;
import android.widget.ImageView;


import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 4;
    private FragmentManager manager;
    private Context mContext = ProfileActivity.this;
    private ImageView profilePhoto;
    private ProgressBar mProgressBar;
    TextView editProfile;
    private ArrayList<Post> posts;
    private GridView gridView;
    private ImageView addbutton;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();
        setupActivityWidgets();

        setUpProfile();

        posts = new ArrayList<>();

        setProfileImage();
        addbutton = (ImageView) findViewById(R.id.addfriend);
        gridView = (GridView) findViewById(R.id.gridViewprofile) ;
        setupGridView();
        TextView editYourProfile = (TextView) findViewById(R.id.edityourprofile);
        manager = getFragmentManager();
        editYourProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to 'edit profile'");
                addFragmentView();
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to add friend");
                Intent i = new Intent(ProfileActivity.this, AddFriend.class);
                startActivity(i);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "onClick: navigating to the click outfits.");
                Post post = posts.get(position);
                Intent intent = new Intent(ProfileActivity.this, ViewPost.class);
                intent.putExtra("viewing", post.getImage_path());
                intent.putExtra("name", post.getPhoto_id());
                intent.putExtra("date", post.getDate_created());
                startActivity(intent);
            }
        });
    }

    private void setUpProfile() {

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

    private void setupGridView() {
        Log.d(TAG, "setupGridView: Setting up image grid.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Posts");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    posts.add(singleSnapshot.getValue(Post.class));
                }

                //setup image grid
                int gridWidth = getResources().getDisplayMetrics().widthPixels;
                int imageWidth = gridWidth / NUM_GRID_COLUMNS;
                gridView.setColumnWidth(imageWidth);

                ArrayList<String> imgUrls = new ArrayList<String>();
                for (int i = 0; i < posts.size(); i++) {
                    imgUrls.add(posts.get(i).getImage_path());
                }
                GridImageAdapter adapter = new GridImageAdapter(ProfileActivity.this, R.layout.layout_grid_imageview, "", imgUrls);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled");
            }
        });


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
