package com.example.fellowtraveller;

public class Notification {
    private int id;
    private User user;
    private Trip trip;


    public Notification(int id, User user, Trip trip) {
        this.id = id;
        this.user = user;
        this.trip = trip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
