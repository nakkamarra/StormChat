package com.stjohns.stormchat.Objects.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by nick on 11/6/17.
 */

public class ForeignUser extends User
{
    private FirebaseUser currUser;
    private boolean userIsForeign = false;

    public ForeignUser(FirebaseAuth userAuth, User user) {
        super(user.getName(),user.getEmail(),user.getImagePath(),user.getBio(),user.getUUID());
        currUser = userAuth.getCurrentUser();
        if (currUser.getUid() != user.getUUID()){
            userIsForeign = true;
        }
    }

    public boolean isUserIsForeign() {
        return userIsForeign;
    }
}
