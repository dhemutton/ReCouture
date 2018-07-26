package com.example.recouture.Outfit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.recouture.Calendar.CalendarActivity;
import com.example.recouture.R;
<<<<<<< HEAD
=======
import com.example.recouture.ShirtGallery.EmptyRecyclerView;
import com.example.recouture.ShirtGallery.ShirtAdapter2;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.FirebaseMethods;
>>>>>>> b23c28ae23c0b91cadc48cd9098fc40e90551661
import com.example.recouture.utils.GridImageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class PlanOutfit extends AppCompatActivity {

    private static final String TAG = "View Outfits";

    private DatabaseReference mDatabaseReference;
    private ValueEventListener mDatabaseListener;
    private static final int NUM_GRID_COLUMNS = 4;
    private static final int ACTIVITY_NUM = 1;

    private GridView gridView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private String mSelectedImage;
    private ArrayList<String> imgUrls;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewoufits);
        setupBottomNavigationView();

        gridView = (GridView)findViewById(R.id.gridview) ;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        date = getIntent().getStringExtra("date");

        setupGridView();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "onClick: navigating to the view planned outfits.");
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                Intent intent = new Intent(PlanOutfit.this, ConfirmPlan.class);
                intent.putExtra("planning", bitmap);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }

    private void setupGridView() {
        Log.d(TAG, "setupGridView: Setting up image grid.");

        final ArrayList<Outfit> outfits = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Outfits");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    outfits.add(singleSnapshot.getValue(Outfit.class));
                }

                //setup image grid
                int gridWidth = getResources().getDisplayMetrics().widthPixels;
                int imageWidth = gridWidth / NUM_GRID_COLUMNS;
                gridView.setColumnWidth(imageWidth);

                imgUrls = new ArrayList<String>();
                for (int i = 0; i < outfits.size(); i++) {
                    imgUrls.add(outfits.get(i).getmImageUrl());
                }
                GridImageAdapter adapter = new GridImageAdapter(PlanOutfit.this, R.layout.layout_grid_imageview, "", imgUrls);
                gridView.setAdapter(adapter);
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
        BottomNavigationViewHelper.enableNavigation(PlanOutfit.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}

