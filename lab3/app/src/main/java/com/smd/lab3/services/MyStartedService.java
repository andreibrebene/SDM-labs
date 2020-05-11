package com.smd.lab3.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class MyStartedService extends Service {

    private final Random random = new Random();

    public static final String TAG = MyStartedService.class.getName();

    public MyStartedService() {
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MyStartedService.class);
    }

    // It is used when the service is first instantiated
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate called on started service: thread=" + Thread.currentThread().getName());
    }

    // Called automatically
    // For Started Service
    // Started with startService()
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand called on started service: thread=" + Thread.currentThread().getName());
        Toast.makeText(this, getBitsSaved(), Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind called on started service: thread=" + Thread.currentThread().getName());
        return null; // Because it's a started service
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy called on started service: thread=" + Thread.currentThread().getName());
        super.onDestroy();
    }

    public String getBitsSaved() {
        return "Bits saved: " + random.nextInt(1000) + ".";
    }
}
