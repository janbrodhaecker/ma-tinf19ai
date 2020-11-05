package com.dhbw.tinf19ai.task5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static android.text.TextUtils.isEmpty;

public class AddressInputFragment extends Fragment implements View.OnClickListener {

    public interface ResultCallback {
        void onResult(String result);
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ResultCallback resultCallback;
    private Button btnGo;
    private EditText etAddressInput;

    public AddressInputFragment(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address_input, container, false);
        this.btnGo = view.findViewById(R.id.btn_go);
        this.btnGo.setOnClickListener(this);

        this.etAddressInput = view.findViewById(R.id.et_address_input);
        return  view;
    }

    @Override
    public void onClick(View v) {
        String input = this.etAddressInput.getText().toString();
        if (!isEmpty(input) && (this.resultCallback != null)) {
            this.resultCallback.onResult(input);
        }
    }
}