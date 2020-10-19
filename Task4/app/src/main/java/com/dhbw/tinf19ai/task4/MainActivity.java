package com.dhbw.tinf19ai.task4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String URL = "https://cat-fact.herokuapp.com/facts";

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Request request = new Request.Builder().url(URL).build();

        this.okHttpClient = new OkHttpClient();

        // use async method, to not block the UI thread
        this.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "Could not fetch data! Message: " + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String rawResponse = response.body().string();
                    List<String> textInfo = getTextInformation(rawResponse);
                    Log.d(TAG, "Response: " + textInfo);
                }
            }
        });
    }

    private List<String> getTextInformation(String rawResponse) {
        List<String> results = new ArrayList<>();
        try {
            JSONObject responseObject = new JSONObject(rawResponse);
            JSONArray responseArray = responseObject.getJSONArray("all");
            for (int index = 0; index < responseArray.length(); index++) {
                JSONObject tempObject = responseArray.getJSONObject(index);
                String text = tempObject.getString("text");
                results.add(text);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Could not read json data!");
        }
        return results;
    }
}