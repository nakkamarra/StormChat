package com.stjohns.stormchat.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stjohns.stormchat.Objects.User.User;
import com.stjohns.stormchat.R;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private User operatingUser =  new User();
    private FirebaseAuth userAuthenticator;
    private DrawerLayout homeDrawer;
    private ActionBarDrawerToggle showMenu;
    private NavigationView homeNavView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userDB = database.getReference("https://stormchatsju/").child("Users");

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
                    case "Maurice's Button":
                        whereToGo = new Intent(HomeActivity.this, ChatActivity.class);
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

        final ImageView navDrawProfileImage = homeNavView.getHeaderView(0).findViewById(R.id.menu_profile_image);
        final TextView navDrawProfileName = homeNavView.getHeaderView(0).findViewById(R.id.menu_profile_name);
        final TextView navDrawProfileEmail =  homeNavView.getHeaderView(0).findViewById(R.id.menu_profile_email);

        if (currentUser.getEmail() == null)
            navDrawProfileEmail.setText(R.string.email_default);
        else
            navDrawProfileEmail.setText(currentUser.getEmail());
            userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
            userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String displayName=dataSnapshot.child("username").getValue(String.class);
                navDrawProfileName.setText(displayName);
                String displayPic=dataSnapshot.child("imageurl").getValue(String.class);
            Picasso.with(HomeActivity.this).load(displayPic).placeholder(R.drawable.user).into(navDrawProfileImage);
            }
            @Override
            public void onCancelled(DatabaseError errorResult) {
                String error = errorResult.getMessage();
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

//    public static User getUserInDatabase(FirebaseUser currentUser) {
//        final User[] aUser = {new User()};
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userDB = database.getReference("https://stormchatsju/").child("Users");
//        userDB = FirebaseDatabase.
//                getInstance().
//                getReference("https://stormchatsju/").
//                child("Users").child(currentUser.getUid());
//        final Query query = userDB.orderByChild("ZTtTSLth6kSBLA").equalTo("ZTtTSLth6kSBLAatplWk2cCx5IN2");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Log.e("Snapshot not working", "Assignment might not be correct");
//                } else {
//                    Log.e("Snapshot doesnt exists", "Most likely error with DB connection");
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError result) {
//                String error = result.getMessage();
//            }
//        });
//        {
//
//
//        }
//        return aUser[0];
//    }
}
