package com.smd.lab3.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smd.lab3.MainActivity;
import com.smd.lab3.R;
import com.smd.lab3.services.MyBoundService;

public class BoundServiceActivity extends AppCompatActivity {

    MyBoundService myBoundService = null;
    boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);
        setTitle("Bound Service");

        final Button getCountButton = findViewById(R.id.get_count);
        Button getCurrentDateButton = findViewById(R.id.get_current_date);

        getCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBoundService != null) {
                    Toast.makeText(
                            v.getContext(),
                            "Count: " + Integer.toString(myBoundService.getCount()),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        getCurrentDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBoundService != null) {
                    Toast.makeText(
                            v.getContext(),
                            "Current date is: " + myBoundService.getCurrentDate(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        bindToService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(serviceConnection);
        }
    }

    private void bindToService() {
        if (myBoundService == null) {
            Intent intent = MyBoundService.getIntent(this);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBoundService = ((MyBoundService.MyBoundServiceBinder) service).getServiceBinder();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myBoundService = null;
            isBound = false;
        }
    };
}
