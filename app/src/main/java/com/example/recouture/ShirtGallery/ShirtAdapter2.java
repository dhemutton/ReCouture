package com.example.recouture.ShirtGallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.recouture.Item;
import com.example.recouture.R;
import com.example.recouture.utils.BaseViewHolder;
import com.example.recouture.utils.GenericGalleryAdapter;
import com.example.recouture.utils.OnRecyclerClickListener;

import java.util.ArrayList;
import java.util.List;

public class ShirtAdapter2 extends GenericGalleryAdapter<Shirt,OnRecyclerClickListener<Shirt>,ShirtAdapter2.Shirt2ViewHolder> {


    public ShirtAdapter2(Context context) {
        super(context);
    }

    @Override
    public Shirt2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Shirt2ViewHolder(inflate(R.layout.single_item_shirt, parent));

    }




    class Shirt2ViewHolder extends BaseViewHolder<Shirt, OnRecyclerClickListener<Shirt>> {

        public Shirt2ViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Shirt item, @Nullable final OnRecyclerClickListener<Shirt> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);


            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,Shirt2ViewHolder.this);
                    }
                });
            }
        }
    }
}

