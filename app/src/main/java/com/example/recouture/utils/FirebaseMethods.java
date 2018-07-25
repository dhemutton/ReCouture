package com.example.recouture.utils;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.recouture.Item;
import com.example.recouture.R;
import com.example.recouture.ShirtGallery.Shirt;
import com.example.recouture.TagHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    private static FirebaseUser firebaseUser;
    private Context mContext;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private String userID;
    private String FIREBASE_IMAGE_STORAGE = "photos/users";


    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    /**
     * Helper class that implements firebase methods for use in other activities.
     */


    /**
     * Firebase method to get User's Uid.
     * @return the user's unique Uid.
     */
    public static String getUserUid() {
        if (firebaseUser == null) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        return firebaseUser.getUid();
    }

    public static<T extends Item> void deleteGalleryImages(List<T> deletables, final DatabaseReference mDatabaseReference,
                                                           DatabaseReference mDatabaseTagRef,
                                                           final Context context) {
        for (final T item : deletables) {
            final String selectedKey = item.getKey();
            StorageReference imageRef = FirebaseStorage.getInstance().
                    getReferenceFromUrl(item.getmImageUrl());
            List<TagHolder> tags = item.getTags();
            for (TagHolder tagHolder : tags) {
                String tagName = tagHolder.getTagName();
                final DatabaseReference tagRef = mDatabaseTagRef.child(tagName);
                String uniqueKey = tagHolder.getmKey();
                tagRef.child(uniqueKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG,"delete tag success");
                    }
                });
            }
            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mDatabaseReference.child(selectedKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context,"successfully deleted",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }


    public static <T extends Item> ValueEventListener returnGalleryListener(DatabaseReference databaseRef, final Class<T> itemClass, final GenericGalleryAdapter genericGalleryAdapter) {
        ValueEventListener mValueEventListener = databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                genericGalleryAdapter.clear();
                // mShirtList.clear();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    T item = (T)postSnapShot.getValue(itemClass);
                    item.setMkey(postSnapShot.getKey());
                    // mShirtList.add(shirt);
                    genericGalleryAdapter.addItem(item);
                }
                 genericGalleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return mValueEventListener;
    }


    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child("Posts")
                .getChildren()){
            count++;
        }
        return count;
    }

}
