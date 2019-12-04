package com.example.fellowtraveller;

import android.os.Parcel;
import android.os.Parcelable;

public class Search_item_filter implements Parcelable {
    private String from;
    private String to;
    private String date_start;
    private String date_end;
    private String time_start;
    private String time_end;


    public Search_item_filter(String from, String to, String date_start, String date_end, String time_start, String time_end) {
        this.from = from;
        this.to = to;
        this.date_start = date_start;
        this.date_end = date_end;
        this.time_start = time_start;
        this.time_end = time_end;
    }

    protected Search_item_filter(Parcel in) {
        from = in.readString();
        to = in.readString();
        date_start = in.readString();
        date_end = in.readString();
        time_start = in.readString();
        time_end = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(date_start);
        dest.writeString(date_end);
        dest.writeString(time_start);
        dest.writeString(time_end);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }
}
