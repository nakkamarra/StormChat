package com.stjohns.stormchat.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stjohns.stormchat.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class ForeignProfileActivity extends AppCompatActivity {

    private TextView user;
    ImageView userPic;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("https://stormchatsju/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        DatabaseReference text = FirebaseDatabase.getInstance().getReference().child("Users");
        setContentView(R.layout.foreign_user_profile_activity);
        TextView userCollege = findViewById(R.id.college_field);
        TextView userEmail = findViewById(R.id.userEmail);
        ImageView profileImage = findViewById(R.id.userProfileImageView);
        TextView userMajor = findViewById(R.id.major_field);
        TextView userStatus = findViewById(R.id.userProfileStatus);
        TextView userName = findViewById(R.id.profileTitle);
    }


    public void onDataChange(DataSnapshot dataSnapshot) {

        String viewCollege = dataSnapshot.child("college").getValue(String.class);
        System.out.println(viewCollege);
        String displayEmail = dataSnapshot.child("Email").getValue(String.class);
        System.out.println(displayEmail);
        String displayPic = dataSnapshot.child("imageurl").getValue(String.class);
        System.out.println(displayPic);
        String displayMajor = dataSnapshot.child("major").getValue(String.class);
        System.out.println(displayMajor);
        String displayStatus = dataSnapshot.child("status").getValue(String.class);
        System.out.println(displayStatus);
        String displayName = dataSnapshot.child("username").getValue(String.class);
        System.out.println(displayName);
        Picasso.with(ForeignProfileActivity.this).load(displayPic).placeholder(R.drawable.user).into(userPic);
    }

    public void onCancelled(DatabaseError databaseError)
    {
        Toast.makeText(ForeignProfileActivity.this, "Reading user profile has failed", Toast.LENGTH_LONG).show();

    }}



