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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    Message mess;
    private ArrayList<String> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<Message> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        if (getIntent().getStringExtra("chatID") != null){
            chatID = getIntent().getStringExtra("chatID");
        }
        else
            chatID = "0000";

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Messages");

        result = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setStackFromEnd(true);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lin);
        adapter = new UserAdapter(result);
        recyclerView.setAdapter(adapter);
        updateList();
//        mDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                list.add(dataSnapshot.child("Content").getValue().toString());
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            }
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                list.remove(dataSnapshot.getValue(String.class));
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });


        userMessage = (EditText) findViewById(R.id.editMessage);
//        date = (TextView) findViewById(R.id.date);
//        user = (TextView) findViewById(R.id.userName);
    }


    public void sendButtonClicked(View view) {
        final String messageValue = userMessage.getText().toString().trim();

        final String date =  DateFormat.getDateTimeInstance().format(new Date());
        final String userName = "Maurice";
        mess = new Message(messageValue, date, userName);
        if (!TextUtils.isEmpty(messageValue)) {
            final DatabaseReference newPost = reference.push();
//            newPost.child("Content").setValue(messageValue);
//            newPost.child("Date").setValue(date);
//            newPost.child("userName").setValue(userName);
            newPost.setValue(mess);
        }
    }

    private void updateList() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String content = ""+dataSnapshot.child("Content").getValue().toString();
//                String date = ""+dataSnapshot.child("Date").getValue();
//                String userName = ""+dataSnapshot.child("userName").getValue();
//                mess = new Message(content, date, userName);
//                result.add(new Message(content, date, userName));
                result.add(dataSnapshot.getValue(Message.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Message message = dataSnapshot.getValue(Message.class);

//                int index = getItemIndex(mess);
//
//                result.set(index, mess);
//                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Message message = dataSnapshot.getValue(Message.class);
//
//                int index = getItemIndex(message);
//
//                result.remove(index);
//                adapter.notifyItemRemoved(index);
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
