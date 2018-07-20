package com.example.recouture.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.recouture.R;
import com.example.recouture.utils.BaseActivity;

public abstract class BaseGalleryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setView());
    }


    public abstract void changeHeader(String headerTitle);

    @Override
    public int setView() {
        return R.layout.activity_shirts;
    }




}
