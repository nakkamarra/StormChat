package com.stjohns.stormchat.Activities;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.database.ChildEventListener;
import com.stjohns.stormchat.Objects.User.User;
import com.stjohns.stormchat.R;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class CreateChatActivity extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    FirebaseDatabase reference1, reference2=FirebaseDatabase.getInstance();

    DatabaseReference ref1 = reference1.getReference("https://stormchatsju/");
    DatabaseReference ref2 = reference2.getReference("https://stormchatsju/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_chat_activity);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.send_button);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

      //  FirebaseDatabase.setAndroidContext(this);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", User.class.getName());
                    ref1.push().setValue(map);
                    ref2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });


    }
    public void onStart()
    {
        super.onStart();
        DatabaseReference ref1 = reference1.getReference("https://stormchatsju/");
        DatabaseReference ref2 = reference2.getReference("https://stormchatsju/");
        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if (userName.equals(User.class.getName())) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(User.class.getCanonicalName() + ":-\n" + message, 2);
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

            public void onChildRemoved(DatabaseError error)
            {
                String s="bye";
            }
    });
    }

//    ImageButton returnButton = findViewById(R.id.goBack);
//        returnButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            onBackPressed();
//        }
//    });
//@Override
//public void onBackPressed(){
//
//    startActivity(new Intent(ForeignProfileActivity.this, HomeActivity.class));
//    ForeignProfileActivity.this.finish();
//}
//}

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(CreateChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.receiverbubble);
        }
        else
            {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.senderbubble);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
