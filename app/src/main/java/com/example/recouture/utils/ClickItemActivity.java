package com.example.recouture.utils;

import android.content.Intent;
import android.nfc.Tag;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    DatabaseReference mDatabaseItemReference;

    DatabaseReference mDatabaseTagRef;





    /**
     * This class will implement all common UI when a user clicks an item in a gallery.
     * Common features include the layout, the edit and delete function. As well as a base activity
     * to extend the bottom nav bar UI.
     * What do i need when i delete. -> DatabaseReference and StorageReference. 
     * @param savedInstanceState
     */

    // just update the firebase values dont need to update individual tag references.
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


        mDatabaseItemReference = FirebaseDatabase.getInstance().getReference().child(FirebaseMethods.getUserUid()).child(firebaseRef).child(item.getKey());

        //tag reference if want to access inner tags.
        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference().child("Tags");

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
                String newColor = postDesColor.getText().toString().trim();

                mDatabaseItemReference.child("mColor").setValue(newColor);

                final List<TagInfo> tagInfos = new ArrayList<>();

                mDatabaseItemReference.child("tags").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                            TagHolder tagHolder = childSnapShot.getValue(TagHolder.class);
                            TagInfo tagInfo = new TagInfo(tagHolder.getTagName(),tagHolder.getmKey());
                            tagInfos.add(tagInfo);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                for (TagInfo tagInfo : tagInfos) {
                    mDatabaseTagRef.child(tagInfo.tagName).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                                TagHolder tagHolder = childSnapShot.getValue(TagHolder.class);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    })
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

class TagInfo {

    public String tagName;

    public String mKey;

    public String newTagName;

    public TagInfo(String tagName, String mKey) {
        this.tagName = tagName;
        this.mKey = mKey;
    }


}
