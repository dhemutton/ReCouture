package com.example.recouture.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 6/26/2017.
 */

public class User implements Parcelable{

    private String user_id;
    private String email;
    private String displayname;
    private String password;
    private String description;
    private String website;
    private String image_path;


    public User(String user_id, String email, String displayname, String password, String description, String website, String image_path) {
        this.user_id = user_id;
        this.email = email;
        this.displayname = displayname;
        this.password = password;
        this.description = description;
        this.website = website;
        this.image_path = image_path;
    }

    public User() {

    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    protected User(Parcel in) {
        user_id = in.readString();
        email = in.readString();
        displayname = in.readString();
        password = in.readString();
        description = in.readString();
        website = in.readString();
        image_path = in.readString();

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", displayname='" + displayname + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", image_path='" + image_path + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(email);
        dest.writeString(displayname);
        dest.writeString(password);
        dest.writeString(description);
        dest.writeString(website);
        dest.writeString(image_path);
    }
}
