package com.mibrh.firechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SetUsernameActivity extends AppCompatActivity {
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_username);

        editText = (EditText) findViewById(R.id.username_field);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                    if (event.getAction() == KeyEvent.ACTION_DOWN){
                        // Perform action on key press
                        usernameSelected(null);
                        return true;
                    }
                return false;
            }
        });
    }

    public void usernameSelected(View view){
        String username = editText.getText().toString();

        // TODO:
        // Validate username (i.e. check for whitespaces)

        if (username.isEmpty()) {
            Toast.makeText(this, "Username not valid", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_MESSAGE, editText.getText().toString());
            startActivity(intent);
        }
    }
}
