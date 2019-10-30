package com.example.fellowtraveller;

public class SearchItemscontainer {
    private String date;
    private String from;
    private String to;
    private String number;
    private String name;

    public SearchItemscontainer(String from, String to, String number, String name,String  date) {
        this.from = from;
        this.to = to;
        this.number = number;
        this.name = name;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
