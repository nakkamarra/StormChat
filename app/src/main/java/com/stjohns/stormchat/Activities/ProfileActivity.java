package com.stjohns.stormchat.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.stjohns.stormchat.Objects.Profile.Profile;
import com.stjohns.stormchat.R;

public class ProfileActivity extends Activity {

    Profile profileInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!profileInstance.isEditable())
            setContentView(R.layout.foreign_profile_activity);
        else
            setContentView(R.layout.current_profile_activity);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        ProfileActivity.this.finish();

    }

}
