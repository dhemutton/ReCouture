package com.example.recouture.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;

import com.example.recouture.R;
import com.example.recouture.ShirtGallery.EmptyRecyclerView;
import com.example.recouture.ShirtGallery.ShirtActivity;
import com.example.recouture.utils.BaseActivity;

public abstract class BaseGalleryActivity extends BaseActivity {


    protected EmptyRecyclerView mRecyclerViewShirt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public int setView() {
        return R.layout.activity_shirts;
    }




}
