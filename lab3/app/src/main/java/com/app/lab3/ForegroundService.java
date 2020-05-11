package com.app.lab3;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.smd.lab3.R;

import static com.app.lab3.App.CHANNEL_ID;

public class ForegroundService extends Service {
        MediaPlayer myPlayer;
        @Override
        public void onCreate() {
            super.onCreate();

            myPlayer = MediaPlayer.create(this, R.raw.track);
            myPlayer.setLooping(false);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            myPlayer.start();

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);

            //Build a notification
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Forground Service")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .build();
            //A notifcation HAS to be passed for the foreground service to be started.
            startForeground(1, notification);;

            return START_NOT_STICKY;

        }

        @Override
        public void onDestroy() {
            myPlayer.stop();
        }

        //Used for Bound service,At this point let's keep it null
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
