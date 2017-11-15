package com.stjohns.stormchat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stjohns.stormchat.Objects.User.User;
import com.stjohns.stormchat.R;

public class HomeActivity extends AppCompatActivity {

    private User operatingUser;
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
                Intent whereToGo =  new Intent();
                switch (title) {
                    case "Profile":
                        whereToGo = new Intent(HomeActivity.this, ProfileActivity.class);
                        break;
                    case "Search":
                        whereToGo = new Intent(HomeActivity.this, SearchActivity.class);
                        break;
                    case "Settings":
                        break;
                    case "Log Out":
                        userAuthenticator.signOut();
                        whereToGo = new Intent(HomeActivity.this, LoginActivity.class);
                        break;
                }
                startActivity(whereToGo);
                HomeActivity.this.finish();
                return true;
            }
        });

//        LinearLayout menuHeader = new LinearLayout(this);
//        menuHeader.findViewById(R.id.menu_header);
//
//        TextView navDrawProfileName = menuHeader.findViewById(R.id.menu_profile_name);
//        TextView navDrawProfileEmail = menuHeader.findViewById(R.id.menu_profile_email);
//
//        navDrawProfileName.setText(R.string.user_name);
//        navDrawProfileEmail.setText(currentUser.getEmail());



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (showMenu.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
