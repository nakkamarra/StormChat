package com.stjohns.stormchat.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stjohns.stormchat.Objects.Message.Message;
import com.stjohns.stormchat.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends Activity
{
    private String chatID;
    private EditText userMessage;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    com.stjohns.stormchat.Objects.Message.Message mess;
    private ArrayList<String> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<com.stjohns.stormchat.Objects.Message.Message> result;
    private FirebaseAuth user = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = user.getCurrentUser();
    String name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        getCurrentUser();
        if (getIntent().getStringExtra("chatID") != null){
            chatID = getIntent().getStringExtra("chatID");
        }
        else
            chatID = "0000";

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Chats").child(chatID).child("Messages");

        result = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setStackFromEnd(true);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lin);
        adapter = new MessageAdapter(result);
        recyclerView.setAdapter(adapter);
        updateList();
        userMessage = (EditText) findViewById(R.id.editMessage);
    }


    public void sendButtonClicked(View view) {
        final String messageValue = userMessage.getText().toString().trim();
        final String date =  DateFormat.getDateTimeInstance().format(new Date());
        final String userName = name2;
        mess = new com.stjohns.stormchat.Objects.Message.Message(messageValue, date, userName);

        if (!TextUtils.isEmpty(messageValue)) {
            final DatabaseReference newPost = reference.push();
            newPost.setValue(mess);
            userMessage.setText("");
            userMessage.clearFocus();
            adapter.notifyDataSetChanged();
        }
    }

    public void getCurrentUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mb = db.getReference("Users");

        mb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equalsIgnoreCase(currentUser.getUid())){
                    getUserName(dataSnapshot.child("username").getValue().toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getUserName (String name) {
        name2 = name;
        return name2;
    }

    private void updateList() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                result.add(dataSnapshot.getValue(com.stjohns.stormchat.Objects.Message.Message.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Error",""+databaseError.getMessage());
            }
        });
    }


    private int getItemIndex(Message message) {

        int index =-1;

        for (int i = 0; i<result.size(); i++) {
            if (result.get(i).getContent().equals(message.getContent()) ) {
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ChatActivity.this, HomeActivity.class));
        ChatActivity.this.finish();
    }
}
