package com.example.recouture.ShortsGallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.recouture.R;
import com.example.recouture.utils.BaseViewHolder;
import com.example.recouture.utils.GenericGalleryAdapter;
import com.example.recouture.utils.OnRecyclerClickListener;

public class ShortsAdapter extends GenericGalleryAdapter<Shorts,OnRecyclerClickListener<Shorts>, ShortsAdapter.ShortsViewHolder> {


    public ShortsAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "ShoesAdapter";

    @Override
    public ShortsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShortsViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class ShortsViewHolder extends BaseViewHolder<Shorts, OnRecyclerClickListener<Shorts>> {

        public ShortsViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Shorts item, @Nullable final OnRecyclerClickListener<Shorts> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,ShortsViewHolder.this);
                    }
                });
            }
        }
    }
}

