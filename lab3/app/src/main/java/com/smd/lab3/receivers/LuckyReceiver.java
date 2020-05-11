package com.smd.lab3.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class LuckyReceiver extends BroadcastReceiver {

    private static final String TAG = "LuckyReceiver";
    public static final String ACTION_LUCKY_RESP = "com.smd.luckyserviceresp";
    public static final String EXTRA_LUCKY_RESULT = "com.smd.luckyresult";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "on receive");
        if (intent.getAction() != null && intent.getAction().equals(ACTION_LUCKY_RESP)) {
            Log.d(TAG, "on receive " + intent.getIntExtra(EXTRA_LUCKY_RESULT, 0));

            Toast.makeText(context,
                "You won " +
                String.valueOf(intent.getIntExtra(EXTRA_LUCKY_RESULT,0)) +
                " euro",
                Toast.LENGTH_LONG)
                .show();
        }
    }
}
