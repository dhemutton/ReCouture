package com.example.recouture.utils;

import android.view.View;

import com.example.recouture.Item;

public interface OnRecyclerClickListener<T> extends BaseRecyclerListener {


    public void onItemClicked(View itemView, T item);

}
