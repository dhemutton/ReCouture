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
import com.example.recouture.utils.EmptyRecyclerView;
import com.example.recouture.utils.EventDecorator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.example.recouture.R.layout.*;
import com.example.recouture.Calendar.CalendarUtils;

import static android.content.ContentValues.TAG;
import static com.example.recouture.Calendar.CalendarUtils.FIREBASE_DATE_FORMATTER;
import static com.example.recouture.Calendar.CalendarUtils.FORMATTER;


/**
 * Created by User on 7/24/2017.
 */

public class ConfirmPlan extends AppCompatActivity {

    /*
    add a listener for events database and to check for 4 days before and after
    see if that day has any event that has the same item . If it has notify
     */

    private static final String TAG = "NextActivity";



    //firebase
    private FirebaseUser firebaseUser;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
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

        List<Item> itemList = getIntent().getParcelableArrayListExtra("itemList");

        Log.i(TAG,outfit.toString());

        date = getIntent().getParcelableExtra("calendarDay");
        theDate.setText(FORMATTER.format(date.getDate()));

        for (Item item : itemList) {
            Log.i(TAG,item.getCategory());
        }

        findMaterialCalendar();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference()
                .child(FIREBASE_IMAGE_STORAGE + "/" + firebaseUser.getUid() + "/Events");

        checkConflict(); // dummy test value
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
                uploadFile();
            }
        });
    }

    //Calendar calendar = Calendar.getInstance(); // return calendar with current date.
//            calendar.add(Calendar.MONTH, -2); // make calendar go back 2 months
//            ArrayList<CalendarDay> dates = new ArrayList<>(); // create array list of calendar dates
//            for (int i = 0; i < 30; i++) { // loop 30 times
//                CalendarDay day = CalendarDay.from(calendar); // get the day of the calendar 2 months back
//                dates.add(day); // add the day to the array list
//                calendar.add(Calendar.DATE, 5); // make calendar jump 5 days
//            }
//            return dates; // get array list of dates over 150 days
    /*
    we have the date. Calendar = calendarDay.getCalendar() -> Calendar.add(-4,DAYS);
    have a ArrayList<CalendarDay> dates. loop 8 times, in loop,
    we call CalendarDay day = CalendarDay.from(calendar);
    add each day to dates. Then add one to the calendar.
    We get an arraylist of CalendarDay dates. Convert to a string of dates.
    Add single value event listener for events in firebase and with the same dates in the array list.
    Check the items in that outfit and see whether they match the items in the current outfit. Maybe add
    all the firebase events into a arraylist and sort it. Then try to find the index of the date that
    we upload using bin search. Then take 4 outfits from before the index and 4 outfits after the index.
    For each outfit check if the date matches the range within the upload. If outfit matches the range ,
    check if there is an item that has conflict with the current outfit that you are uploading.
    -> Out of the 8 items check which dates are valid -> out of all the valid dates ->
    loop through their itemList and for each item check if the current outfit has that item. O(itemListOutfit * itemListOtherOutfits). Get all the
    items in the item list then sort the items according to name? O(itemListOutfit * log(itemListOtherOutfits)) .

     */

    public boolean checkWithinDate(String firebaseDate, int maxDay, int minDay, int month, int year) {
        String[] dateArray = firebaseDate.split("-");
        int firebaseDay = Integer.valueOf(dateArray[0]);
        int firebaseMonth = Integer.valueOf(dateArray[1]);
        int firebaseYear = Integer.valueOf(dateArray[2]);
        Log.i(TAG,"year : "  + firebaseYear + " month : " + firebaseMonth + "day : " + firebaseDay);
        if (year == firebaseYear && month == firebaseMonth) {
            if (firebaseDay <= maxDay && firebaseDay >= minDay) {
                Log.i(TAG,"true");
                return true;
            }
        }
        return false;
    }




    public void checkConflict() {
        Calendar calendar = date.getCalendar();
        ArrayList<Date> dates = new ArrayList<>();
        calendar.add(Calendar.DATE,-4);
        int minDay = calendar.get(Calendar.DAY_OF_MONTH); // get min day
        Log.i(TAG,"minDay " + minDay);
        calendar.add(Calendar.DATE,8);
        int maxDay = calendar.get(Calendar.DAY_OF_MONTH); // get max day
        Log.i(TAG,"maxday " + maxDay);

        int month = date.getMonth() + 1; // get month for the day u add outfit
        Log.i(TAG,"calendarDayMonth " + month);

        int year = date.getYear(); // get year for the day u add outfit.
        Log.i(TAG,"calendarDayYear " + year);

        Log.i(TAG,"check conflict");

        readData(new FirebaseCallback() {
            @Override
            public void onCallBack(List<Event> events) {
                Log.i(TAG, events.toString());
                List<Event> filteredListOfEvents = new ArrayList<>();
                for (Event event : events) {
                    String date = event.getmDate();
                    Log.i(TAG, date);
                    if (checkWithinDate(date, maxDay, minDay, month, year)) {
                        filteredListOfEvents.add(event);
                    }
                }
                for (Event e : filteredListOfEvents) {
                    Log.i(TAG, e.toString());
                }
                List<Item> itemList = getAllItemsFromEvents(filteredListOfEvents);
                List<Item> similarItems = findSimilarOutfits(itemList, outfit.getItemList());
                Log.i(TAG,similarItems.toString());
                if (similarItems.size() >= 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Item item : similarItems) {
                        stringBuilder.append(item.toString() + ", ");
                    }
                    String names = stringBuilder.toString();
                    Toast.makeText(ConfirmPlan.this, "Similar items found : " + names, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private List<Item> findSimilarOutfits(List<Item> itemList1, List<Item> outfitList) {
        Collections.sort(itemList1);
        List<Item> similarItems = new ArrayList<>();
        for (Item item : itemList1) {
            int index = Collections.binarySearch(outfitList,item);
            if (index > -1) {
                similarItems.add(item);
            }
        }
        return similarItems;
    }

    private List<Item> getAllItemsFromEvents(List<Event> events) {
        List<Item> itemList = new ArrayList<>();
        for (Event event : events) {
            List<Item> items = event.getOutfit().getItemList();
            itemList.addAll(items);
        }
        return itemList;
    }


    public void readData(FirebaseCallback firebaseCallback) {
        mDatabaseRef.child("Events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Event> events = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Event event = childSnapshot.getValue(Event.class);
                    events.add(event);
                }

//                for (Event event : events) {
//                    Log.i(TAG,event.toString());
//                }
                firebaseCallback.onCallBack(events);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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


interface FirebaseCallback {
    void onCallBack(List<Event> events);
}
