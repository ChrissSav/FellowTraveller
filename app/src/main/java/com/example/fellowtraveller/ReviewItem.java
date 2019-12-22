package com.example.fellowtraveller;

public class ReviewItem {
    private String name;
    private String date;
    private String review;

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
}
