package com.example.recouture.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recouture.R;
import com.example.recouture.ShirtGallery.EmptyRecyclerView;
import com.example.recouture.ShirtGallery.ShirtActivity;
import com.example.recouture.utils.BaseActivity;

public abstract class BaseGalleryActivity extends BaseActivity {


    protected EmptyRecyclerView mRecyclerViewShirt;

    protected RelativeLayout deleteNavBar;

    protected TextView cancelDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(setView());

//        deleteNavBar = findViewById(R.id.deleteNavBar);
//        cancelDelete = findViewById(R.id.canceldel);
//
//        cancelDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // helperDelete()
//            }
//        });
        super.onCreate(savedInstanceState);
    }

    private void helperDelete(GenericGalleryAdapter galleryAdapter) {

    }


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
