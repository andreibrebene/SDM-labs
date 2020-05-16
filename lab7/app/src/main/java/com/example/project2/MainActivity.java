package com.example.project2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Enumeration;

import javax.crypto.Cipher;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SHARED_PREFS = "sharedPrefs";
    public Button savebtn,showpinbtn;
    public KeyPair pair;
    public EditText textbox;
    public TextView status;
    public  KeyStore ks;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeyguardManager km = (KeyguardManager)getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        assert km != null;
        if(km.isKeyguardSecure())
            Toast.makeText(getApplicationContext(), "Phone is Secure!", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(getApplicationContext(), "Phone is Unlocked!", Toast.LENGTH_LONG).show();
            finish();
        }
        try {
            ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            Enumeration<String> aliases = ks.aliases();
            pair = generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }

        savebtn=findViewById(R.id.save);
        showpinbtn=findViewById(R.id.show_pin);
        showpinbtn.setOnClickListener(this);
        textbox=findViewById(R.id.textbox);
        savebtn.setOnClickListener(this);
        status=findViewById(R.id.status);
    }


    public static KeyPair generateKeyPair() throws Exception{
        String alias="rsakey1";
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setKeySize(2048).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1).build();
        generator.initialize(keyGenParameterSpec);
        return generator.generateKeyPair();
    }

    public void saveData(String text){
        SharedPreferences sharedPreferences =getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("encryptedtext",text);
        editor.apply();
    }

    public String loadData(){
        String text;
        SharedPreferences sharedPreferences =getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        text=sharedPreferences.getString("encryptedtext","");
        return text;
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save:
                String message= textbox.getText().toString();
                String cipherText = null;
                try {
                    cipherText = encrypt(message, pair.getPublic());
                    saveData(cipherText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                status.setText("Encrypted Text: "+cipherText);
                Toast.makeText(getApplicationContext(),"Data Encrypted and Saved!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.show_pin:
                String decryptmessage = loadData();
                String deciphertext=null;
                try {
                    deciphertext = decrypt(decryptmessage, pair.getPrivate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                status.setText("Decrypted Text: "+deciphertext);

        }

    }
}

