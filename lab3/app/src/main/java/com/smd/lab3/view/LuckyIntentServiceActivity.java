package com.smd.lab3.view;

import android.content.IntentFilter;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.smd.lab3.R;
import com.smd.lab3.receivers.LuckyReceiver;
import com.smd.lab3.services.LuckyIntentService;

public class LuckyIntentServiceActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LuckyReceiver luckyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_intent_service);

        Button getMoneyButton = findViewById(R.id.get_money);
        getMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuckyIntentService.sendLuckyTask(v.getContext());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        registerLuckyReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterLuckyReceiver();
    }

    private void unregisterLuckyReceiver() {
        Log.d(TAG, "Unregister lucky receiver");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(luckyReceiver);
    }

    private void registerLuckyReceiver() {
        Log.d(TAG, "Register lucky receiver");
        IntentFilter filter = new IntentFilter(LuckyReceiver.ACTION_LUCKY_RESP);
        luckyReceiver = new LuckyReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(luckyReceiver, filter);
    }
}
