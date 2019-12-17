package com.example.fellowtraveller;

public class Notification {
    private int id;
    private UserB user;
    private TripB trip;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserB getUser() {
        return user;
    }

    public void setUser(UserB user) {
        this.user = user;
    }

    public TripB getTrip() {
        return trip;
    }

    public void setTrip(TripB trip) {
        this.trip = trip;
    }
}
