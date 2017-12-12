package com.stjohns.stormchat.Activities;

/**
 * Created by mbill740 on 12/2/2017.
 */


public class Message {
    private String content, date, user;

    public Message() {

    }

    public Message(String content, String date, String user) {
        this.content = content;
        this.date = date;
        this.user = user;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

