package com.stjohns.stormchat.Objects.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by nick on 11/6/17.
 */

public class CurrentUser extends User {

    private FirebaseUser currUser;
    private boolean userIsCurrent = false;
        public CurrentUser(FirebaseAuth userAuth, User user)
        {
            super(user.getName(),user.getEmail(),user.getImagePath(),user.getBio(),user.getUUID(), user.getMajor(), user.getCollege());
            DatabaseReference userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userAuth.getCurrentUser().getUid());
            DataSnapshot snapshot;
//        super(userAuth, new User (snapshot.child("username").getValue(String.class)));
            currUser = userAuth.getCurrentUser();
            if (currUser.getUid() == user.getUUID())
            {
                userIsCurrent = true;
            }
        }

    public boolean isUserIsCurrent()
    {
        return userIsCurrent;
    }
}
