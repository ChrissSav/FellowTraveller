package com.example.fellowtraveller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Trip implements Parcelable {

    private String ffrom;
    private String tto;
    private String date;
    private String time;
    private List<User> creator;
    private List<User> passengers;
    private String description;
    private int max_seats;
    private int current_num_of_seats;
    private int max_bags;
    private int current_num_of_bags;
    private int max_suitcases;
    private int current_num_of_suitcases;
    private Double rate;
    private String state;


    protected Trip(Parcel in) {
        ffrom = in.readString();
        tto = in.readString();
        date = in.readString();
        time = in.readString();
        creator = in.createTypedArrayList(User.CREATOR);
        passengers = in.createTypedArrayList(User.CREATOR);
        description = in.readString();
        max_seats = in.readInt();
        current_num_of_seats = in.readInt();
        max_bags = in.readInt();
        current_num_of_bags = in.readInt();
        max_suitcases = in.readInt();
        current_num_of_suitcases = in.readInt();
        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readDouble();
        }
        state = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ffrom);
        dest.writeString(tto);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeTypedList(creator);
        dest.writeTypedList(passengers);
        dest.writeString(description);
        dest.writeInt(max_seats);
        dest.writeInt(current_num_of_seats);
        dest.writeInt(max_bags);
        dest.writeInt(current_num_of_bags);
        dest.writeInt(max_suitcases);
        dest.writeInt(current_num_of_suitcases);
        if (rate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rate);
        }
        dest.writeString(state);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public String getFfrom() {
        return ffrom;
    }

    public void setFfrom(String ffrom) {
        this.ffrom = ffrom;
    }

    public String getTto() {
        return tto;
    }

    public void setTto(String tto) {
        this.tto = tto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<User> getCreator() {
        return creator;
    }

    public void setCreator(List<User> creator) {
        this.creator = creator;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax_seats() {
        return max_seats;
    }

    public void setMax_seats(int max_seats) {
        this.max_seats = max_seats;
    }

    public int getCurrent_num_of_seats() {
        return current_num_of_seats;
    }

    public void setCurrent_num_of_seats(int current_num_of_seats) {
        this.current_num_of_seats = current_num_of_seats;
    }

    public int getMax_bags() {
        return max_bags;
    }

    public void setMax_bags(int max_bags) {
        this.max_bags = max_bags;
    }

    public int getCurrent_num_of_bags() {
        return current_num_of_bags;
    }

    public void setCurrent_num_of_bags(int current_num_of_bags) {
        this.current_num_of_bags = current_num_of_bags;
    }

    public int getMax_suitcases() {
        return max_suitcases;
    }

    public void setMax_suitcases(int max_suitcases) {
        this.max_suitcases = max_suitcases;
    }

    public int getCurrent_num_of_suitcases() {
        return current_num_of_suitcases;
    }

    public void setCurrent_num_of_suitcases(int current_num_of_suitcases) {
        this.current_num_of_suitcases = current_num_of_suitcases;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSeatesStatus(){
        String s = current_num_of_bags+"/"+max_seats;
        return s;
    }

    public String getSuitcasesStatus(){
        String s = current_num_of_suitcases+"/"+max_suitcases;
        return s;
    }

}
