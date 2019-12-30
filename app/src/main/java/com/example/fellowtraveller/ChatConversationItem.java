package com.example.fellowtraveller;

public class ChatConversationItem {
    private String imageUrl;
    private String name;
    private boolean newMessage;
    private boolean onlineStatus;
    private long timestamp;
    private String yourId;
    private String senderId;

    public ChatConversationItem(String aImage, String aName, boolean sendMessage,boolean online,long aTimestamp, String yourId, String aId){

        this.imageUrl = aImage;
        this.name = aName;
        this.newMessage = sendMessage;
        this.onlineStatus = online;
        this.timestamp = aTimestamp;
        this.yourId = yourId;
        this.senderId = aId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getYourId() {
        return yourId;
    }

    public void setYourId(String yourId) {
        this.yourId = yourId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
