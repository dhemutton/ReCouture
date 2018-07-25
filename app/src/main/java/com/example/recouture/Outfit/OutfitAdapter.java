package com.example.recouture.Outfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.R;

import java.util.ArrayList;

public class OutfitAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Outfit> outfits;

    // 1
    public OutfitAdapter(Context context, ArrayList<Outfit> outfits) {
        this.mContext = context;
        this.outfits = outfits;
    }

    // 2
    @Override
    public int getCount() {
        return outfits.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return outfits.get(position);
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Outfit outfit = outfits.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.outfit_forgrid, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.emptyimage);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.outfitname);

        // 4
        nameTextView.setText(outfit.getmName());
        Glide.with(mContext).load(outfit.getmImageUrl()).into(imageView);

        return convertView;
    }

}

