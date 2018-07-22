package com.example.recouture.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.Item;
import com.example.recouture.R;
import com.example.recouture.TagHolder;

import java.util.ArrayList;
import java.util.List;

public class ClickItemActivity extends BaseActivity {

    private static final String TAG = "ClickItemActivity";

    private ImageView imageView;

    private EditText postDesTag;

    private EditText postDesColor;

    private Button clickEditPost;

    private Button clickDeletePost;

    private TextView pictureName;

    List<TagHolder> tagHolders;





    /**
     * This class will implement all common UI when a user clicks an item in a gallery.
     * Common features include the layout, the edit and delete function. As well as a base activity
     * to extend the bottom nav bar UI.
     * What do i need when i delete. -> DatabaseReference and StorageReference. 
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);
        setWidgets();
        Intent intent = getIntent();
        //tagHolders = intent.getParcelableArrayListExtra("tagHolders");
        String color = intent.getExtras().getString("color");
        String itemName = intent.getExtras().getString("itemName");
        String imageUri = intent.getExtras().getString("imageUri");
        String firebaseRef = intent.getExtras().getString("firebaseRef");
        final Item item = intent.getExtras().getParcelable("item");
        tagHolders = item.getTags();

        Glide.with(this).load(imageUri).into(imageView);
        pictureName.setText(itemName);
        postDesColor.setText(color);

        StringBuilder stringBuilder = new StringBuilder();
        for (TagHolder tagHolder : tagHolders) {
            stringBuilder.append(tagHolder.getName() + ",");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        postDesTag.setText(stringBuilder.toString());

        clickEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"ClickEdit");
                String textStyle = postDesTag.getText().toString().trim();
                String color = postDesColor.getText().toString().trim();
                item.setmColor(color);
                String[] strings = textStyle.split(",");
                for (int i = 0; i < tagHolders.size(); i++) {
                    tagHolders.get(i).setTagName(strings[i]);
                }
            }
        });
    }


    private void setWidgets() {
        imageView = findViewById(R.id.emptyimage);
        postDesColor = findViewById(R.id.postDesColor);
        postDesTag = findViewById(R.id.postDesTag);
        clickDeletePost = findViewById(R.id.click_deletePost);
        clickEditPost = findViewById(R.id.click_editPost);
        pictureName = findViewById(R.id.picturename);
    }

    @Override
    public int setView() {
        return R.layout.activity_click_shirt_activity;
    }



}
