package com.example.recouture.StartUpPage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.recouture.R;
import com.google.firebase.database.FirebaseDatabase;


public class SplashActivity extends AppCompatActivity {


    private final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.i(TAG,Thread.currentThread().getName());

        init();


    }

    private void init() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,Thread.currentThread().getName());
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        },2000);
    }

}
