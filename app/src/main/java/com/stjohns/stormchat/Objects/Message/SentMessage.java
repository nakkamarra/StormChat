package com.stjohns.stormchat.Objects.Message;

import com.stjohns.stormchat.Objects.User.User;

/**
 * Created by nick on 11/6/17.
 */

public class SentMessage extends Message {
    private User sender;

    public SentMessage(String message, String time, User sender){
        super(message, time);
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
