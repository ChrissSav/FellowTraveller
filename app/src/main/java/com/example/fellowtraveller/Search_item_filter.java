package com.example.fellowtraveller;

import android.os.Parcel;
import android.os.Parcelable;

public class Search_item_filter implements Parcelable {
    private String from;
    private String to;
    private String date_from;
    private String date_to;
    private String time_from;
    private String time_to;
    private int seats_from;
    private int seats_to;
    private int bags_from;
    private int bags_to;
    private double rate_from;
    private double rate_to;
    private int price_from;
    private int price_to;



    public Search_item_filter() {
        this.date_from = 0+"";
        this.date_to = 0+"";
        this.time_from = 0+"";
        this.time_to = 0+"";
        this.seats_from = 0;
        this.seats_to = 0;
        this.bags_from = 0;
        this.bags_to = 0;
        this.rate_from = 0;
        this.rate_to = 0;
        this.price_from = 0;
        this.price_to = 0;
    }


    protected Search_item_filter(Parcel in) {
        from = in.readString();
        to = in.readString();
        date_from = in.readString();
        date_to = in.readString();
        time_from = in.readString();
        time_to = in.readString();
        seats_from = in.readInt();
        seats_to = in.readInt();
        bags_from = in.readInt();
        bags_to = in.readInt();
        rate_from = in.readDouble();
        rate_to = in.readDouble();
        price_from = in.readInt();
        price_to = in.readInt();
    }

    public static final Creator<Search_item_filter> CREATOR = new Creator<Search_item_filter>() {
        @Override
        public Search_item_filter createFromParcel(Parcel in) {
            return new Search_item_filter(in);
        }

        @Override
        public Search_item_filter[] newArray(int size) {
            return new Search_item_filter[size];
        }
    };

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

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from.replace('/','-');
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to.replace('/','-');
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    public int getSeats_from() {
        return seats_from;
    }

    public void setSeats_from(int seats_from) {
        this.seats_from = seats_from;
    }

    public int getSeats_to() {
        return seats_to;
    }

    public void setSeats_to(int seats_to) {
        this.seats_to = seats_to;
    }

    public int getBags_from() {
        return bags_from;
    }

    public void setBags_from(int bags_from) {
        this.bags_from = bags_from;
    }

    public int getBags_to() {
        return bags_to;
    }

    public void setBags_to(int bags_to) {
        this.bags_to = bags_to;
    }

    public double getRate_from() {
        return rate_from;
    }

    public void setRate_from(double rate_from) {
        this.rate_from = rate_from;
    }

    public double getRate_to() {
        return rate_to;
    }

    public void setRate_to(double rate_to) {
        this.rate_to = rate_to;
    }

    public int getPrice_from() {
        return price_from;
    }

    public void setPrice_from(int price_from) {
        this.price_from = price_from;
    }

    public int getPrice_to() {
        return price_to;
    }

    public void setPrice_to(int price_to) {
        this.price_to = price_to;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(date_from);
        dest.writeString(date_to);
        dest.writeString(time_from);
        dest.writeString(time_to);
        dest.writeInt(seats_from);
        dest.writeInt(seats_to);
        dest.writeInt(bags_from);
        dest.writeInt(bags_to);
        dest.writeDouble(rate_from);
        dest.writeDouble(rate_to);
        dest.writeInt(price_from);
        dest.writeInt(price_to);
    }
}
