package com.example.recouture.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.recouture.R;

public abstract class ClickItemActivity extends AppCompatActivity {

    /**
     * This class will implement all common UI when a user clicks an item in a gallery.
     * Common features include the layout, the edit and delete function. As well as a base activity
     * to extend the bottom nav bar UI.
     * What do i need when i delete. -> DatabaseReference and StorageReference. 
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_item);
    }
}
