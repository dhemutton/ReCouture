package com.example.recouture.SkirtsGallery;

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

public class SkirtAdapter extends GenericGalleryAdapter<Skirt,OnRecyclerClickListener<Skirt>, SkirtAdapter.SkirtViewHolder> {


    public SkirtAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "ShoesAdapter";

    @Override
    public SkirtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SkirtViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class SkirtViewHolder extends BaseViewHolder<Skirt, OnRecyclerClickListener<Skirt>> {

        public SkirtViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Skirt item, @Nullable final OnRecyclerClickListener<Skirt> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,SkirtViewHolder.this);
                    }
                });
            }
        }
    }
}

