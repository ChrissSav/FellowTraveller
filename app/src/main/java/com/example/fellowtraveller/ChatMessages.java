package com.example.fellowtraveller;

public class ChatMessages {
    private String message, type;
    private Boolean seen;
    private long time;
    private String from,to;




    public ChatMessages(String aMessage, Boolean aSeen, long aTime, String aType, String aFrom,String aTo){

        this.message = aMessage;
        this.seen = aSeen;
        this.time = aTime;
        this.type = aType;
        this.from = aFrom;
        this.to = aTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    public ChatMessages(){

    }
}