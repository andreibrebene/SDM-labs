package com.app.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.Button;

public class mainmenu extends AppCompatActivity implements View.OnClickListener {

    private Button t1 , t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        findViewById();
        click();

    }

    private void click() {
        t1.setOnClickListener(this);
        t4.setOnClickListener(this);


    }

    private void findViewById() {
        t1 = findViewById(R.id.t1);
        t4 = findViewById(R.id.t4);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.t1:
                startActivity(new Intent(mainmenu.this, HttpURLConnectionActivity.class));
                break;
            case R.id.t4:
                startActivity(new Intent(mainmenu.this, retrofit.class));

        }

    }


}
