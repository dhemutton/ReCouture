package com.example.recouture.Outfit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

class ScreenshotUtils {

    /*  Method which will return Bitmap after taking screenshot. We have to pass the view which we want to take screenshot.  */
    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        screenView.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
