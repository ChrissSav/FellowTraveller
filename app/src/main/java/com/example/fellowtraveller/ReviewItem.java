package com.example.fellowtraveller;

public class ReviewItem {
    private String name;
    private String date;
    private String review;
    private UserB user;
    private Double rate;
    private String description;

    public ReviewItem(String aName, String aDate, String aReview){

        name = aName;
        date = aDate;
        review = aReview;
    }

    public String getName() {
        return name;
    }
    public String getDate(){
        return date;
    }
    public String getReview(){
        return review;
    }
    public void setName(String aName) {
        this.name = aName;
    }
    public void setDate(String aDate) {
        this.date = aDate;
    }
    public void setReview(String aReview) {
        this.review = aReview;
    }


    public UserB getUser() {
        return user;
    }

    public void setUser(UserB user) {
        this.user = user;
    }

    public Double getRate() {
        return rate;
    }

    public void Double(Double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
