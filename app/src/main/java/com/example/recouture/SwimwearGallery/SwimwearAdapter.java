package com.example.recouture.SwimwearGallery;

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

public class SwimwearAdapter extends GenericGalleryAdapter<Swimwear,OnRecyclerClickListener<Swimwear>, SwimwearAdapter.SwimwearViewHolder> {


    public SwimwearAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "ShoesAdapter";

    @Override
    public SwimwearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SwimwearViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class SwimwearViewHolder extends BaseViewHolder<Swimwear, OnRecyclerClickListener<Swimwear>> {

        public SwimwearViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Swimwear item, @Nullable final OnRecyclerClickListener<Swimwear> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,SwimwearViewHolder.this);
                    }
                });
            }
        }
    }
}

