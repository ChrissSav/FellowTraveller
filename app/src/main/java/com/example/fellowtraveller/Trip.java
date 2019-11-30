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

    public String getTto() {
        return tto;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<User> getCreator() {
        return creator;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public String getDescription() {
        return description;
    }

    public int getMax_seats() {
        return max_seats;
    }

    public int getCurrent_num_of_seats() {
        return current_num_of_seats;
    }

    public int getMax_bags() {
        return max_bags;
    }

    public int getCurrent_num_of_bags() {
        return current_num_of_bags;
    }

    public Double getRate() {
        return rate;
    }

    public String getState() {
        return state;
    }

    public String getSeatesStatus(){
        String s = current_num_of_seats+"/"+max_seats;
        return s;
    }
    public String getbagsStatus(){
        String s = current_num_of_bags+"/"+max_bags;
        return s;
    }


}
