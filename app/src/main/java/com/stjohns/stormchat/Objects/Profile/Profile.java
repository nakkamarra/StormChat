package com.stjohns.stormchat.Objects.Profile;

import com.stjohns.stormchat.Objects.User.CurrentUser;
import com.stjohns.stormchat.Objects.User.User;

/**
 * Created by nick on 11/6/17.
 */

public class Profile
{

    private User user;

    public Profile(User u)
    {
        this.user = u;

    }

    public User getUser() {
        return user;
    }
    public boolean isEditable()
    {
        if(this.getUser() instanceof CurrentUser)
        {
            return true;
        }
        return false;
    }


}
