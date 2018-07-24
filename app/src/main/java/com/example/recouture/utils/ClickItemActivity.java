package com.example.recouture.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recouture.Item;
import com.example.recouture.R;
import com.example.recouture.TagHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
     *
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
        final String itemName = intent.getExtras().getString("itemName");
        final String imageUri = intent.getExtras().getString("imageUri");
        final String firebaseRef = intent.getExtras().getString("firebaseRef");
        final Item item = intent.getExtras().getParcelable("item");
        tagHolders = item.getTags();

        Glide.with(this).load(imageUri).into(imageView);
        pictureName.setText(itemName);
        postDesColor.setText(color);


        mDatabaseItemReference = FirebaseDatabase.getInstance().getReference().child(FirebaseMethods.getUserUid()).child(firebaseRef).child(item.getKey());

        //tag reference if want to access inner tags.
        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference().child(FirebaseMethods.getUserUid()).child("Tags");

        StringBuilder stringBuilder = new StringBuilder();
        for (TagHolder tagHolder : tagHolders) {
            Log.i(TAG,"tagHolder old name " + tagHolder.getTagName());

            stringBuilder.append(tagHolder.getTagName() + ",");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        postDesTag.setText(stringBuilder.toString());

        clickEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "ClickEdit");
                String textStyle = postDesTag.getText().toString().trim();
                String newColor = postDesColor.getText().toString().trim();

                mDatabaseItemReference.child("mColor").setValue(newColor);



                //remove tags from Tags databaseRef
                for (final TagHolder tagHolder : tagHolders) {
                    Log.i(TAG,"tagHolder old name " + tagHolder.getTagName());
                    mDatabaseTagRef
                            .child(tagHolder.getTagName())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // loop through each unique key associated with the tag name
                                    Log.i(TAG,"ref " + dataSnapshot.getRef());
                                    for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                                        TagHolder tempTagHolder = childSnapShot.getValue(TagHolder.class);
                                        if (tempTagHolder.getmKey().equals(tagHolder.getmKey())) {
                                            childSnapShot.getRef().removeValue();
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }

                // set new tags to item tags and Tags holder tag
                String[] strings = textStyle.split(",");
                List<TagHolder> newTagHolders = new ArrayList<>();
                for (String newTagName : strings) {
                    TagHolder newTagHolder = new TagHolder(itemName,imageUri,
                            newTagName);
                    newTagHolders.add(newTagHolder);
                    DatabaseReference tempDatabaseTagRef =
                            mDatabaseTagRef
                                    .child(newTagName);
                    String uniqueId = tempDatabaseTagRef.push().getKey();
                    tempDatabaseTagRef.child(uniqueId).setValue(newTagHolder);
                }
                //item.setTags(newTagHolders);
                mDatabaseItemReference.child("tags").setValue(newTagHolders).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        }
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ClickItemActivity.this,"why ....." , Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i(TAG,"Ref is at " + mDatabaseItemReference.child("tags"));
                //Toast.makeText(ClickItemActivity.this,"done with editing",Toast.LENGTH_SHORT).show();
            }
        });

        clickDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> deleteItemList = new ArrayList<>();
                deleteItemList.add(item);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child(firebaseRef);
                FirebaseMethods.deleteGalleryImages(deleteItemList,databaseReference,mDatabaseTagRef,ClickItemActivity.this);
                finish();
            }
        });
    }


    private void setWidgets() {
        imageView = findViewById(R.id.emptyimage);
        postDesColor = findViewById(R.id.editname);
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
