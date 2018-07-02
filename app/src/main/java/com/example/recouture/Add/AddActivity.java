package com.example.recouture.Add;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.recouture.R;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.ByteArrayOutputStream;
import java.net.URI;

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


        Bundle extras = getIntent().getExtras();

        int requestCode = extras.getInt("requestCode");

        switch (requestCode) {
            case 0: {
                if (extras != null) {
                    Bitmap image = (Bitmap) extras.get("image");
                    if (image != null) {
                        imageView.setImageBitmap(image);
                        imageUri = getImageUri(this, image);
                    }
                }
            }
            case 1: {
                Intent intent = getIntent();
                String image_path = intent.getStringExtra("imagePath");
                imageUri = Uri.parse(image_path);
                Glide.with(this).load(imageUri).apply(new RequestOptions().fitCenter()).into(imageView);
            }
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
    }

    private void uploadFile() {

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