package com.stjohns.stormchat.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stjohns.stormchat.R;
import com.squareup.picasso.Picasso;


public class ForeignProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.foreign_user_profile_activity);
        TextView userName = findViewById(R.id.profileTitle);
        ImageView profileImage =findViewById(R.id.userProfileImageView);
        TextView userStatus = findViewById(R.id.userProfileStatus);
        TextView userCollege = findViewById(R.id.college_field);
        TextView userMajor = findViewById(R.id.major_field);

//        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
//
//
//       public void onDataChange(DataSnapshot dataSnapshot){
//           DataSnapshot displayName = child("Email").getValue().toString();

    }


    }



}
