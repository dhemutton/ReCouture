
package com.example.recouture.ShirtGallery;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recouture.utils.BaseGalleryActivity;
import com.example.recouture.R;
import com.example.recouture.TagHolder;
import com.example.recouture.utils.FirebaseMethods;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ShirtActivity extends BaseGalleryActivity {

    //private EmptyRecyclerView mRecyclerViewShirt;
    private DatabaseReference ClickPostRef;
    private FirebaseAuth mAuth;
    private String PostKey, currentUserID, databaseUserID, description, image;
    private DatabaseReference mDatabaseReference;
   // private BottomNavigationViewEx bottomNavigationViewEx;

    private FirebaseUser firebaseUser;

    private List<Shirt> mShirtList;

    private StorageReference mStorageReference;

    private ValueEventListener mDatabaseListener;

    private ShirtAdapter shirtAdapter;

    // to delete tag of shirt
    private DatabaseReference mDatabaseTagRef;


    // cancel dustbin
    private TextView cancelDelete;

    // delete items
    private ImageView delete;


    // to delete image from storage ref
    private String FIREBASE_IMAGE_STORAGE = "photos/users";


    /*
    how to extract the layout? everything is the same except the header. Set content view for
    each activity use the same but change profile header title.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeHeader("Shirts");
        //setContentView(setView());
        final RelativeLayout deleteNavBar = findViewById(R.id.deleteNavBar);
        cancelDelete = findViewById(R.id.canceldel);
        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNavBar.setVisibility(View.INVISIBLE);
                bottomNavigationViewEx.setVisibility(View.VISIBLE);
                shirtAdapter.clearDeletables();
                shirtAdapter.setDeletable(false);
                shirtAdapter.cancelSelection(true);
                shirtAdapter.notifyDataSetChanged();
            }
        });


        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Shirt> deletables = shirtAdapter.returnDeletables();
                for (final Shirt shirt : deletables) {
                    final String selectedKey = shirt.getKey();
                    StorageReference shirtStorageReference = mStorageReference.child("Shirts");
                    StorageReference imageRef = FirebaseStorage.getInstance()
                            .getReferenceFromUrl(shirt.getmImageUrl());
                    List<TagHolder> tags = shirt.getTags();
                    for (TagHolder tagHolder : tags) {
                        String tagName = tagHolder.getTagName();
                        final DatabaseReference tagRef = mDatabaseTagRef.child(tagName);
                        String uniqueKey = tagHolder.getmKey();
                        if (uniqueKey != null) {
                            Log.i("ShirtActivity", uniqueKey);
                        }
                        tagRef.child(uniqueKey).removeValue();
                    }
                    imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDatabaseReference.child(selectedKey).removeValue();
                        }
                    });
                }
            }
        });



        deleteNavBar.setVisibility(View.INVISIBLE);

        TextView selectButton = (TextView) findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               bottomNavigationViewEx.setVisibility(View.INVISIBLE);
               deleteNavBar.setVisibility(View.VISIBLE);
               shirtAdapter.setDeletable(true);
            }
        });

        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShirtActivity.super.onBackPressed();
            }
        });

        setUpRecyclerView(this);
        
        //mRecyclerViewShirt = findViewById(R.id.recyclerViewShirt);

//        mRecyclerViewShirt.setHasFixedSize(true);
//        mRecyclerViewShirt.setLayoutManager(new GridLayoutManager(ShirtActivity.this,3));
//        mRecyclerViewShirt.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.HORIZONTAL));
//        mRecyclerViewShirt.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));

        // need to set adapter


        mShirtList = new ArrayList<>();


        shirtAdapter = new ShirtAdapter(this,mShirtList);


        mRecyclerViewShirt.setAdapter(shirtAdapter);

        mStorageReference = FirebaseStorage.getInstance().getReference(FirebaseMethods.getUserUid());

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Shirts");

        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid() + "/Tags/");



        /*
        public<T extends Item> ValueEventListener returnListener(DatabaseReference databaseRef, List<? extends Item>)

         */

        mDatabaseListener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mShirtList.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Shirt shirt = postSnapShot.getValue(Shirt.class);
                    shirt.setMkey(postSnapShot.getKey());
                    mShirtList.add(shirt);
                    Log.i("ShirtActivity","listner");
                }
                shirtAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void changeHeader(String headerTitle) {
        TextView textView =  findViewById(R.id.ProfileHeader);
        textView.setText(headerTitle);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mDatabaseListener);
    }


//    private void setupBottomNavigationView() {
//        bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(ShirtActivity.this, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//    }
}
