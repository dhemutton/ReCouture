package com.example.recouture.Friends;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.recouture.R;
import com.example.recouture.utils.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchFriendsAdapter extends RecyclerView.Adapter<SearchFriendsAdapter.UserViewHolder> {


    private static final String TAG = "SearchFriendsAdapter";
    /**
     * Adapter class that
     */


    public void clear() {
        usersList.clear();
        notifyDataSetChanged();
    }

    private Context context;

    private List<User> usersList;

    public SearchFriendsAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.friend_view_layout, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, int position) {
        Log.i(TAG,"onBind");
        User user = usersList.get(position);
        holder.displayName.setText(user.getDisplayname());
        Glide.with(context).load(user.getImage_path()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView displayName;

        CircleImageView imageView;




        public UserViewHolder(View itemView) {
            super(itemView);

            displayName = itemView.findViewById(R.id.displayName);

            imageView = itemView.findViewById(R.id.userProfilePic);


        }
    }

}
