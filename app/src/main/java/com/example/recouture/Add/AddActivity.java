package com.example.recouture.Add;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.example.recouture.HomePage.HomepageActivity;
import com.example.recouture.R;
import com.example.recouture.ShirtGallery.Shirt;
import com.example.recouture.StartUpPage.ActivityIndicator;
import com.example.recouture.TagHolder;
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

import dalvik.system.PathClassLoader;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    private static final int ACTIVITY_NUM = 2;
    private int STORAGE_PERMISSION_CODE = 3;

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

    // firebase database for TAGS
    private DatabaseReference mDatabaseTagRef;

    private String FIREBASE_IMAGE_STORAGE = "photos/users";


    /**
     * On create method for add initializes the view , widgets for all the corresponding views
     * in the layout and also additional firebase database references. Handles camera request when
     * if each image was taken from a photo album or if it was captured from a photo. Calls the
     * corresponding methods to handle each request.
     * @param savedInstanceState
     */
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

        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid() + "/Tags");

        activityIndicator = new ActivityIndicator(this);

        ThreeBounce threeBounce = new ThreeBounce();

        Bundle extras = getIntent().getExtras();

        int requestCode = extras.getInt("requestCode");

        requestStoragePermission();

        switch (requestCode) {
            case 2: {
                if (extras != null) {
                    Bitmap image = (Bitmap) extras.get("image");
                    if (image != null) {
                        imageView.setImageBitmap(image);
                        imageUri = getImageUri(this, image);
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


    /**
     * hanldes permission request for access to storage in camera API23
     * @param requestCode the request code defined by programmer
     * @param permissions the type of permissions requested
     * @param grantResults the results of whether permission was granted corresponding to each permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * request access to storage permission
     */
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }


    /**
     * Upload file method. Handles logic of uploading all required data to firebase and displaying
     * it in recycler view.
     */
    private void uploadFile() {

        final String name = editTextName.getText().toString().trim();
        final String color = editTextColor.getText().toString().trim();
        final String category = editTextCategory.getText().toString().trim();
        final String tags = editTextTags.getText().toString().trim();
        final DatabaseReference databaseRef = mDatabaseRef.child("/" + category);
        //final DatabaseReference tagDataBaseRef = mDatabaseTagRef;


        if (imageUri != null) {
            activityIndicator.show();
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() +
                    "." + getFileExtension(imageUri));

            mUploadTask = fileReference.putFile(imageUri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // handles the retreival of tags when user enters each tag seperated by
                                    // commas.
                                    Shirt shirt = new Shirt(name, color, uri.toString());
                                    List<String> stringTag = Arrays.asList(tags.split(","));
                                    List<TagHolder> tagHolders = new ArrayList<>();

                                    for (String shirtTag : stringTag) {


                                        // Handles the uploading of tags onto firebase for search
                                        // to be conducted.
                                        DatabaseReference dataRef = mDatabaseTagRef.child(shirtTag);
                                        String uniqueId = dataRef.push().getKey();
                                        TagHolder tagHolder = new TagHolder(name,uri.toString(),shirtTag);
                                        tagHolders.add(tagHolder);


                                        //shirt.putTag(shirtTag,tagHolder);
                                        tagHolder.setmKey(uniqueId);
                                        dataRef.child(uniqueId).setValue(tagHolder);
//                                        if (shirt.checkContainsKey(shirtTag)) {
//                                            Log.i("AddActivity",shirtTag);
//                                            Log.i("AddActivity",shirt.retrieveTagHolder(shirtTag).toString());
//                                        }
                                    }
                                    shirt.setTags(tagHolders);
                                    String uploadId = databaseRef.push().getKey();
                                    databaseRef.child(uploadId).setValue(shirt);

                                    // for each tag in the list, upload it onto firebase database.
                                }
                            });
                            Toast.makeText(AddActivity.this, "upload successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AddActivity.this, HomepageActivity.class);
                            startActivity(i);
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

    /**
     * sets up library imported bottom navigation view for this activity.
     */
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(AddActivity.this, bottomNavigationViewEx);
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


    /**
     * Converts a bitmap image into its corresponding URI.
     * @param context the context of the activity.
     * @param inImage the bitmap to be converted
     * @return the corresponding image URI.
     */
    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}