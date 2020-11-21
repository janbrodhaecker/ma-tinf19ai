package com.dhbw.tinf19ai.task6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.dhbw.tinf19ai.task6.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements BatteryLevelReceiver.ResultCallback, PowerStatusReceiver.ResultCallback {

    private ActivityMainBinding binding;

    private BatteryLevelReceiver batteryLevelReceiver;
    private PowerStatusReceiver powerStatusReceiver;

    private int percentage;
    private boolean charging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.binding.setDataModel(new DataModel(100, false));

        this.batteryLevelReceiver = new BatteryLevelReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(this.batteryLevelReceiver, intentFilter);

        this.powerStatusReceiver = new PowerStatusReceiver(this);
        IntentFilter otherIntentFilter = new IntentFilter();
        otherIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        otherIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(this.powerStatusReceiver, otherIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.batteryLevelReceiver);
        unregisterReceiver(this.powerStatusReceiver);
    }

    @Override
    public void onBatteryLevelChanged(int percentage) {
        this.percentage = percentage;
        updateBinding();
    }

    @Override
    public void isCharging(boolean charging) {
        this.charging = charging;
        updateBinding();
    }

    private void updateBinding() {
        this.binding.setDataModel(new DataModel(this.percentage, this.charging));
    }

}