package com.example.recouture.StartUpPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.recouture.R;

public class SetUpProfile extends AppCompatActivity {

    private EditText username;
    private EditText description;
    private EditText website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_afterregister);

        username = (EditText) findViewById(R.id.display_name);
        description = (EditText) findViewById(R.id.description);
        website = (EditText) findViewById(R.id.website);


    }
}
