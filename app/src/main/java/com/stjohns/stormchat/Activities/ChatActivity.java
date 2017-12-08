package com.stjohns.stormchat.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stjohns.stormchat.Objects.Chat.Chat;
import com.stjohns.stormchat.Objects.User.User;
import com.stjohns.stormchat.R;

import java.util.ArrayList;

public class ChatActivity extends Activity
{

    private EditText userMessage;
    private TextView date, user;
    private DatabaseReference mDatabase;
    static ListView listview;
    static ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    static final User[] users = {new User()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        listview = (ListView) findViewById(R.id.listView);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, list);
        listview.setAdapter(adapter);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                list.add(dataSnapshot.child("Content").getValue().toString());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                list.remove(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference text = FirebaseDatabase.getInstance().getReference().child("Users");

        text.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users[0] = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        userMessage = (EditText) findViewById(R.id.editMessage);
        date = (TextView) findViewById(R.id.date);
        user = (TextView) findViewById(R.id.userName);
    }
    //
    public void sendButtonClicked(View view) {
        final String messageValue = userMessage.getText().toString().trim();
        //final String dateSent = date.getText().toString().trim();
        //final String userName = user.getText().toString().trim();
        if (!TextUtils.isEmpty(messageValue)) {
            final DatabaseReference newPost = mDatabase.push();
            newPost.child("Message").setValue(messageValue);
            //newPost.child("Date").setValue(dateSent);
            //newPost.child("Received User").setValue(userName);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.e("User", users[0].toString());
    }


    @Override
    public void onBackPressed(){
        Intent whereToGo = new Intent(ChatActivity.this, HomeActivity.class);
        ChatActivity.this.finish();
        startActivity(whereToGo);
    }
}
