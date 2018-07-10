package com.example.recouture.HomePage;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recouture.R;
import com.example.recouture.TagHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ImageViewHolder> {

    Context context;
    List<TagHolder> tagHolders;

    public SearchAdapter(Context context, List<TagHolder> tagHolders) {
        this.context = context;
        this.tagHolders = tagHolders;
    }




    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        TagHolder tagHolder = tagHolders.get(position);
        holder.name.setText(tagHolder.getName());
        Glide.with(context)
                .load(tagHolder.getDownloadImageUri())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return tagHolders.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView;


        public ImageViewHolder(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.searchItemDescription);
            imageView = itemView.findViewById(R.id.imageItemView);
        }
    }
}