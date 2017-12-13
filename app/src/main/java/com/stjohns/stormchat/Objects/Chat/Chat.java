package com.stjohns.stormchat.Objects.Chat;

import com.stjohns.stormchat.Objects.Message.Message;
import com.stjohns.stormchat.Objects.User.User;

import java.util.ArrayList;

/**
 * Created by nick on 11/6/17.
 */

public class Chat {
    private ArrayList<Message> conversation;
    private ArrayList<User> members;
    private User owner;

    public Chat(ArrayList<Message> conversation, ArrayList<User> members, User owner)
    {
        this.conversation = conversation;
        this.members = members;
        this.owner = owner;
    }

    public ArrayList<Message> getmessage() {
        return conversation;
    }

    public void Messages(ArrayList<Message> conversation) {
        this.conversation = conversation;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
