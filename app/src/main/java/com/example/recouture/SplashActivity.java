package com.example.recouture;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {


    private final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

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
