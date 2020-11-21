package com.dhbw.tinf19ai.task6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryLevelReceiver extends BroadcastReceiver {

    public interface ResultCallback {
        void onBatteryLevelChanged(int percentage);
    }

    private ResultCallback resultCallback;

    public BatteryLevelReceiver(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        if (this.resultCallback != null) {
            this.resultCallback.onBatteryLevelChanged(batteryLevel);
        }
    }
}
