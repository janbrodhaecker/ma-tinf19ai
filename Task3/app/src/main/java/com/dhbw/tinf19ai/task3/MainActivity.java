package com.dhbw.tinf19ai.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.text.TextUtils.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MESSAGE_KEY = "message_key";

    private EditText etMessage;
    private Button btnStartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize all fields
        this.etMessage = findViewById(R.id.et_message);
        this.btnStartActivity = findViewById(R.id.btn_start_activity);
        this.btnStartActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String message = this.etMessage.getText().toString();
        if (!isEmpty(message)) {
            // to start a new activity, an Intent is required
            Intent newActivityIntent = new Intent(this, OtherActivity.class);

            // put trivial data type to the Intent
            newActivityIntent.putExtra(MESSAGE_KEY, message);
            startActivity(newActivityIntent);
        }
    }
}