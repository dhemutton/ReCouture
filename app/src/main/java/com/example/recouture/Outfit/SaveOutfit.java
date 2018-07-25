package com.example.recouture.Outfit;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recouture.Add.AddActivity;
import com.example.recouture.Calendar.CalendarActivity;
import com.example.recouture.HomePage.HomepageActivity;
import com.example.recouture.R;
import com.example.recouture.ShirtGallery.Shirt;
import com.example.recouture.StartUpPage.ActivityIndicator;
import com.example.recouture.TagHolder;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.UniversalImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveOutfit extends AppCompatActivity {

    private static final String TAG = "SaveOutfit";

    //vars
    EditText editTextName;
    private ImageView img;
    Uri imageUri;
    private static final int ACTIVITY_NUM = 1;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser firebaseUser;
    private String FIREBASE_IMAGE_STORAGE = "photos/users";
    private ActivityIndicator activityIndicator;
    private StorageTask mUploadTask;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_outfit);
        setupBottomNavigationView();

        img = (ImageView) findViewById(R.id.empty);
        activityIndicator = new ActivityIndicator(this);

        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(SaveOutfit.this
                    .openFileInput("myImage"));
            imageUri = getImageUri(this, b);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        img.setImageBitmap(b);


        editTextName = findViewById(R.id.editname);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference()
                .child(FIREBASE_IMAGE_STORAGE + "/" + firebaseUser.getUid() + "/Outfits");



        TextView add = (TextView) findViewById(R.id.addtext);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to the final share screen.");
                //upload the image to firebase
                Toast.makeText(SaveOutfit.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
                uploadFile();

            }
        });
    }

    /**
     * Upload file method. Handles logic of uploading all required data to firebase and displaying
     * it in recycler view.
     */
    private void uploadFile() {

        final String name = editTextName.getText().toString().trim();
        final String location = "Outfits";
        final DatabaseReference databaseRef = mDatabaseRef.child("/" + location);

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
                                    Outfit outfit = new Outfit(name, uri.toString());
                                    String uploadId = databaseRef.push().getKey();
                                    databaseRef.child(uploadId).setValue(outfit);

                                }
                            });
                            Toast.makeText(SaveOutfit.this, "upload successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SaveOutfit.this, HomepageActivity.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SaveOutfit.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            activityIndicator.dismiss();
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
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

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(SaveOutfit.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
