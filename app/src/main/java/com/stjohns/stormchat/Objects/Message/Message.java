package com.stjohns.stormchat.Objects.Message;

/**
 * Created by nick on 11/6/17.
 */

public class Message {
    private String message;
    private String timestamp;

    public Message(String message, String timestamp){
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
