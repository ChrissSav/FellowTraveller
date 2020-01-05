package com.example.fellowtraveller;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAuth implements Parcelable {
    private int id;
    private String name;
    private String email;
    private String picture;


    protected UserAuth(Parcel in) {
        id = in.readInt();
        name = in.readString();
        email = in.readString();
        picture = in.readString();
    }

    public static final Creator<UserAuth> CREATOR = new Creator<UserAuth>() {
        @Override
        public UserAuth createFromParcel(Parcel in) {
            return new UserAuth(in);
        }

        @Override
        public UserAuth[] newArray(int size) {
            return new UserAuth[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(picture);
    }
}
