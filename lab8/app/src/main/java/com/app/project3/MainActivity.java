package com.app.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText textbox;
    public SecretKeySpec key;
    public Singleton singleton;
    public Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textbox=findViewById(R.id.edit);
        button1 = findViewById(R.id.button1);
        singleton = com.app.project3.Singleton.getInstance();

        try {
            key = new SecretKeySpec(("password").getBytes("UTF-8"), "HmacSHA256");
            singleton.setKey(key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        button1.setOnClickListener(this);
    }
    public static String sStringToHMACSHA256(String s, SecretKeySpec key)
    {
        String sEncodedString = null;
        try
        {

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);

            byte[] bytes = mac.doFinal(s.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();

            for (int i=0; i<bytes.length; i++) {
                String hex = Integer.toHexString(0xFF &  bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
        }
        catch (UnsupportedEncodingException e) {

        }
        catch(InvalidKeyException e){

        }
        catch (NoSuchAlgorithmException e) {

        }
        return sEncodedString ;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button1:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("TEXT", textbox.getText().toString());
                intent.putExtra("HMAC", sStringToHMACSHA256(textbox.getText().toString(),key));
                startActivity(intent);
                break;
        }
    }
}
