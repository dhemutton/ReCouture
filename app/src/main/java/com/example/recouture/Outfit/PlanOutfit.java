package com.example.recouture.Outfit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.recouture.Calendar.CalendarActivity;
import com.example.recouture.Item;
import com.example.recouture.R;
import com.example.recouture.ShirtGallery.Shirt;
import com.example.recouture.ShirtGallery.ShirtAdapter2;
import com.example.recouture.utils.BaseActivity;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.CommonUi;
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.GridImageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlanOutfit extends BaseActivity {

    private static final String TAG = "ViewOutfits";

    private DatabaseReference mDatabaseReference;
    private ValueEventListener mDatabaseListener;
    private static final int NUM_GRID_COLUMNS = 4;

    private GridView gridView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private String mSelectedImage;
    private ArrayList<String> imgUrls;
    private CalendarDay date;
    final ArrayList<Outfit> outfits = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);


        gridView = (GridView)findViewById(R.id.gridview) ;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        date = getIntent().getParcelableExtra("calendarDay");

        setupGridView();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /* i need to send the outfit here with the list of items to confirm plan so that
                when i upload the calendar event to firebase i have the outfit in my database. Every time i add
                outfit i call
                 */

                Log.i(TAG, "object is at pos : " + position + outfits.get(position).toString());
                Outfit outfit = outfits.get(position);
                //testing parcelable for outfitcal
                Log.d(TAG, "onClick: navigating to the view planned outfits.");
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                Intent intent = new Intent(PlanOutfit.this, ConfirmPlan.class);
                intent.putExtra("planning", bitmap);
                intent.putExtra("calendarDay", date);
                intent.putExtra("outfit", outfit);

                for (Item item : outfit.getItemList()) {
                    if (item instanceof Shirt) {
                        Log.i(TAG,"this is shirt");
                    }
                }

                intent.putParcelableArrayListExtra("itemList",(ArrayList)outfit.getItemList());
                startActivity(intent);
            }
        });
    }

    @Override
    public int setView() {
        return R.layout.activity_viewoufits;
    }

    // setUpGridView(Context context, ArrayList<T>

    private void setupGridView() {
        Log.d(TAG, "setupGridView: Setting up image grid.");

        // listener called everytime this activity starts up
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Outfits");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                reading data from the outfits snapshot.
                 */

                Log.i(TAG,"listener called");

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Log.i(TAG,singleSnapshot.toString());
                    outfits.add(singleSnapshot.getValue(Outfit.class));
                }


                for (Outfit outfit : outfits) {
                    List<Item> itemList = outfit.getItemList();
                    for (Item item : itemList) {
                        Log.i(TAG,item.getCategory());
                    }
                }
                ArrayList<String> imageUri = CommonUi.getOutfitUri(outfits);
                CommonUi.setGridView(PlanOutfit.this,gridView,imageUri);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled");
            }
        });
    }
}

