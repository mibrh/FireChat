package com.mibrh.firechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.id.message;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SetUsername extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_username);

        editText = (EditText) findViewById(R.id.editText1);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (actionId == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if (editText.getText().toString() != null) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, editText.getText().toString());
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
