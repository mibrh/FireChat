package com.mibrh.firechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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

    TextView display;
    EditText input;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String username = intent.getStringExtra(SetUsername.EXTRA_MESSAGE);

        // Initialize vars
        display = (TextView) findViewById(R.id.textView);
        input = (EditText) findViewById(editText);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("main room");

        // On Enter
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (actionId == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    myRef.push().setValue("\n" + username + ": " + input.getText().toString());
                    return true;
                }
                return false;
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                display.setText(dataSnapshot.getValue(String.class));
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
}
