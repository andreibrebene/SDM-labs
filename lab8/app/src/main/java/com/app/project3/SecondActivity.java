package com.app.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        String text=extras.getString("TEXT");
        String hmac=extras.getString("HMAC");
        TextView txtview=findViewById(R.id.text);
        TextView hmac_view=findViewById(R.id.hmac);
        txtview.setText(text);
        Singleton singleton=com.app.project3.Singleton.getInstance();
        if(sStringToHMACSHA256(text,singleton.getKey()).equals(hmac)){
            hmac_view.setText("HMAC VERIFIED");
        }
        else{
            hmac_view.setText("HMAC NOT VERIFIED");
        }
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
}
