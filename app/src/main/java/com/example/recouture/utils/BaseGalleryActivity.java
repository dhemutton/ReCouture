package com.example.recouture.utils;

import android.content.Context;
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

public abstract class BaseGalleryActivity<T> extends BaseActivity implements OnRecyclerClickListener<T> {


    protected EmptyRecyclerView mRecyclerViewShirt;

    protected RelativeLayout deleteNavBar;

    protected TextView cancelDelete;

    protected boolean isDeletable = false;

    protected List<T> toBeDeleted = new ArrayList<>();

    protected GenericGalleryAdapter genericGalleryAdapter;

    protected ImageView delete;


//    public void onClick(View view) {
//        if (isDeletable) {
//            if (!isSelected) {
//                toBeDeleted.add(shirts.get(getAdapterPosition()));
//                itemView.setBackgroundColor(Color.argb(50,0,0,0));
//                checkHolder.setVisibility(View.VISIBLE);
//                isSelected = true;
//            } else {
//                toBeDeleted.remove(shirts.get(getAdapterPosition()));
//                itemView.setBackgroundColor(Color.argb(0,0,0,0));
//                checkHolder.setVisibility(View.INVISIBLE);
//                isSelected = false;
//            }
//
//        }

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
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);

        deleteNavBar = findViewById(R.id.deleteNavBar);
        cancelDelete = findViewById(R.id.canceldel);
        deleteNavBar.setVisibility(View.INVISIBLE);

////        cancelDelete.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                helperCancelDelete(genericGalleryAdapter);
//            }
//        });
    }

    private void helperCancelDelete(GenericGalleryAdapter galleryAdapter) {
        bottomNavigationViewEx.setVisibility(View.VISIBLE);
        deleteNavBar.setVisibility(View.INVISIBLE);
        toBeDeleted.clear();
        isDeletable = false;
        galleryAdapter.cancel = true;
        galleryAdapter.notifyDataSetChanged();
    }



//         deleteNavBar.setVisibility(View.INVISIBLE);
//                bottomNavigationViewEx.setVisibility(View.VISIBLE);
//                shirtAdapter.clearDeletables();
//                shirtAdapter.setDeletable(false);
//                shirtAdapter.cancelSelection(true);
//                shirtAdapter.notifyDataSetChanged();
//


    public abstract void changeHeader(String headerTitle);

    public void setUpRecyclerView(Context context) {
        mRecyclerViewShirt = findViewById(R.id.recyclerViewShirt);
        mRecyclerViewShirt.setHasFixedSize(true);
        mRecyclerViewShirt.setLayoutManager(new GridLayoutManager(context,3));
        mRecyclerViewShirt.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        mRecyclerViewShirt.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mRecyclerViewShirt.setEmptyView(findViewById(R.id.empty_view));
    }

    @Override
    public int setView() {
        return R.layout.activity_shirts;
    }


//    final RelativeLayout deleteNavBar = findViewById(R.id.deleteNavBar);
//    cancelDelete = findViewById(R.id.canceldel);
//        cancelDelete.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            deleteNavBar.setVisibility(View.INVISIBLE);
//            bottomNavigationViewEx.setVisibility(View.VISIBLE);
//            shirtAdapter.clearDeletables();
//            shirtAdapter.setDeletable(false);
//            shirtAdapter.cancelSelection(true);
//            shirtAdapter.notifyDataSetChanged();
//        }
//    });

}
