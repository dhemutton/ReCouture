package com.example.recouture.Shirt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.recouture.R;

public class ShirtActivity extends AppCompatActivity {

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
