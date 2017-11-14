package com.stjohns.stormchat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stjohns.stormchat.R;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth userAuthenticator;
    private DrawerLayout homeDrawer;
    private ActionBarDrawerToggle showMenu;
    private NavigationView homeNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        userAuthenticator = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = userAuthenticator.getCurrentUser();

        homeDrawer = findViewById(R.id.home_drawer);
        showMenu = new ActionBarDrawerToggle(this, homeDrawer, R.string.open, R.string.close);
        homeDrawer.addDrawerListener(showMenu);
        showMenu.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        homeNavView = findViewById(R.id.home_nav_view);
        homeNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String title = menuItem.getTitle().toString();
                switch (title) {
                    case "Profile":
                        break;
                    case "Search":
                        break;
                    case "Settings":
                        break;
                    case "Log Out":
                        userAuthenticator.signOut();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        HomeActivity.this.finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (showMenu.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
