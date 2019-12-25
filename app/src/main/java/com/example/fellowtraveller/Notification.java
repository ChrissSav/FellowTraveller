package com.example.fellowtraveller;

public class Notification {
    private int id;
    private UserB user;
    private TripB trip;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
