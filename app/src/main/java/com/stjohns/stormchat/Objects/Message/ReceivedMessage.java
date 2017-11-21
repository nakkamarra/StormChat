package com.stjohns.stormchat.Objects.Message;

import com.stjohns.stormchat.Objects.User.User;

/**
 * Created by nick on 11/6/17.
 */

public class ReceivedMessage extends SentMessage {
    private User receiver;

    public ReceivedMessage(String message, String time, User sender, User receiver){
        super(message, time, sender);
        this.receiver = receiver;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
