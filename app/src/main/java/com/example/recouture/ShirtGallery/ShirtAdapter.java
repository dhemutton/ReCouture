package com.example.recouture.ShirtGallery;

import android.content.Context;
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

import java.util.List;

public class ShirtAdapter extends EmptyRecyclerView.Adapter<ShirtAdapter.ShirtViewHolder> {

    private Context mContext;
    private List<Shirt> shirts;

    public ShirtAdapter(Context mContext, List<Shirt> shirts) {
        this.mContext = mContext;
        this.shirts = shirts;
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
    }

    @Override
    public int getItemCount() {
        return shirts.size();
    }

    class ShirtViewHolder extends RecyclerView.ViewHolder {

        public TextView descriptionText;
        public ImageView imageView;

        public ShirtViewHolder(View itemView) {
            super(itemView);

            descriptionText = itemView.findViewById(R.id.textViewDescription);
            imageView = itemView.findViewById(R.id.imageViewShirt);

        }
    }
}
