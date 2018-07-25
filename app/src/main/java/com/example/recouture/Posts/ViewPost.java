package com.example.recouture.Posts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.R;

public class ViewPost extends AppCompatActivity {

    private static final String TAG = "Click Post";
    private String name;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_post);

        Log.d(TAG, "starting click");

        Intent i = getIntent();

        ImageView imageView = (ImageView) findViewById(R.id.post_image);
        TextView picName = (TextView) findViewById(R.id.image_caption);

        name = i.getStringExtra("name");
        url = i.getStringExtra("viewing");
        Glide.with(this).load(url).into(imageView);

        Log.d(TAG, "name :" + url);

        picName.setText(name);


    }
}
