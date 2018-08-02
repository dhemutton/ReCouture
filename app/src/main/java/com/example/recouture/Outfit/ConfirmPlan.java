package com.example.recouture.Outfit;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recouture.Calendar.CalendarActivity;
import com.example.recouture.Calendar.Event;
import com.example.recouture.HomePage.HomepageActivity;
import com.example.recouture.Item;
import com.example.recouture.StartUpPage.ActivityIndicator;
import com.example.recouture.utils.EventDecorator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.recouture.R;
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.UniversalImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.example.recouture.R.layout.*;


/**
 * Created by User on 7/24/2017.
 */

public class ConfirmPlan extends AppCompatActivity {

    /*
    add a listener for events database and to check for 4 days before and after
    see if that day has any event that has the same item . If it has notify
     */

    private static final String TAG = "NextActivity";

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    private static final SimpleDateFormat FIREBASE_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    //firebase
    private FirebaseUser firebaseUser;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private FirebaseMethods mFirebaseMethods;
    private StorageTask mUploadTask;
    private String FIREBASE_IMAGE_STORAGE = "photos/users";



    private TextView theDate;
    Uri imageUri;
    private ActivityIndicator activityIndicator;
    private CalendarDay date;

    private MaterialCalendarView materialCalendarView;

    private  Outfit outfit;


    //vars
    private String mAppend = "file:/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_plan);
        theDate = (TextView) findViewById(R.id.date);
        activityIndicator = new ActivityIndicator(this);

        // get outfit
        outfit = getIntent().getParcelableExtra("outfit");


        Log.i(TAG,outfit.toString());

        date = getIntent().getParcelableExtra("calendarDay");
        theDate.setText(FORMATTER.format(date.getDate()));

        findMaterialCalendar();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference()
                .child(FIREBASE_IMAGE_STORAGE + "/" + firebaseUser.getUid() + "/Events");

        setImage();


        ImageView backArrow = (ImageView) findViewById(R.id.ivBackArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the activity");
                finish();
            }
        });


        TextView add = (TextView) findViewById(R.id.tvShare);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: add outfit to the date.");
                //upload the image to firebase
                Toast.makeText(ConfirmPlan.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
                Log.i(TAG,materialCalendarView.toString());
                materialCalendarView.addDecorator(new EventDecorator(date, Color.RED));
                uploadFile();
            }
        });
    }



    public void findMaterialCalendar() {
        View v = LayoutInflater.from(this).inflate(R.layout.layout_calendar,null,false);
        materialCalendarView = v.findViewById(R.id.calendarView);
    }

    /**
     * gets the image url from the incoming intent and displays the chosen image
     */
    private void setImage() {
        Intent intent = getIntent();
        ImageView image = (ImageView) findViewById(R.id.imageShare);
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("planning");
        image.setImageBitmap(bitmap);
        imageUri = FirebaseMethods.getImageUri(ConfirmPlan.this, bitmap);
    }






    /**
     * Upload file method. Handles logic of uploading all required data to firebase and displaying
     * it in recycler view.
     */
    private void uploadFile() {

        final String dateConfirm = FIREBASE_DATE_FORMATTER.format(date.getDate());
        final String location = "Events";
        final DatabaseReference databaseRef = mDatabaseRef.child("/" + location);

        if (imageUri != null) {
            activityIndicator.show();
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() +
                    "." + FirebaseMethods.getFileExtension(imageUri,getApplicationContext()));

            mUploadTask = fileReference.putFile(imageUri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Event event = new Event(dateConfirm, uri.toString(),outfit);
                                    String date = dateConfirm;
                                    databaseRef.child(dateConfirm).setValue(event);
                                }
                            });
                            Toast.makeText(ConfirmPlan.this, "upload successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ConfirmPlan.this, CalendarActivity.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ConfirmPlan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            activityIndicator.dismiss();
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}