package com.example.recouture.SweaterGallery;

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

public class SweaterAdapter extends GenericGalleryAdapter<Sweater,OnRecyclerClickListener<Sweater>, SweaterAdapter.SweaterViewHolder> {


    public SweaterAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "SweaterAdapter";

    @Override
    public SweaterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SweaterViewHolder(inflate(R.layout.single_item_shirt, parent));

    }




    class SweaterViewHolder extends BaseViewHolder<Sweater, OnRecyclerClickListener<Sweater>> {

        public SweaterViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Sweater item, @Nullable final OnRecyclerClickListener<Sweater> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,SweaterViewHolder.this);
                    }
                });
            }
        }
    }
}

