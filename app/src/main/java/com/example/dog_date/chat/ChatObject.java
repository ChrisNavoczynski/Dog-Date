package com.example.dog_date.chat;

import java.util.Date;

public class ChatObject {
    public String message;
    private Boolean currentUser;
    private long messageTime;

    public ChatObject(String message, Boolean currentUser){
        this.message = message;
        this.currentUser = currentUser;
        messageTime = new Date().getTime();
    }

    public ChatObject() {

    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public Boolean getCurrentUser(){
        return currentUser;
    }
    public void setCurrentUser(Boolean currentUser){
        this.currentUser = currentUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
