package com.example.recouture.StartUpPage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.recouture.R;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class ActivityIndicator extends Dialog {

    private ProgressBar progressBar;

    public ActivityIndicator(@NonNull Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_indicator);
        this.setCancelable(false);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ThreeBounce threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
    }



}
