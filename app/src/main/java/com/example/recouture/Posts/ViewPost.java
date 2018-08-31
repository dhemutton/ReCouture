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
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.Heart;
import com.example.recouture.utils.Like;
import com.example.recouture.utils.Post;
import com.example.recouture.utils.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
    private String displayName;

    private TextView mTimestamp, mLikes, userTop, userBot;
    private ImageView mHeartRed,mHeartWhite;
    private GestureDetector mGestureDetector;
    private Heart mHeart;
    private Post mPost;
    private Boolean mLikedByCurrentUser;
    private StringBuilder mUsers;
    private String mLikesString = "";



    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_post);
        setupBottomNavigationView();

        Log.d(TAG, "starting click");

        Intent i = getIntent();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        ImageView imageView = (ImageView) findViewById(R.id.post_image);
        TextView picName = (TextView) findViewById(R.id.image_caption);
        mLikes = (TextView) findViewById(R.id.image_likes);
        mHeartRed = (ImageView) findViewById(R.id.image_heart_red);
        mHeartWhite = (ImageView) findViewById(R.id.image_heart);

        mHeart = new Heart(mHeartWhite, mHeartRed);
        mGestureDetector = new GestureDetector(ViewPost.this, new GestureListener());

        name = i.getStringExtra("name");
        url = i.getStringExtra("viewing");
        datecreated = i.getStringExtra("date");
        displayName = i.getStringExtra("displayName");

        mPost = (Post) i.getParcelableExtra("post");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userBot = (TextView) findViewById(R.id.user);
        userTop = (TextView) findViewById(R.id.username);

        Glide.with(this).load(url).into(imageView);
        mTimestamp = (TextView) findViewById(R.id.image_time_posted);
        getLikesString();

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
        userTop.setText(displayName);
        userBot.setText(displayName);
        mLikes.setText(mLikesString);

        if(mLikedByCurrentUser){
            mHeartWhite.setVisibility(View.GONE);
            mHeartRed.setVisibility(View.VISIBLE);
            mHeartRed.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d(TAG, "onTouch: red heart touch detected.");
                    return mGestureDetector.onTouchEvent(event);
                }
            });
        }
        else{
            mHeartWhite.setVisibility(View.VISIBLE);
            mHeartRed.setVisibility(View.GONE);
            mHeartWhite.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d(TAG, "onTouch: white heart touch detected.");
                    return mGestureDetector.onTouchEvent(event);
                }
            });
        }
    }


    private void getLikesString(){
        Log.d(TAG, "getLikesString: getting likes string");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Posts")
                .child(mPost.getPhoto_id())
                .child("Likes");                  //find user ids of those who liked the post
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUsers = new StringBuilder();     //append here
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query query = reference
                            .equalTo(singleSnapshot.getValue(Like.class).getUser_id());    //finding the user through user id
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                                Log.d(TAG, "onDataChange: found like: " +
                                        singleSnapshot.getValue(User.class).getDisplayname());   //found user through user id, getting displayname

                                mUsers.append(singleSnapshot.getValue(User.class).getDisplayname());
                                mUsers.append(",");
                            }

                            String[] splitUsers = mUsers.toString().split(",");

                            if(mUsers.toString().contains(firebaseUser.getUid())){
                                mLikedByCurrentUser = true;
                            }else{
                                mLikedByCurrentUser = false;
                            }

                            int length = splitUsers.length;
                            if(length == 1){
                                mLikesString = "Liked by " + splitUsers[0];

                            }
                            else if(length == 2){
                                mLikesString = "Liked by " + splitUsers[0]
                                        + " and " + splitUsers[1];
                            }
                            else if(length == 3){
                                mLikesString = "Liked by " + splitUsers[0]
                                        + ", " + splitUsers[1]
                                        + " and " + splitUsers[2];
                            }
                            else if(length > 3){
                                mLikesString = "Liked by " + splitUsers[0]
                                        + ", " + splitUsers[1]
                                        + ", " + splitUsers[2]
                                        + " and " + (splitUsers.length - 3) + " others";
                            }
                            setupWidgets();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                if(!dataSnapshot.exists()){
                    mLikesString = "";
                    mLikedByCurrentUser = false;
                    setupWidgets();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            Log.d(TAG, "onDoubleTap: double tap detected.");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("Posts")
                    .child(mPost.getPhoto_id())
                    .child("Likes");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                        String keyID = singleSnapshot.getKey();

                        //case1: Then user already liked the photo
                        if(mLikedByCurrentUser &&
                                singleSnapshot.getValue(Like.class).getUser_id()
                                        .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Posts")
                                    .child(mPost.getPhoto_id())
                                    .child(getString(R.string.field_likes))
                                    .child(keyID)
                                    .removeValue();

                            mHeart.toggleLike();
                            getLikesString();
                        }


                        //case2: The user has not liked the photo
                        else if(!mLikedByCurrentUser){
                            //add new like
                            addNewLike();
                            break;
                        }
                    }

                    if(!dataSnapshot.exists()){
                        //add new like
                        addNewLike();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return true;
        }
    }

    private void addNewLike(){
        Log.d(TAG, "addNewLike: adding new like");

        String newLikeID = myRef.push().getKey();
        Like like = new Like();
        like.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Posts")
                .child(mPost.getPhoto_id())
                .child(getString(R.string.field_likes))
                .setValue(like);

        mHeart.toggleLike();
        getLikesString();
    }


}
