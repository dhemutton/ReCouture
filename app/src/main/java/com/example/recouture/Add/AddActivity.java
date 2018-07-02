package com.example.recouture.Add;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.recouture.R;
import com.example.recouture.ShirtGallery.Shirt;
import com.example.recouture.StartUpPage.ActivityIndicator;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.github.ybq.android.spinkit.style.ThreeBounce;
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
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    private static final int ACTIVITY_NUM = 2;

    ImageView imageView;
    EditText editTextName;
    EditText editTextCategory;
    EditText editTextColor;
    EditText editTextTags;
    Button upload;
    Uri imageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser firebaseUser;
    private StorageTask mUploadTask;
    private ActivityIndicator activityIndicator;

    private String FIREBASE_IMAGE_STORAGE = "photos/users";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_additem);
        Log.i(TAG, "onCreate: started");
        setupBottomNavigationView();

        imageView = findViewById(R.id.addPic);

        editTextCategory = findViewById(R.id.editTextCategory);
        editTextColor = findViewById(R.id.editTextColor);
        editTextName = findViewById(R.id.editTextName);
        editTextTags = findViewById(R.id.editTextTags);
        upload = findViewById(R.id.upload);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mStorageRef = FirebaseStorage.getInstance().getReference()
                .child(FIREBASE_IMAGE_STORAGE + "/" + firebaseUser.getUid() + "/Shirts");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());

        activityIndicator = new ActivityIndicator(this);

        ThreeBounce threeBounce = new ThreeBounce();

        Bundle extras = getIntent().getExtras();

        int requestCode = extras.getInt("requestCode");

        switch (requestCode) {
            case 2: {
                if (extras != null) {
                    Bitmap image = (Bitmap) extras.get("image");
                    if (image != null) {
                        imageView.setImageBitmap(image);
                        //imageUri = getImageUri(this, image);
                    }
                }
                break;
            }
            case 1: {
                Intent intent = getIntent();
                String image_path = intent.getStringExtra("imagePath");
                imageUri = Uri.parse(image_path);
                Picasso.get().load(imageUri).resize(100, 100).into(imageView);
            }
            break;
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
    }

    private void uploadFile() {

        final String name = editTextName.getText().toString().trim();
        final String color = editTextColor.getText().toString().trim();
        final String category = editTextCategory.getText().toString().trim();
        final String tags = editTextTags.getText().toString().trim();
        final DatabaseReference databaseRef = mDatabaseRef.child("/" + category);


        if (imageUri != null) {
            activityIndicator.show();
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() +
                    "." + getFileExtension(imageUri));

            mUploadTask = fileReference.putFile(imageUri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Shirt shirt = new Shirt(name, color, uri.toString());
                                    List<String> stringTag = Arrays.asList(tags.split(","));
                                    shirt.setTags(stringTag);
                                    String uploadId = databaseRef.push().getKey();
                                    databaseRef.child(uploadId).setValue(shirt);
                                }
                            });
                            Toast.makeText(AddActivity.this, "upload successfuk", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            activityIndicator.dismiss();
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(AddActivity.this, bottomNavigationViewEx);
    }



    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}