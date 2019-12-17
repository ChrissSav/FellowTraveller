package com.example.fellowtraveller;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

public class TripB implements Parcelable {

    private String ffrom;
    private String tto;
    private String date;
    private String time;
    private UserB creator;
    private List<UserB> passengers;
    private String description;
    private int max_seats;
    private int current_num_of_seats;
    private int max_bags;
    private int current_num_of_bags;
    private Double rate;
    private String state;

    public TripB(String ffrom, String tto, String date, String time, UserB creator, List<UserB> passengers, String description, int max_seats,
                 int current_num_of_seats, int max_bags, int current_num_of_bags, Double rate, String state) {
        this.ffrom = ffrom;
        this.tto = tto;
        this.date = date;
        this.time = time;
        this.creator = creator;
        this.passengers = passengers;
        this.description = description;
        this.max_seats = max_seats;
        this.current_num_of_seats = current_num_of_seats;
        this.max_bags = max_bags;
        this.current_num_of_bags = current_num_of_bags;
        this.rate = rate;
        this.state = state;
    }

    protected TripB(Parcel in) {
        ffrom = in.readString();
        tto = in.readString();
        date = in.readString();
        time = in.readString();
        creator = in.readParcelable(UserB.class.getClassLoader());
        passengers = in.createTypedArrayList(UserB.CREATOR);
        description = in.readString();
        max_seats = in.readInt();
        current_num_of_seats = in.readInt();
        max_bags = in.readInt();
        current_num_of_bags = in.readInt();
        if (in.readByte() == 0) {
            rate = null;
        } else {
            rate = in.readDouble();
        }
        state = in.readString();
    }

    public static final Creator<TripB> CREATOR = new Creator<TripB>() {
        @Override
        public TripB createFromParcel(Parcel in) {
            return new TripB(in);
        }

        @Override
        public TripB[] newArray(int size) {
            return new TripB[size];
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

    public UserB getCreator() {
        return creator;
    }

    public void setCreator(UserB creator) {
        this.creator = creator;
    }

    public List<UserB> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<UserB> passengers) {
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
        String s = current_num_of_seats+"/"+max_seats;
        Log.i("makis",s);
        return s;
    }
    public String getbagsStatus(){
        String s = current_num_of_bags+"/"+max_bags;
        return s;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ffrom);
        dest.writeString(tto);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeParcelable(creator, flags);
        dest.writeTypedList(passengers);
        dest.writeString(description);
        dest.writeInt(max_seats);
        dest.writeInt(current_num_of_seats);
        dest.writeInt(max_bags);
        dest.writeInt(current_num_of_bags);
        if (rate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rate);
        }
        dest.writeString(state);
    }
}
