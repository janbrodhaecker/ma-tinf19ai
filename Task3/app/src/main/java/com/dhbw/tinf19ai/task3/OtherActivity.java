package com.dhbw.tinf19ai.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {

    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        // get message from intent with specified key
        String intentMessage = getIntent().getStringExtra(MainActivity.MESSAGE_KEY);

        // format string with the label from strings.xml
        String formattedMessage = String.format(getResources().getString(R.string.label_message), intentMessage);

        this.tvMessage = findViewById(R.id.tv_message);
        this.tvMessage.setText(formattedMessage);
    }
}