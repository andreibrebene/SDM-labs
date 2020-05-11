package com.smd.lab3;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.smd.lab3.view.BoundServiceActivity;
import com.smd.lab3.view.ForegroundServiceActivity;
import com.smd.lab3.view.LuckyIntentServiceActivity;
import com.smd.lab3.view.StartedServiceActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.startedServiceActivity).setOnClickListener(this);
        findViewById(R.id.boundServiceActivity).setOnClickListener(this);
        findViewById(R.id.foreground_service).setOnClickListener(this);
        findViewById(R.id.start_intent_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        if (id == R.id.startedServiceActivity) {
            intent = new Intent(this, StartedServiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.boundServiceActivity) {
            intent = new Intent(this, BoundServiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.foreground_service) {
            intent = new Intent(this, ForegroundServiceActivity.class);
            startActivity(intent);
        } else if (id == R.id.start_intent_service) {
            intent = new Intent(this, LuckyIntentServiceActivity.class);
            startActivity(intent);
        }
    }
}
