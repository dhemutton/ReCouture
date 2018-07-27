
package com.example.recouture.ShoesGallery;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.R;
import com.example.recouture.utils.BaseGalleryActivity;
import com.example.recouture.utils.EmptyRecyclerView;
import com.example.recouture.utils.FirebaseMethods;
import com.google.firebase.database.FirebaseDatabase;

public class ShoesActivity extends BaseGalleryActivity<Shoes> {

    //private EmptyRecyclerView mRecyclerViewSleeveless;



    // cancel dustbin


    // delete items
    private ImageView delete;

    private ShoesAdapter shoesAdapter;


    // to delete image from storage ref


    /*
    how to extract the layout? everything is the same except the header. Set content view for
    each activity use the same but change profile header title.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeHeader("Shoes");
        //changeEmptyViewText(mRecyclerView);

        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helperCancelDelete(shoesAdapter);
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMethods.deleteGalleryImages(toBeDeletedOrChosen, mDatabaseReference, mDatabaseTagRef,
                        ShoesActivity.this);
            }
        });

        setUpRecyclerView(this);
        changeEmptyViewText(mRecyclerView);




        shoesAdapter = new ShoesAdapter(this);
        mRecyclerView.setAdapter(shoesAdapter);
        shoesAdapter.setListener(this);



        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Shoes");

        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid() + "/Tags/");


        mDatabaseListener = FirebaseMethods.returnGalleryListener(mDatabaseReference, Shoes.class, shoesAdapter);
    }


    @Override
    public void changeHeader(String headerTitle) {
        TextView textView =  findViewById(R.id.ProfileHeader);
        textView.setText(headerTitle);
    }

    @Override
    public void changeEmptyViewText(EmptyRecyclerView emptyRecyclerView) {
        TextView textView = (TextView)emptyRecyclerView.getmEmptyView();
        textView.setText("Add more Shoes");
    }


}
