package com.example.recouture.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by User on 7/29/2017.
 */

    public class Post {

        private String date_created;
        private String image_path;
        private String photo_id;


        public Post() {

        }

        public Post(String date_created, String image_path, String photo_id) {
            this.date_created = date_created;
            this.image_path = image_path;
            this.photo_id = photo_id;
        }


        public String getDate_created() {
            return date_created;
        }

        public void setDate_created(String date_created) {
            this.date_created = date_created;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }




        @Override
        public String toString() {
            return "Post{" + '\'' +
                    ", date_created='" + date_created + '\'' +
                    ", image_path='" + image_path + '\'' +
                    ", photo_id='" + photo_id + '\'' +
                    '}';
        }
    }