package com.stjohns.stormchat.Objects.Chat;

import com.stjohns.stormchat.Objects.Message.Message;
import com.stjohns.stormchat.Objects.User.User;

import java.util.ArrayList;

/**
 * Created by nick on 11/6/17.
 */

public class DirectChat extends Chat {

    public DirectChat(User initiator, User chattedUser){
        super(new ArrayList<Message>(), new ArrayList<User>(2), initiator);
        this.getMembers().add(0, initiator);
        this.getMembers().add(1, chattedUser);
    }
}
