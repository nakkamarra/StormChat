package com.stjohns.stormchat.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.stjohns.stormchat.Objects.Profile.Profile;
import com.stjohns.stormchat.R;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        ProfileActivity.this.finish();
    }
}
