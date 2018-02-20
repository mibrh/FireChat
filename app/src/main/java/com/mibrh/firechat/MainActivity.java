package com.mibrh.firechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    List<Message> messageList = new ArrayList<>();
    RecyclerView recyclerView;
    MessageAdapter mAdapter;

    private final static String MAIN_ROOM = "main_room";

    String username;
    EditText input;
    Button send;
    DatabaseReference mainroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the username from the intent that started this activity
        username = getIntent().getStringExtra(EXTRA_MESSAGE);

        // Initialize vars
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_messages_display);
        mAdapter = new MessageAdapter(messageList, username);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        send = (Button) findViewById(R.id.sendMessageButton);
        input = (EditText) findViewById(R.id.enterText);
        mainroom = FirebaseDatabase.getInstance().getReference(MAIN_ROOM);

        // On Enter
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                    if (event.getAction() == KeyEvent.ACTION_DOWN){
                        // Perform action on key press
                        sendMessage(null);
                        return true;
                    }
                return false;
            }
        });

        mainroom.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = Message.deserialize(dataSnapshot);
                // add message to messageArrayList (array)
                messageList.add(message);
                mAdapter.notifyDataSetChanged();
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void sendMessage(View v){
        String text = input.getText().toString();

        if (text.isEmpty()){
            Toast.makeText(this, "Empty message", Toast.LENGTH_SHORT).show();
        } else {
            Message message = new Message(username, text);
            mainroom.push().setValue(message.serialize());
            input.setText("");
        }
    }
}
