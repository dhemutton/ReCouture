package com.example.recouture.PantsGallery;

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

public class PantsAdapter extends GenericGalleryAdapter<Pants,OnRecyclerClickListener<Pants>, PantsAdapter.PantsViewHolder> {


    public PantsAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "PantsAdapter";

    @Override
    public PantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PantsViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class PantsViewHolder extends BaseViewHolder<Pants, OnRecyclerClickListener<Pants>> {

        public PantsViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Pants item, @Nullable final OnRecyclerClickListener<Pants> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,PantsViewHolder.this);
                    }
                });
            }
        }
    }
}

