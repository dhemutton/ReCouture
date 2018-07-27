package com.example.recouture.DressGallery;

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

public class DressAdapter extends GenericGalleryAdapter<Dress,OnRecyclerClickListener<Dress>, DressAdapter.DressViewHolder> {


    public DressAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "ShoesAdapter";

    @Override
    public DressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DressViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class DressViewHolder extends BaseViewHolder<Dress, OnRecyclerClickListener<Dress>> {

        public DressViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Dress item, @Nullable final OnRecyclerClickListener<Dress> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,DressViewHolder.this);
                    }
                });
            }
        }
    }
}

