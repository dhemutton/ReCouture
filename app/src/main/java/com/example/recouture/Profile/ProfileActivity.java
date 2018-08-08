package com.example.recouture.Profile;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.example.recouture.Calendar.Event;
import com.example.recouture.Friends.AddFriend;
import com.example.recouture.Outfit.Click_Outfit;
import com.example.recouture.Outfit.Outfit;
import com.example.recouture.Outfit.ViewOutfits;
import com.example.recouture.Posts.ViewPost;
import com.example.recouture.utils.BaseActivity;
import com.example.recouture.utils.CommonUi;
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.GridImageAdapter;
import com.example.recouture.utils.Post;
import com.example.recouture.utils.UniversalImageLoader;
import android.content.Context;
import android.widget.ImageView;


import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends BaseActivity {
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
        setContentView(R.layout.activity_profile);
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        setMenuChecked();
        setupActivityWidgets();

        setUpProfile();

        posts = new ArrayList<>();

        //setProfileImage();
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

    @Override
    public int setView() {
        return R.layout.activity_profile;
    }

    private void setUpProfile() {
        TextView displayName = findViewById(R.id.display_name);
        TextView description = findViewById(R.id.description);
        TextView website = findViewById(R.id.website);
        CircleImageView profilePhoto = findViewById(R.id.profile_photo);

        ImageLoader imageLoader = ImageLoader.getInstance();

        DatabaseReference databaseReference = new FirebaseMethods(this).getMyRef();
        Log.i(TAG,databaseReference.toString());

        databaseReference.child("UserData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, dataSnapshot.toString());
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.i(TAG,dataSnapshot1.toString());
                }
                User user = dataSnapshot.getValue(User.class);
                Log.i(TAG, user.toString());
                displayName.setText(user.getDisplayname());
                description.setText(user.getDescription());
                website.setText(user.getWebsite());
                String uri = user.getImage_path();
                UniversalImageLoader.setImage(uri, profilePhoto, mProgressBar, "");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void setMenuChecked(){
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
                ArrayList<String> imageUri = CommonUi.getPostUri(posts);
                CommonUi.setGridView(ProfileActivity.this,gridView,imageUri);
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
