package com.example.recouture.Outfit;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.recouture.HomePage.HomepageActivity;
import com.example.recouture.R;
import com.example.recouture.StartUpPage.ActivityIndicator;
import com.example.recouture.utils.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Click_Outfit extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseUser firebaseUser;
    private String FIREBASE_IMAGE_STORAGE = "photos/users";
    private ActivityIndicator activityIndicator;


    private String name;
    private static final String TAG = "Click Outfits";
    private Button post;
    private int imageCount = 0;
    private String url;
    private StorageTask mUploadTask;
    private Uri myUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "starting click");
        activityIndicator = new ActivityIndicator(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click__outfit);

        Intent i = getIntent();

        ImageView imageView = (ImageView) findViewById(R.id.emptyimage);
        TextView picName = (TextView) findViewById(R.id.name);

        name = i.getStringExtra("name");
        url = i.getStringExtra("viewing");
        myUri = Uri.parse(url);
        Glide.with(this).load(url).into(imageView);

        Log.d(TAG, "name :" + url);

        picName.setText(name);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference()
                .child(FIREBASE_IMAGE_STORAGE + "/" + firebaseUser.getUid() + "/Posts");


        post = (Button) findViewById(R.id.postbut);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload the image to firebase
                Toast.makeText(Click_Outfit.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
                postOutfit();
            }
        });
    }

    private String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        return sdf.format(new Date());
    }


    private void postOutfit() {

        final String location = "Posts";
        final DatabaseReference databaseRef = myRef.child("/" + location);

        Post post = new Post(getTimestamp(), url,name);

//                                    post.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String uploadId = databaseRef.push().getKey();
//                                    post.setPhoto_id(uploadId);
        databaseRef.child(uploadId).setValue(post);
        Toast.makeText(Click_Outfit.this, "upload successful", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Click_Outfit.this, HomepageActivity.class);
        startActivity(i);
    }



    /**
     * gets the file extension type for particular URI.
     * @param uri the image URI
     * @return the file extension for this URI, eg jpeg,png
     */
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}