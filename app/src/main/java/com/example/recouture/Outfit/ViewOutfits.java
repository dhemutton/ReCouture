package com.example.recouture.Outfit;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.R;
import com.example.recouture.ShirtGallery.ShirtAdapter2;
import com.example.recouture.utils.BaseGalleryActivity;
import com.example.recouture.utils.FirebaseMethods;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//public class ViewOutfits extends BaseGalleryActivity<Outfit> {
//
//    private DatabaseReference mDatabaseReference;
//    private ValueEventListener mDatabaseListener;
//    private DatabaseReference mDatabaseTagRef;
//    private ImageView delete;
//    private ShirtAdapter2 shirtAdapter2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        changeHeader("View Outfits");
//
//        cancelDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                helperCancelDelete(shirtAdapter2);
//            }
//        });
//
//        delete = findViewById(R.id.delete);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseMethods.deleteGalleryImages(toBeDeleted, mDatabaseReference, mDatabaseTagRef);
//            }
//        });
//
//        setUpRecyclerView(this);
//
//
//
//        shirtAdapter2 = new ShirtAdapter2(this);
//        mRecyclerViewShirt.setAdapter(shirtAdapter2);
//        shirtAdapter2.setListener(this);
//
//
//
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Outfit");
//
//        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid() + "/Tags/");
//
//
//        mDatabaseListener = FirebaseMethods.returnGalleryListener(mDatabaseReference, Outfit.class, shirtAdapter2);
//    }
//
//
//    @Override
//    public void changeHeader(String headerTitle) {
//        TextView textView =  findViewById(R.id.ProfileHeader);
//        textView.setText(headerTitle);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mDatabaseReference.removeEventListener(mDatabaseListener);
//    }
//}
