
package com.example.recouture.ShortsGallery;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.R;
import com.example.recouture.utils.BaseGalleryActivity;
import com.example.recouture.utils.EmptyRecyclerView;
import com.example.recouture.utils.FirebaseMethods;
import com.google.firebase.database.FirebaseDatabase;

public class ShortsActivity extends BaseGalleryActivity<Shorts> {

    //private EmptyRecyclerView mRecyclerViewSleeveless;



    // cancel dustbin


    // delete items
    private ImageView delete;

    private ShortsAdapter ShortsAdapter;


    // to delete image from storage ref


    /*
    how to extract the layout? everything is the same except the header. Set content view for
    each activity use the same but change profile header title.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeHeader("Shorts");
        //changeEmptyViewText(mRecyclerView);

        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperCancelDelete(ShortsAdapter);
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMethods.deleteGalleryImages(toBeDeletedOrChosen, mDatabaseReference, mDatabaseTagRef,
                        ShortsActivity.this);
            }
        });

        setUpRecyclerView(this);
        changeEmptyViewText(mRecyclerView);




        ShortsAdapter = new ShortsAdapter(this);
        mRecyclerView.setAdapter(ShortsAdapter);
        ShortsAdapter.setListener(this);



        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Shorts");

        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid() + "/Tags/");


        mDatabaseListener = FirebaseMethods.returnGalleryListener(mDatabaseReference, Shorts.class, ShortsAdapter);
    }


    @Override
    public void changeHeader(String headerTitle) {
        TextView textView =  findViewById(R.id.ProfileHeader);
        textView.setText(headerTitle);
    }

    @Override
    public void changeEmptyViewText(EmptyRecyclerView emptyRecyclerView) {
        TextView textView = (TextView)emptyRecyclerView.getmEmptyView();
        textView.setText("Add more Shorts");
    }


}
