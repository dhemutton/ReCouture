package com.example.recouture.Calendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.Outfit.Outfit;
import com.example.recouture.Outfit.ViewOutfits;
import com.example.recouture.R;
import com.example.recouture.utils.BaseActivity;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.GridImageAdapter;
import com.example.recouture.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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

public class ViewPlanned extends BaseActivity {

    private TextView theDate;
    private Intent intent;
    private String imgUrl;
    private String mAppend = "file:/";
    private static final String TAG = "ViewPlanned";


    private String wantedDate;
    private String suppliedUri;

    private String firebaseDate;

    private TextView deleteOutfit;

    private ImageView backArrow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);

        setUpWidets();

        FirebaseMethods firebaseMethods = new FirebaseMethods(this);

        Intent incomingIntent = getIntent();

        firebaseDate = incomingIntent.getStringExtra("date");

        wantedDate = incomingIntent.getStringExtra("textViewDate");
        theDate.setText(wantedDate);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPlanned.super.onBackPressed();
            }
        });

        Log.i(TAG,"date " + firebaseDate);

        deleteOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"onClick");
                deleteOutfit(firebaseMethods.getMyRef(),firebaseDate);
                startActivity(new Intent(ViewPlanned.this,CalendarActivity.class));
            }
        });
        checkEvent();
    }

    private void deleteOutfit(DatabaseReference databaseReference,String firebaseDate) {
        databaseReference.child("Events").child(firebaseDate).removeValue();
    }


    public void setUpWidets() {
        theDate = (TextView) findViewById(R.id.date);
        deleteOutfit = findViewById(R.id.deleteOutfit);
        backArrow = findViewById(R.id.ivBackArrow);

    }

    @Override
    public int setView() {
        return R.layout.activity_view_planned;
    }

    private void setImage() {
        ImageView image = (ImageView) findViewById(R.id.imageShare);
        Log.i(TAG,suppliedUri.toString());
        if (suppliedUri != null) {

            Glide.with(this).load(suppliedUri).into(image);
        }
    }

    private void checkEvent() {
        Log.d(TAG, "checking for event existence");

        final ArrayList<Event> events = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child(FirebaseMethods.getUserUid()).child("Events").
                child(firebaseDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    boolean isNull = event == null;
                    Log.i(TAG, "is null" + isNull);
                    Log.i(TAG, "uri " + event.getmImageUrl());
                    Log.i(TAG, "date " + event.getmDate());
                    suppliedUri = event.getmImageUrl();
                    setImage();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
