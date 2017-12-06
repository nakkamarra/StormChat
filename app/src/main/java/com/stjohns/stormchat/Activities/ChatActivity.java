package com.stjohns.stormchat.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stjohns.stormchat.R;
import java.util.ArrayList;

public class ChatActivity extends Activity
{

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> list_of_chats = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
    }
    public void onStart()
    {
        super.onStart();
}}
