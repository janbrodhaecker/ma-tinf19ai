package com.dhbw.tinf19ai.task2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.dhbw.tinf19ai.task2.databinding.ActivityMainBinding;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // uses Android Jetpack View Binding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // add onClick listener to button
        this.binding.btnSayHi.setOnClickListener(this);

        // as long there is no text, hide input label
        this.binding.tvName.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        // get text from editText
        String inputText = this.binding.etInputName.getText().toString();

        // checks if the input string is null or empty
        if (!isEmpty(inputText)) {
            // use string formatter to format label from strings.xml
            String helloText = String.format(getResources().getString(R.string.label_hello), inputText);

            // set text to label
            this.binding.tvName.setText(helloText);

            // set text label visible
            this.binding.tvName.setVisibility(View.VISIBLE);
        }
    }
}