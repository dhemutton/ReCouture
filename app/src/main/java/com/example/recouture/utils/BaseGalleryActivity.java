package com.example.recouture.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recouture.Item;
import com.example.recouture.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGalleryActivity<T extends Item> extends BaseActivity implements OnRecyclerClickListener<T> {


    /*
    Override finish() method. then get extras, if extras is not null we have a choose outfit boolean.

     */



    protected DatabaseReference mDatabaseReference;
    // private BottomNavigationViewEx bottomNavigationViewEx;

    protected ValueEventListener mDatabaseListener;

    // to delete tag of Shoes
    protected DatabaseReference mDatabaseTagRef;

    private boolean chooseOutfits = false;


    private static final String TAG = "BaseGalleryActivity";

    protected EmptyRecyclerView mRecyclerView;

    protected RelativeLayout deleteNavBar;

    protected TextView cancelDelete;

    protected boolean isDeletable = false;

    protected List<T> toBeDeletedOrChosen = new ArrayList<>();

    protected ImageView delete;

    protected TextView selectButton;

    protected ImageView backButton;


    @Override
    public void finish() {
        if (chooseOutfits) {
            Intent data = new Intent();
            data.putParcelableArrayListExtra("outfits", (ArrayList)toBeDeletedOrChosen);
            setResult(RESULT_OK, data);
        }
        super.finish();
    }

    public void onItemClicked(View itemView, T item, BaseViewHolder baseViewHolder) {
        if (isDeletable || chooseOutfits) {
            if (!baseViewHolder.isSelected) {
                toBeDeletedOrChosen.add(item);
                itemView.setBackgroundColor(Color.argb(50, 0, 0, 0));
                baseViewHolder.checkHolder.setVisibility(View.VISIBLE);
                baseViewHolder.isSelected = true;
            } else {
                toBeDeletedOrChosen.remove(item);
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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
        }
    }

    // add a button for confirming list of items chosen.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);

        deleteNavBar = findViewById(R.id.deleteNavBar);
        cancelDelete = findViewById(R.id.canceldel);
        deleteNavBar.setVisibility(View.INVISIBLE);

        if (getIntent().hasExtra("chooseOutfits")) {
            chooseOutfits = getIntent().getExtras().getBoolean("chooseOutfits");
        }


       selectButton = (TextView) findViewById(R.id.selectButton);
       selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chooseOutfits) {
                    bottomNavigationViewEx.setVisibility(View.INVISIBLE);
                    deleteNavBar.setVisibility(View.VISIBLE);
                    isDeletable = true;
                } else {
                    Toast.makeText(BaseGalleryActivity.this,"choose outfit now ",Toast.LENGTH_SHORT).show();
                }
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
        toBeDeletedOrChosen.clear();
        isDeletable = false;
        galleryAdapter.cancel = true;
        galleryAdapter.notifyDataSetChanged();
    }




    public abstract void changeHeader(String headerTitle);

    public void setUpRecyclerView(Context context) {
        mRecyclerView = findViewById(R.id.recyclerViewShirt);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.HORIZONTAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setEmptyView(findViewById(R.id.empty_view));
    }

    @Override
    public int setView() {
        return R.layout.activity_shirts;
    }

    public abstract void changeEmptyViewText(EmptyRecyclerView emptyRecyclerView);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mDatabaseListener);
    }

}
