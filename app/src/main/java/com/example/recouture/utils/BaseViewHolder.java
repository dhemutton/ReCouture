package com.example.recouture.utils;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.Item;
import com.example.recouture.R;

public abstract class BaseViewHolder<T,L extends BaseRecyclerListener> extends
        RecyclerView.ViewHolder {

    public TextView descriptionText;

    public ImageView imageView;

    public boolean isSelected = false;

    public ImageView checkHolder;

    public BaseViewHolder(View itemView) {
        super(itemView);
        descriptionText = itemView.findViewById(R.id.textViewDescription);
        imageView = itemView.findViewById(R.id.imageViewShirt);
        checkHolder = itemView.findViewById(R.id.check);
        checkHolder.setVisibility(View.INVISIBLE);
    }



    public abstract void onBind(T item, @Nullable L listener);


}
