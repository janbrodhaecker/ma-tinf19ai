package com.dhbw.tinf19ai.task6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerStatusReceiver extends BroadcastReceiver {

    public interface ResultCallback {
        void isCharging(boolean charging);
    }

    private ResultCallback resultCallback;

    public PowerStatusReceiver(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
            this.resultCallback.isCharging(true);
        } else if(Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            this.resultCallback.isCharging(false);
        }
    }
}
