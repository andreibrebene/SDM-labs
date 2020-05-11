package com.smd.lab3.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBoundService extends Service {

    private static final String TAG = MyBoundService.class.getName();
    private final IBinder boundServiceBinder = new MyBoundServiceBinder();
    private int count;

    public MyBoundService() {
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MyBoundService.class);
    }

    // It is used when the service is first instantiated
    @Override
    public void onCreate() {
        super.onCreate();

        count = 0;

        Log.d(TAG, "onCreate called on bound service: thread=" + Thread.currentThread().getName());
    }

    // Called automatically
    // For Bound Service
    // Used when bindService()
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind called on bound service: thread=" + Thread.currentThread().getName());
        return boundServiceBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind called on bound service: thread=" + Thread.currentThread().getName());
    }

    // Used when unbindService()
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind called on bound service: thread=" + Thread.currentThread().getName());
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy called on bound service: thread=" + Thread.currentThread().getName());
        super.onDestroy();
    }

    public int getCount() {
        return count++;
    }

    public String getCurrentDate() {
        return SimpleDateFormat.getDateInstance().format(new Date());
    }
    public class MyBoundServiceBinder extends Binder {
        public MyBoundService getServiceBinder() {
            return MyBoundService.this;
        }
    }
}
