package com.example.recouture.Shirt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.recouture.R;
import com.google.firebase.database.DatabaseReference;

public class ShirtActivity extends AppCompatActivity {


    /*
    get the images from firebase.
    firebase storage URL -> Photos/shirts
    store a shirt class. {
                            ArrayList<String> tags;
                            ImageUri uri;
                            }
                           edittext can be separated with commas and store them into array.






     */

    private RecyclerView mRecyclerViewShirt;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shirts);


        Toolbar toolbar = (Toolbar) findViewById(R.id.categoryToolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShirtActivity.this,"Pressed back",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
