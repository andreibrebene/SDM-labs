package com.smd.lab3.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.smd.lab3.R;
import com.smd.lab3.services.ForegroundService;

import static android.os.Build.VERSION_CODES.O;

public class ForegroundServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service);
        setTitle("Foreground Service");

        findViewById(R.id.start_foreground_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ForegroundService.getStartIntent(v.getContext());

                if (isOreoOrHigher()) {
                    startForegroundService(intent);
                } else {
                    startService(intent);
                }
            }
        });

        findViewById(R.id.stop_foreground_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ForegroundService.getStopIntent(v.getContext());

                stopService(intent);
            }
        });
    }

    private Boolean isOreoOrHigher() {
        return Build.VERSION.SDK_INT >= O;
    }
}
