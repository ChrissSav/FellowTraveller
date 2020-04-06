package com.example.fellowtraveller;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class GlobalClass extends Application {
    private String name;
    private String email;
    private int id;
    private String user_icon = "null";
    private ArrayList<TripB> ListOfTrips_to_SearchFragment = new ArrayList<>();
    private ArrayList<TripB> ListOfTrips_to_ProsforesFragment = new ArrayList<>();


    public ArrayList<TripB> getListOfTrips_to_ProsforesFragment() {
        return ListOfTrips_to_ProsforesFragment;
    }

    public void setListOfTrips_to_ProsforesFragment(ArrayList<TripB> listOfTrips_to_ProsforesFragment) {
        ListOfTrips_to_ProsforesFragment = listOfTrips_to_ProsforesFragment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<TripB> getListOfTrips_to_SearchFragment() {
        return ListOfTrips_to_SearchFragment;
    }

    public void setListOfTrips_to_SearchFragment(ArrayList<TripB> listOfTrips_to_SearchFragment) {
        ListOfTrips_to_SearchFragment = listOfTrips_to_SearchFragment;
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

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }
}
