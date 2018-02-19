package com.mibrh.firechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private final static String MAIN_ROOM = "main_room";
    private final static String TAG = "App info";

    String username;
    EditText input;
    Button send;
    DatabaseReference mainroom;
    ListView messagelistview;

    ArrayList<String> messageArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate started");

        // Get the username from the intent that started this activity
        username = getIntent().getStringExtra(EXTRA_MESSAGE);
        Log.d(TAG,"got username");

        // Initialize vars
        send = (Button) findViewById(R.id.sendMessageButton);
        input = (EditText) findViewById(R.id.enterText);
        messageArrayList = new ArrayList<String>();
        messagelistview = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messageArrayList);
        mainroom = FirebaseDatabase.getInstance().getReference(MAIN_ROOM);
        Log.d(TAG,"Initialized vars, got reference of firebase mainroom");

        // Adapter -> ListView
        messagelistview.setAdapter(adapter);
        Log.d(TAG,"setAdapter on messagelistview");

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
                messageArrayList.add(message.toString());
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
