package com.example.recouture.BagsGallery;

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

public class BagAdapter extends GenericGalleryAdapter<Bag,OnRecyclerClickListener<Bag>, BagAdapter.BagViewHolder> {


    public BagAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "ShoesAdapter";

    @Override
    public BagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BagViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class BagViewHolder extends BaseViewHolder<Bag, OnRecyclerClickListener<Bag>> {

        public BagViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Bag item, @Nullable final OnRecyclerClickListener<Bag> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,BagViewHolder.this);
                    }
                });
            }
        }
    }
}

