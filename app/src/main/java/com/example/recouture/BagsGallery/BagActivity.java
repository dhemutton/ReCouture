
package com.example.recouture.BagsGallery;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.R;
import com.example.recouture.utils.BaseGalleryActivity;
import com.example.recouture.utils.EmptyRecyclerView;
import com.example.recouture.utils.FirebaseMethods;
import com.google.firebase.database.FirebaseDatabase;

public class BagActivity extends BaseGalleryActivity<Bag> {

    //private EmptyRecyclerView mRecyclerViewSleeveless;



    // cancel dustbin


    // delete items
    private ImageView delete;

    private BagAdapter bagAdapterer;


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
                helperCancelDelete(bagAdapterer);
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMethods.deleteGalleryImages(toBeDeletedOrChosen, mDatabaseReference, mDatabaseTagRef,
                        BagActivity.this);
            }
        });

        setUpRecyclerView(this);
        changeEmptyViewText(mRecyclerView);




        bagAdapterer = new BagAdapter(this);
        mRecyclerView.setAdapter(bagAdapterer);
        bagAdapterer.setListener(this);



        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Bags");

        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid() + "/Tags/");


        mDatabaseListener = FirebaseMethods.returnGalleryListener(mDatabaseReference, Bag.class, bagAdapterer);
    }


    @Override
    public void changeHeader(String headerTitle) {
        TextView textView =  findViewById(R.id.ProfileHeader);
        textView.setText(headerTitle);
    }

    @Override
    public void changeEmptyViewText(EmptyRecyclerView emptyRecyclerView) {
        TextView textView = (TextView)emptyRecyclerView.getmEmptyView();
        textView.setText("Add more Bags");
    }


}
