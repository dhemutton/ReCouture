package com.example.recouture.SleevelessGallery;

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

public class SleevelessAdapter extends GenericGalleryAdapter<Sleeveless,OnRecyclerClickListener<Sleeveless>, SleevelessAdapter.Sleeveless2ViewHolder> {


    public SleevelessAdapter(Context context) {
        super(context);
    }

    private static final String TAG = "SleevelessAdapter2";

    @Override
    public Sleeveless2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Sleeveless2ViewHolder(inflate(R.layout.single_item_shirt, parent));

    }




    class Sleeveless2ViewHolder extends BaseViewHolder<Sleeveless, OnRecyclerClickListener<Sleeveless>> {

        public Sleeveless2ViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final Sleeveless item, @Nullable final OnRecyclerClickListener<Sleeveless> listener) {
            descriptionText.setText(item.getmName());
            Glide.with(itemView.getContext()).load(item.getmImageUrl()).into(imageView);
            Log.i(TAG,"uri " + item.getmImageUrl());



            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClicked(view, item,Sleeveless2ViewHolder.this);
                    }
                });
            }
        }
    }
}

