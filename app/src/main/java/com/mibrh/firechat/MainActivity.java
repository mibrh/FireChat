package com.mibrh.firechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static com.mibrh.firechat.R.id.editText;

public class MainActivity extends AppCompatActivity {
    private final static String MAIN_ROOM = "main_room";

    String username;
    TextView display;
    EditText input;
    DatabaseReference mainroom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Intent that started this activity and extract the string
        username = getIntent().getStringExtra(EXTRA_MESSAGE);

        // Initialize vars
        display = (TextView) findViewById(R.id.textView);
        input = (EditText) findViewById(editText);
        mainroom = FirebaseDatabase.getInstance().getReference(MAIN_ROOM);

        // On Enter
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                    if (event.getAction() == KeyEvent.ACTION_DOWN){
                        // Perform action on key press
                        Message message = new Message(username, input.getText().toString());
                        mainroom.push().setValue(message.serialize());
                        input.setText("");
                        return true;
                    }
                return false;
            }
        });

        mainroom.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = Message.deserialize(dataSnapshot);
                display.append(message.toString() + "\n");
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
