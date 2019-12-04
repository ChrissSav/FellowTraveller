package com.example.fellowtraveller;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id;
    private String name;
    private String birthday;
    private String email;
    private String password;
    private String phone;
    private Double rate;
    private int num_of_travels_offered;
    private int num_of_travels_takespart;


    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        birthday = in.readString();
        email = in.readString();
        password = in.readString();
        phone = in.readString();
        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readDouble();
        }
        num_of_travels_offered = in.readInt();
        num_of_travels_takespart = in.readInt();
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public int getNum_of_travels_offered() {
        return num_of_travels_offered;
    }

    public void setNum_of_travels_offered(int num_of_travels_offered) {
        this.num_of_travels_offered = num_of_travels_offered;
    }

    public int getNum_of_travels_takespart() {
        return num_of_travels_takespart;
    }

    public void setNum_of_travels_takespart(int num_of_travels_takespart) {
        this.num_of_travels_takespart = num_of_travels_takespart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(birthday);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(phone);
        if (rate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rate);
        }
        dest.writeInt(num_of_travels_offered);
        dest.writeInt(num_of_travels_takespart);
    }

}
