
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
import com.example.recouture.utils.BaseViewHolder;
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

public class ShirtActivity extends BaseGalleryActivity<Shirt> {

    //private EmptyRecyclerView mRecyclerViewShirt;

    private DatabaseReference mDatabaseReference;
   // private BottomNavigationViewEx bottomNavigationViewEx;

    private ValueEventListener mDatabaseListener;


    // to delete tag of shirt
    private DatabaseReference mDatabaseTagRef;


    // cancel dustbin


    // delete items
    private ImageView delete;

    private ShirtAdapter2 shirtAdapter2;


    // to delete image from storage ref


    /*
    how to extract the layout? everything is the same except the header. Set content view for
    each activity use the same but change profile header title.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeHeader("Shirts");

        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperCancelDelete(shirtAdapter2);
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMethods.deleteGalleryImages(toBeDeleted, mDatabaseReference, mDatabaseTagRef);
            }
        });

        setUpRecyclerView(this);



        shirtAdapter2 = new ShirtAdapter2(this);
        mRecyclerViewShirt.setAdapter(shirtAdapter2);
        shirtAdapter2.setListener(this);



        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Shirts");

        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid() + "/Tags/");


        mDatabaseListener = FirebaseMethods.returnGalleryListener(mDatabaseReference, Shirt.class, shirtAdapter2);
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
}
