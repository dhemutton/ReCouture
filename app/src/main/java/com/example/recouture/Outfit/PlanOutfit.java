package com.example.recouture.Outfit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.support.annotation.NonNull;

import android.util.Log;
import android.widget.ImageView;


import com.example.recouture.Calendar.ViewPlanned;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.recouture.R;
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.UniversalImageLoader;

public class PlanOutfit extends AppCompatActivity {

    private static final String TAG = "Plan outfit";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String mSelectedImage;


    //vars
    private String mAppend = "file:/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewoufits);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "onClick: navigating to the view planned outfits.");
                Intent intent = new Intent(getApplicationContext(), ConfirmPlan.class);
                intent.putExtra(getString(R.string.selected_image), id);
                startActivity(intent);
            }
        });

    }


}