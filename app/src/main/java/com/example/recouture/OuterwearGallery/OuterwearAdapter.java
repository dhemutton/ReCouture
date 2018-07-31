package com.example.recouture.OuterwearGallery;

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

public class OuterwearAdapter extends GenericGalleryAdapter<Outerwear,OnRecyclerClickListener<Outerwear>, OuterwearAdapter.OuterwearViewHolder> {


    public OuterwearAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "OuterwearAdapter";

    @Override
    public OuterwearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OuterwearViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class OuterwearViewHolder extends BaseViewHolder<Outerwear, OnRecyclerClickListener<Outerwear>> {

        public OuterwearViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Outerwear item, @Nullable final OnRecyclerClickListener<Outerwear> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,OuterwearViewHolder.this);
                    }
                });
            }
        }
    }
}

