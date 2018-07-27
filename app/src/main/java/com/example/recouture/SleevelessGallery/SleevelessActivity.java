
package com.example.recouture.SleevelessGallery;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.R;
import com.example.recouture.utils.BaseGalleryActivity;
import com.example.recouture.utils.EmptyRecyclerView;
import com.example.recouture.utils.FirebaseMethods;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SleevelessActivity extends BaseGalleryActivity<Sleeveless> {

    //private EmptyRecyclerView mRecyclerViewSleeveless;


    // cancel dustbin


    // delete items
    private ImageView delete;

    private SleevelessAdapter SleevelessAdapter;


    // to delete image from storage ref


    /*
    how to extract the layout? everything is the same except the header. Set content view for
    each activity use the same but change profile header title.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeHeader("Sleevelesss");
        //changeEmptyViewText(mRecyclerView);

        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperCancelDelete(SleevelessAdapter);
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMethods.deleteGalleryImages(toBeDeletedOrChosen, mDatabaseReference, mDatabaseTagRef,
                        SleevelessActivity.this);
            }
        });

        setUpRecyclerView(this);
        changeEmptyViewText(mRecyclerView);




        SleevelessAdapter = new SleevelessAdapter(this);
        mRecyclerView.setAdapter(SleevelessAdapter);
        SleevelessAdapter.setListener(this);



        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Sleevelesss");

        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid() + "/Tags/");


        mDatabaseListener = FirebaseMethods.returnGalleryListener(mDatabaseReference, Sleeveless.class, SleevelessAdapter);
    }


    @Override
    public void changeHeader(String headerTitle) {
        TextView textView =  findViewById(R.id.ProfileHeader);
        textView.setText(headerTitle);
    }

    @Override
    public void changeEmptyViewText(EmptyRecyclerView emptyRecyclerView) {
        TextView textView = (TextView)emptyRecyclerView.getmEmptyView();
        textView.setText("Add more Sleeveless");
    }



}
