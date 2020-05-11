package com.smd.lab3.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.smd.lab3.receivers.LuckyReceiver;

import java.util.concurrent.ThreadLocalRandom;

public class LuckyIntentService extends IntentService {

    private static final String TAG = "LuckyIntentService";

    private static final String ACTION_LUCKY = "com.smd.luckyservice";

    public LuckyIntentService() {
        this(TAG);
    }

    public LuckyIntentService(String name) {
        super(name);
    }

    public static void sendLuckyTask(Context context) {
        Log.d(TAG, "Sending lucky intent");

        Intent msgIntent = new Intent(context, LuckyIntentService.class);
        msgIntent.setAction(LuckyIntentService.ACTION_LUCKY);
        context.startService(msgIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            Log.d(TAG, "Null intent.");
            return;
        }

        if (intent.getAction() != null && intent.getAction().equals(ACTION_LUCKY)) {
            int money = generateMoney();
            Log.d(TAG, "Congratulations, you won " + money + " euro.");
            Log.d(TAG, Thread.currentThread().toString());
            informUser(money);
        }
    }

    private int generateMoney() {
        return ThreadLocalRandom.current().nextInt(10, 10000);
    }

    private void informUser(int money) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(LuckyReceiver.ACTION_LUCKY_RESP);
        broadcastIntent.putExtra(LuckyReceiver.EXTRA_LUCKY_RESULT, money);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }
}