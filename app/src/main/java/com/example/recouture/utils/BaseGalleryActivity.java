package com.example.recouture.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recouture.Item;
import com.example.recouture.R;
import com.example.recouture.ShirtGallery.EmptyRecyclerView;
import com.example.recouture.ShirtGallery.Shirt;
import com.example.recouture.ShirtGallery.ShirtActivity;
import com.example.recouture.TagHolder;
import com.example.recouture.utils.BaseActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGalleryActivity<T extends Item> extends BaseActivity implements OnRecyclerClickListener<T> {


    private static final String TAG = "BaseGalleryActivity";

    protected EmptyRecyclerView mRecyclerViewShirt;

    protected RelativeLayout deleteNavBar;

    protected TextView cancelDelete;

    protected boolean isDeletable = false;

    protected List<T> toBeDeleted = new ArrayList<>();

    protected ImageView delete;

    protected TextView selectButton;

    protected ImageView backButton;





    public void onItemClicked(View itemView, T item,BaseViewHolder baseViewHolder) {
        if (isDeletable) {
            if (!baseViewHolder.isSelected) {
                toBeDeleted.add(item);
                itemView.setBackgroundColor(Color.argb(50, 0, 0, 0));
                baseViewHolder.checkHolder.setVisibility(View.VISIBLE);
                baseViewHolder.isSelected = true;
            } else {
                toBeDeleted.remove(item);
                itemView.setBackgroundColor(Color.argb(0, 0, 0, 0));
                baseViewHolder.checkHolder.setVisibility(View.INVISIBLE);
                baseViewHolder.isSelected = false;
            }
        } else {
            Log.i(TAG,"called itemCLick");
            Intent intent = new Intent(getApplicationContext(),ClickItemActivity.class);
            intent.putExtra("itemName",item.getmName());
            intent.putExtra("item",item);
            intent.putParcelableArrayListExtra("tagHolders",(ArrayList)item.getTags());
            intent.putExtra("firebaseRef",item.getCategory());
            intent.putExtra("color",item.getmColor());
            intent.putExtra("imageUri",item.getmImageUrl());
            getApplicationContext().startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);

        deleteNavBar = findViewById(R.id.deleteNavBar);
        cancelDelete = findViewById(R.id.canceldel);
        deleteNavBar.setVisibility(View.INVISIBLE);



       selectButton = (TextView) findViewById(R.id.selectButton);
       selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationViewEx.setVisibility(View.INVISIBLE);
                deleteNavBar.setVisibility(View.VISIBLE);
                isDeletable = true;
            }
        });

        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseGalleryActivity.super.onBackPressed();
            }
        });
    }

    protected void helperCancelDelete(GenericGalleryAdapter galleryAdapter) {
        bottomNavigationViewEx.setVisibility(View.VISIBLE);
        deleteNavBar.setVisibility(View.INVISIBLE);
        toBeDeleted.clear();
        isDeletable = false;
        galleryAdapter.cancel = true;
        galleryAdapter.notifyDataSetChanged();
    }




    public abstract void changeHeader(String headerTitle);

    public void setUpRecyclerView(Context context) {
        mRecyclerViewShirt = findViewById(R.id.recyclerViewShirt);
        mRecyclerViewShirt.setHasFixedSize(true);
        mRecyclerViewShirt.setLayoutManager(new GridLayoutManager(context,3));
        mRecyclerViewShirt.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.HORIZONTAL));
        mRecyclerViewShirt.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        mRecyclerViewShirt.setEmptyView(findViewById(R.id.empty_view));
    }

    @Override
    public int setView() {
        return R.layout.activity_shirts;
    }

}
