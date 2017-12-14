package com.stjohns.stormchat.Activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stjohns.stormchat.R;
import com.squareup.picasso.Picasso;


public class ForeignProfileActivity extends AppCompatActivity {

    String userIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra("userID")){
            userIdentifier = getIntent().getStringExtra("userID");
        }
        else{
            userIdentifier = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        DatabaseReference text = FirebaseDatabase.getInstance().getReference().child("Users").child(userIdentifier);
        setContentView(R.layout.foreign_user_profile_activity);
        final TextView userCollege = findViewById(R.id.college_field);
        final TextView userEmail = findViewById(R.id.userEmail);
        final ImageView profileImage = findViewById(R.id.userProfileImageView);
        final TextView userMajor = findViewById(R.id.major_field);
        final TextView userStatus = findViewById(R.id.userProfileStatus);
        final TextView userName = findViewById(R.id.profileTitle);
        ImageButton returnButton = findViewById(R.id.goBack);
        ImageButton createChatButton = findViewById(R.id.createChatButton);

        createChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChatPressed();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        text.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String viewCollege = dataSnapshot.child("college").getValue(String.class);
                userCollege.setText(viewCollege);
                String displayEmail = dataSnapshot.child("email").getValue(String.class);
                userEmail.setText(displayEmail);
                String displayPic = dataSnapshot.child("imageurl").getValue(String.class);
                Picasso.with(ForeignProfileActivity.this).load(displayPic).placeholder(R.drawable.user).into(profileImage);
                String displayMajor = dataSnapshot.child("major").getValue(String.class);
                userMajor.setText(displayMajor);
                String displayStatus = dataSnapshot.child("status").getValue(String.class);
                userStatus.setText(displayStatus);
                String displayName = dataSnapshot.child("username").getValue(String.class);
                userName.setText(displayName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ForeignProfileActivity.this, "Reading user profile has failed", Toast.LENGTH_LONG).show();

            }

        });

    }

        public void onChatPressed(){
        startActivity(new Intent(ForeignProfileActivity.this, CreateChatActivity.class));
        ForeignProfileActivity.this.finish();
        }

    @Override
    public void onBackPressed(){

        startActivity(new Intent(ForeignProfileActivity.this, HomeActivity.class));
        ForeignProfileActivity.this.finish();
    }
}
