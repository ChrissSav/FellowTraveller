package com.example.fellowtraveller;

public class SearchItem {
    private String from;
    private String to;
    private String name;
    private String num;

    public SearchItem(String from, String to, String name, String num) {
        this.from = from;
        this.to = to;
        this.name = name;
        this.num = num;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}

