package com.example.fellowtraveller;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatConversationItem {
    private String imageUrl;
    private String name;
    private boolean newMessage;
    private boolean onlineStatus;
    private Long timestamp;
    private String yourId;
    private String senderId;
    private DatabaseReference onlineRef = FirebaseDatabase.getInstance().getReference();
    private String online;

    public ChatConversationItem(String aImage, String aName, boolean sendMessage,boolean online,Long aTimestamp, String yourId, String aId){

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

    public String getName() { return name;
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
        online = "";
        onlineRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                online = dataSnapshot.child("Users").child(senderId).child("online").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(online.equals("true")){
            return onlineStatus;
        }else {
            return onlineStatus;
        }
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
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
