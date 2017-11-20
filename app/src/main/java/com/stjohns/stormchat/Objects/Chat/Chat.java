package com.stjohns.stormchat.Objects.Chat;

/**
 * Created by nick on 11/6/17.
 */

public class Chat {
    private String message;
    private String sender;
    private String recipient;

    private int mRecipientOrSenderStatus;
    public Chat()
    {

    }
    public Chat(String message, String sender, String recipient)
    {
        this.message=message;
        this.recipient=recipient;
        this.sender=sender;
    }
    public int getmRecipientOrSenderStatus()
    {
        return mRecipientOrSenderStatus;
    }
    public String getMessage()
    {
        return message;
    }
    public String getSender()
    {
        return sender;
    }
    public String getRecipient()
    {
        return recipient;
    }
}
