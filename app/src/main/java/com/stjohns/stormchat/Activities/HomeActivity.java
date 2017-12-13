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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stjohns.stormchat.R;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity  {
    private ArrayList<String> chatList = new ArrayList<>();
    private DrawerLayout homeDrawer;
    private ActionBarDrawerToggle showMenu;
    private NavigationView homeNavView;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDB = database.getReference("https://stormchatsju/").child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        final FirebaseAuth userAuthenticator= FirebaseAuth.getInstance();
        final FirebaseUser currentUser = userAuthenticator.getCurrentUser();
        ListView lv = findViewById(R.id.chat_list);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_dropdown_item_1line, chatList );
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String chatItem = adapterView.getItemAtPosition(i).toString();
                String chatID = chatItem.substring(chatItem.indexOf(":")+1);
                Intent whereToGo = new Intent(HomeActivity.this, ChatActivity.class);
                whereToGo.putExtra("chatID", chatID);
                startActivity(whereToGo);
            }
        });

        userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid()).child("chats");
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    chatList.add(d.getValue() + " :" + d.getKey());
                    arrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError errorResult){
                String error = errorResult.getMessage();
                Log.e("Database Error", error);
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseAuth userAuthenticator = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = userAuthenticator.getCurrentUser();
        homeDrawer = findViewById(R.id.home_drawer);
        showMenu = new ActionBarDrawerToggle(this, homeDrawer, R.string.open, R.string.close);
        homeDrawer.addDrawerListener(showMenu);
        showMenu.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        homeNavView = findViewById(R.id.home_nav_view);
        homeNavView.bringToFront();
        homeNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String title = menuItem.getTitle().toString();
                Intent whereToGo = new Intent();
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
                    case "Sharon Button":
                        whereToGo = new Intent(HomeActivity.this, ForeignProfileActivity.class);
                        //testing foreign user by going to nicks profile
                        whereToGo.putExtra("userID", "jY0kdPHXSKMysCvQZ6OgyRzNiE23");
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
        final TextView navDrawProfileEmail = homeNavView.getHeaderView(0).findViewById(R.id.menu_profile_email);
        navDrawProfileEmail.setText(currentUser.getEmail());

        userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String displayName = dataSnapshot.child("username").getValue(String.class);
                navDrawProfileName.setText(displayName);
                String displayPic = dataSnapshot.child("imageurl").getValue(String.class);
                Picasso.with(HomeActivity.this).load(displayPic).placeholder(R.drawable.user).into(navDrawProfileImage);
            }

            @Override
            public void onCancelled(DatabaseError errorResult) {
                String error = errorResult.getMessage();
            }
        });

        ImageButton createChatButton = findViewById(R.id.messageWhite);
        createChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CreateChatActivity.class));
                HomeActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (showMenu.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

