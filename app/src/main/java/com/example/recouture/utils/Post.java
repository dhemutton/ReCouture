package com.example.recouture.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by User on 7/29/2017.
 */

    public class Post implements Parcelable {

        private String date_created;
        private String image_path;
    private String photo_id;
    private String photo_name;
        private List<Like> likes;
    private String user_id;


    public Post() {

        }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public Post(String date_created, String image_path, String user_id, String photo_name, String photo_id, List<Like> likes) {
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_name = photo_name;
        this.likes = likes;
        this.photo_id = photo_id;
        this.user_id = user_id;

    }

    public Post(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public static Creator<Post> getCREATOR() {
        return CREATOR;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "date_created='" + date_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", photo_name='" + photo_name + '\'' +
                ", likes=" + likes +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }


    protected Post(Parcel in) {
        date_created = in.readString();
        image_path = in.readString();
        photo_name = in.readString();
        photo_id = in.readString();
        user_id = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date_created);
        dest.writeString(image_path);
        dest.writeString(photo_name);
        dest.writeString(photo_id);
        dest.writeString(user_id);
    }
}