package com.stjohns.stormchat.Objects.Chat;

import com.stjohns.stormchat.Objects.Message.Message;
import com.stjohns.stormchat.Objects.User.User;

import java.util.ArrayList;

/**
 * Created by nick on 11/6/17.
 */

public class GroupChat extends Chat {
    String chatName;

    public GroupChat(String chatName, ArrayList<User> membersAtStart, User owner){
        super(new ArrayList<Message>(), membersAtStart, owner);
        this.chatName = chatName;
    }
}
