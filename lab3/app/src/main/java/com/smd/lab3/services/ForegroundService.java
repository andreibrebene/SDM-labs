package com.smd.lab3.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.smd.lab3.R;

public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";
    private static final String ACTION_START = "ACTION_START_FOREGROUND_SERVICE";
    private static final String ACTION_STOP = "ACTION_STOP_FOREGROUND_SERVICE";
    private static final int FOREGROUND_NOTIFICATION_ID = 2000;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ForegroundService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent getStopIntent(Context context) {
        Intent intent = new Intent(context, ForegroundService.class);
        intent.setAction(ACTION_STOP);
        return intent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    @MainThread
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_START:
                    startForegroundService();
                    break;
                case ACTION_STOP:
                    stopForegroundService();
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    @MainThread
    public void onDestroy() {
        Log.d(TAG, "Destroy service.");
    }

    private void stopForegroundService() {
        Log.d(TAG, "Stop foreground service ...");
        stopForeground(true);
        stopSelf();
    }

    private void startForegroundService() {
        Log.d(TAG, "Start foreground service ...");
        Notification notification = buildNotification();
        startForeground(FOREGROUND_NOTIFICATION_ID, notification);
    }

    private Notification buildNotification() {
        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel("foreground_service", "Foreground Service");
        }

        return new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Foreground service notification")
                .setContentText("Foreground service runs in foreground...")
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel channel;
        NotificationManager notificationManager;

        channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        return channelId;
    }
}