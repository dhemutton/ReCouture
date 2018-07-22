package com.example.recouture.Outfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.recouture.R;

<<<<<<< HEAD

//public class ViewOutfits extends BaseGalleryActivity<Outfit> {
//
//    private DatabaseReference mDatabaseReference;
//    private ValueEventListener mDatabaseListener;
//    private DatabaseReference mDatabaseTagRef;
//    private ImageView delete;
//    private ShirtAdapter2 shirtAdapter2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        changeHeader("View Outfits");
//
//        cancelDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                helperCancelDelete(shirtAdapter2);
//            }
//        });
//
//        delete = findViewById(R.id.delete);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseMethods.deleteGalleryImages(toBeDeleted, mDatabaseReference, mDatabaseTagRef);
//            }
//        });
//
//        setUpRecyclerView(this);
//
//
//
//        shirtAdapter2 = new ShirtAdapter2(this);
//        mRecyclerViewShirt.setAdapter(shirtAdapter2);
//        shirtAdapter2.setListener(this);
//
//
//
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid()).child("Outfit");
//
//        mDatabaseTagRef = FirebaseDatabase.getInstance().getReference(FirebaseMethods.getUserUid() + "/Tags/");
//
//
//        mDatabaseListener = FirebaseMethods.returnGalleryListener(mDatabaseReference, Outfit.class, shirtAdapter2);
//    }
//
//
//    @Override
//    public void changeHeader(String headerTitle) {
//        TextView textView =  findViewById(R.id.ProfileHeader);
//        textView.setText(headerTitle);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mDatabaseReference.removeEventListener(mDatabaseListener);
//    }
//}
=======
public class ViewOutfits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewoufits);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), Click_Outfit.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }

}
>>>>>>> 8bb5964ee4426673833ac467f76004392a8ea9eb
