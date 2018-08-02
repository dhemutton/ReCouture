package com.example.recouture.utils;

import android.content.Context;
import android.widget.GridView;

import com.example.recouture.Outfit.Outfit;
import com.example.recouture.Profile.ProfileActivity;
import com.example.recouture.R;

import java.util.ArrayList;
import java.util.List;

public class CommonUi {
    private static final int NUM_GRID_COLUMNS = 4;



    /*
    helper class that initalizes all common views in similar activities.
     */


    public static void initGridView(Context context, GridView gridView,ArrayList<String> imageUris) {
        int gridWidth = context.getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);
        setGridView(context,gridView,imageUris);
    }


    public static ArrayList<String> getOutfitUri(List<Outfit> outfits) {
        ArrayList<String> imgUrls = new ArrayList<String>();
        for (int i = 0; i < outfits.size(); i++) {
            imgUrls.add(outfits.get(i).getmImageUrl());
        }
        return imgUrls;
    }

    public static ArrayList<String> getPostUri(List<Post> posts) {
        ArrayList<String> imgUrls = new ArrayList<String>();
        for (int i = 0; i < posts.size(); i++) {
            imgUrls.add(posts.get(i).getImage_path());
        }
        return imgUrls;
    }



    public static void setGridView(Context context,GridView gridView,ArrayList<String> imageUris) {
        GridImageAdapter adapter = new GridImageAdapter(context, R.layout.layout_grid_imageview, "", imageUris);
        gridView.setAdapter(adapter);
    }




}
