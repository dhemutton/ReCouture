package com.example.recouture.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recouture.Item;
import com.example.recouture.R;

import java.util.ArrayList;
import java.util.List;


public abstract class GenericGalleryAdapter<T extends Item,L extends BaseRecyclerListener,VH extends BaseViewHolder<T,L>>
        extends RecyclerView.Adapter<VH> {

    private List<T> items;

    private L listener;

    private LayoutInflater layoutInflater;




    public GenericGalleryAdapter(Context context) {

        layoutInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T item = items.get(position);
        holder.onBind(item,listener);
    }


    public int getItemCount() {
        return items != null ? items.size() : 0;
    }


    public void setItems(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot set null item to Recycler Adapter");
        }
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    public List<T> getItems() {
        return items;
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void addItem(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to Recycler adapter");
        }
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot add null items to Recycler adapter");
        }
        this.items.addAll(items);
        notifyItemRangeInserted(this.items.size() - items.size(),items.size());
    }


    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(T item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void setListener(L listener) {
        this.listener = listener;
    }

    @NonNull
    protected View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent,
                           final boolean attachToRoot) {
        return layoutInflater.inflate(layout,parent,attachToRoot);
    }

    @NonNull
    protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent) {
        return inflate(layout,parent,false);
    }






}
