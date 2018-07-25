package com.example.recouture.Outfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.recouture.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewOutfits extends AppCompatActivity {

    private static final String TAG = "View Outfits";

    private DatabaseReference mDatabaseReference;
    private ValueEventListener mDatabaseListener;
    private static final int NUM_GRID_COLUMNS = 4;
    private GridView gridView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private ArrayList<Outfit> outfits;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewoufits);
        gridView = (GridView)findViewById(R.id.gridview) ;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        outfits = new ArrayList<>();

        setupGridView();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "onClick: navigating to the click outfits.");
<<<<<<< HEAD
//                view.buildDrawingCache();
//                Bitmap bitmap = view.getDrawingCache();
=======
>>>>>>> e3e1250... ps help
                Outfit outfit = outfits.get(position);
                Intent intent = new Intent(ViewOutfits.this, Click_Outfit.class);
                intent.putExtra("viewing", outfit.getmImageUrl());
                intent.putExtra("name", outfit.getmName());
                startActivity(intent);
            }
        });
    }

    private void setupGridView() {
        Log.d(TAG, "setupGridView: Setting up image grid.");

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

                ArrayList<String> imgUrls = new ArrayList<String>();
                for (int i = 0; i < outfits.size(); i++) {
                    imgUrls.add(outfits.get(i).getmImageUrl());
                }
               // GridImageAdapter adapter = new GridImageAdapter(ViewOutfits.this, R.layout.layout_grid_imageview, "", imgUrls);
                OutfitAdapter oa = new OutfitAdapter (ViewOutfits.this, outfits);
                gridView.setAdapter(oa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled");
            }
        });


    }
}

