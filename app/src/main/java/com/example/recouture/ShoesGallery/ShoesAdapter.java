package com.example.recouture.ShoesGallery;

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

public class ShoesAdapter extends GenericGalleryAdapter<Shoes,OnRecyclerClickListener<Shoes>, ShoesAdapter.ShoesViewHolder> {


    public ShoesAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "ShoesAdapter";

    @Override
    public ShoesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShoesViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class ShoesViewHolder extends BaseViewHolder<Shoes, OnRecyclerClickListener<Shoes>> {

        public ShoesViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Shoes item, @Nullable final OnRecyclerClickListener<Shoes> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,ShoesViewHolder.this);
                    }
                });
            }
        }
    }
}

