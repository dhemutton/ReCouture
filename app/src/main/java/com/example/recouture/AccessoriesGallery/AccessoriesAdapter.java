package com.example.recouture.AccessoriesGallery;

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

public class AccessoriesAdapter extends GenericGalleryAdapter<Accessories,OnRecyclerClickListener<Accessories>, AccessoriesAdapter.AccesoriesViewHolder> {


    public AccessoriesAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "ShoesAdapter";

    @Override
    public AccesoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccesoriesViewHolder(inflate(R.layout.single_item_shirt, parent));
    }




    class AccesoriesViewHolder extends BaseViewHolder<Accessories, OnRecyclerClickListener<Accessories>> {

        public AccesoriesViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Accessories item, @Nullable final OnRecyclerClickListener<Accessories> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,AccesoriesViewHolder.this);
                    }
                });
            }
        }
    }
}

