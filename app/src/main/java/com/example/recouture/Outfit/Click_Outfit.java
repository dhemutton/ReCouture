package com.example.recouture.Outfit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.R;

public class Click_Outfit extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click__outfit);

        Intent i = getIntent();

        ImageView imageView = (ImageView) findViewById(R.id.emptyimage);
        TextView picName = (TextView) findViewById(R.id.picturename);

        String url = i.getStringExtra("viewing");
        Glide.with(Click_Outfit.this).load(url).into(imageView);

//        name = getIntent().getStringExtra("name");
//        picName.setText(name);
    }
}
