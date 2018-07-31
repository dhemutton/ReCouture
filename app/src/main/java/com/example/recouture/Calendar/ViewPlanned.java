package com.example.recouture.Calendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.Outfit.Outfit;
import com.example.recouture.Outfit.ViewOutfits;
import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.GridImageAdapter;
import com.example.recouture.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ViewPlanned extends AppCompatActivity {

    private TextView theDate;
    private Intent intent;
    private String imgUrl;
    private String mAppend = "file:/";
    private static final String TAG = "ViewPlanned";
    private static final int ACTIVITY_NUM = 1;

    private String wantedDate;
    private String suppliedUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_planned);
        setupBottomNavigationView();
        theDate = (TextView) findViewById(R.id.date);
        Intent incomingIntent = getIntent();

        wantedDate = incomingIntent.getStringExtra("date");
        theDate.setText(wantedDate);
        checkEvent();
    }

    private void setImage() {
        ImageView image = (ImageView) findViewById(R.id.imageShare);

        if (suppliedUri != null) {
            Glide.with(this).load(suppliedUri).into(image);
        }
    }

    private void checkEvent() {
        Log.d(TAG, "checking for event existence");

        final ArrayList<Event> events = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Events");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Log.i(TAG,singleSnapshot.toString());
                    events.add(singleSnapshot.getValue(Event.class));
                }

                for (int i = 0; i < events.size(); i++) {
                    if (events.get(i).getmDate().equals(wantedDate)) {
                        suppliedUri = events.get(i).getmImageUrl();
                        setImage();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled");
            }
        });
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ViewPlanned.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
