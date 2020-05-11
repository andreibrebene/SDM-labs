package com.smd.lab3.view;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.smd.lab3.R;
import com.smd.lab3.services.MyStartedService;

public class StartedServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);
        setTitle("Started Service");

        final Button startServiceButton = findViewById(R.id.start_started_service);
        final Button stopService = findViewById(R.id.stop_started_service);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MyStartedService.getIntent(v.getContext());
                startService(intent);
            }
        });

        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MyStartedService.getIntent(v.getContext());
                stopService(intent);
            }
        });
    }
}
