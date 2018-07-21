package com.example.recouture.ShirtGallery;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShirtAdapter extends EmptyRecyclerView.Adapter<ShirtAdapter.ShirtViewHolder> {

    private Context mContext;
    private List<Shirt> shirts;
    private boolean isDeletable = false;
    private List<Shirt> toBeDeleted = new ArrayList<>();
    private boolean cancel = false;

    public ShirtAdapter(Context mContext, List<Shirt> shirts) {
        this.mContext = mContext;
        this.shirts = shirts;
    }

    public void cancelSelection(boolean isCancel) {
        this.cancel = isCancel;
    }



    @NonNull
    @Override
    public ShirtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_item_shirt,parent,false);
        return new ShirtViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShirtViewHolder holder, int position) {
        Shirt currentShirt = shirts.get(position);
        holder.descriptionText.setText(currentShirt.getmName());
        Glide.with(mContext).load(currentShirt.getmImageUrl()).into(holder.imageView);

        if (cancel) {
            holder.itemView.setBackgroundColor(Color.argb(0,0,0,0));
            if (holder.checkHolder.getVisibility() == View.VISIBLE) {
                holder.checkHolder.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return shirts.size();
    }

    class ShirtViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView descriptionText;
        public ImageView imageView;
        public boolean isSelected = false;
        public ImageView checkHolder;

        public ShirtViewHolder(View itemView) {
            super(itemView);

            descriptionText = itemView.findViewById(R.id.textViewDescription);
            imageView = itemView.findViewById(R.id.imageViewShirt);
            checkHolder = itemView.findViewById(R.id.check);
            checkHolder.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(this);

        }


        // let main activity do the deleting, have adapter define a method to return the photos
        // selected from the delete.
        @Override
        public void onClick(View view) {
            if (isDeletable) {
                if (!isSelected) {
                    toBeDeleted.add(shirts.get(getAdapterPosition()));
                    itemView.setBackgroundColor(Color.argb(50,0,0,0));
                    checkHolder.setVisibility(View.VISIBLE);
                    isSelected = true;
                } else {
                    toBeDeleted.remove(shirts.get(getAdapterPosition()));
                    itemView.setBackgroundColor(Color.argb(0,0,0,0));
                    checkHolder.setVisibility(View.INVISIBLE);
                    isSelected = false;
                }
            }
        }
    }




    public void setDeletable(boolean deletable) {
        this.isDeletable = deletable;
    }


    public List<Shirt> returnDeletables() {
        return toBeDeleted;
    }

    public void clearDeletables() {
        toBeDeleted.clear();
    }

}
