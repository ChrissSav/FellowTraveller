package com.example.fellowtraveller;





import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Trip  implements Parcelable{
    private User creator;
    private String status;
    private String from;
    private String to;
    private ArrayList<User> passengers = new ArrayList<>();
    private String date;
    private String time;
    private int current_suitcases;
    private int current_seats;
    private int max_seats;
    private int max_suitcases;
    private String description;
    private String price;



    public Trip(User creator, String from, String to,
               String date, String time, int max_seats, int max_suitcases
                ,String description,String price) {
        //**************
        this.creator = creator;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.max_seats = max_seats;
        this.max_suitcases = max_suitcases;
        this.description = description;
        this.price = price;
        //**************
        this.status = "good";
        this.current_suitcases = 0;
        this.current_seats = 0;


    }


    protected Trip(Parcel in) {
        creator = in.readParcelable(User.class.getClassLoader());
        status = in.readString();
        from = in.readString();
        to = in.readString();
        passengers = in.createTypedArrayList(User.CREATOR);
        date = in.readString();
        time = in.readString();
        current_suitcases = in.readInt();
        current_seats = in.readInt();
        max_seats = in.readInt();
        max_suitcases = in.readInt();
        description = in.readString();
        price = in.readString();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//*************************
    public User getCreator() {
        return creator;
    }

//*************************



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public int getNumOfPassengers() {

            return passengers.size();
    }

    public int getMax_seats() {
        return max_seats;
    }

    public void setMax_seats(int max_seats) {
        this.max_seats = max_seats;
    }

    public int getMax_suitcases() {
        return max_suitcases;
    }

    public void setMax_suitcases(int max_suitcases) {
        this.max_suitcases = max_suitcases;
    }



    public void addPassenger(User user){
        passengers.add(user);
    }

    public String getSeatesStatus(){
        String s = current_seats+"/"+max_seats;
        return s;
    }

    public String getSuitcasesStatus(){
        String s = current_suitcases+"/"+max_suitcases;
        return s;
    }

    public void IncreaseSeats(){
        current_seats += 1;
    }
    public void IncreaseSuitcases(){
        current_suitcases += 1;
    }

    public void DecreaseSeats(){
        current_seats -= 1;
    }
    public void DecreaseSuitcases(){
        current_suitcases -= 1;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(creator, flags);
        dest.writeString(status);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeTypedList(passengers);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(current_suitcases);
        dest.writeInt(current_seats);
        dest.writeInt(max_seats);
        dest.writeInt(max_suitcases);
        dest.writeString(description);
        dest.writeString(price);
    }
}
