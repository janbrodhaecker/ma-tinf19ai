package com.dhbw.tinf19ai.task5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements AddressInputFragment.ResultCallback {

    private FrameLayout leftContainer;
    private FrameLayout container;

    private AddressInputFragment addressInputFragment;
    private OSMFragment osmFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.osmFragment = new OSMFragment();
        this.addressInputFragment = new AddressInputFragment(this);

        this.leftContainer = findViewById(R.id.left_container);
        this.container = findViewById(R.id.container);

        if (this.leftContainer == null) {
            initializeSmallDeviceView();
        } else {
            initializeLargeDeviceView();
        }
    }

    private void initializeSmallDeviceView() {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, this.addressInputFragment, "AddressInputFragment").commitAllowingStateLoss();
    }

    private void initializeLargeDeviceView() {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, this.osmFragment, "OSMFragment").commitAllowingStateLoss();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.left_container, this.addressInputFragment, "AddressInputFragment").commitAllowingStateLoss();
    }

    @Override
    public void onResult(String result) {
        if (this.leftContainer == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.container, this.osmFragment, "OSMFragment").commitAllowingStateLoss();

            Bundle bundle = new Bundle();
            bundle.putString("result", result);
            this.osmFragment.setArguments(bundle);
        } else {
            this.osmFragment.searchAndCenterAddress(result);
        }
    }
}