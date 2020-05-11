package com.app.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class Firebase_signup extends AppCompatActivity implements View.OnClickListener  {

    EditText et_email, et_pass, et_confirm_pass, et_firstname, et_lastname, et_number;
    Button Register;
    TextView forgotpass,login,open;
    FirebaseAuth Signup;
    ProgressBar progressBar;

    private static String emailPattern = "[a-zA-Z+{n}0-9._-]+@[a-z]+\\.+[a-z]+";
    private static String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}";
    private String Email;
    private String Password;

    private String signupFirsttime = "true";


    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_signup);
        findViewById();
        setOnclickListner();
        Signup = FirebaseAuth.getInstance();




    }


            private void setOnclickListner() {
                Register.setOnClickListener(this);
                login.setOnClickListener(this);


            }

            private void findViewById() {
                et_email = findViewById(R.id.et_email);
                et_pass = findViewById(R.id.et_pass);
                et_confirm_pass = findViewById(R.id.et_confirm_pass);
                Register = findViewById(R.id.Register);
                progressBar = findViewById(R.id.progressBar);
                login = findViewById(R.id.tv_login);

            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Register:
                        if (et_email.getText().toString().trim().isEmpty()) {
                            Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show();
                            et_email.requestFocus();
                            return;

                        } else if (!et_email.getText().toString().matches(emailPattern)) {
                            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_LONG).show();
                            et_email.requestFocus();
                            return;
                        } else if (et_pass.getText().toString().trim().isEmpty()) {
                            Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
                            et_pass.requestFocus();
                            return;
                        } else if (et_pass.getText().toString().trim().length() < 6) {
                            Toast.makeText(this, "Password must be atleast 6 character", Toast.LENGTH_LONG).show();
                            et_pass.requestFocus();
                            return;
                        } else if (!et_pass.getText().toString().matches(passwordPattern)) {
                            Toast.makeText(this, "Password must contain atleast 6 character long with one uppercase, one lowercase, one digit.", Toast.LENGTH_LONG).show();
                            et_pass.requestFocus();
                            return;
                        } else if (et_confirm_pass.getText().toString().trim().isEmpty()) {
                            Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
                            et_confirm_pass.requestFocus();
                            return;
                        } else if (!et_pass.getText().toString().equals(et_confirm_pass.getText().toString())) {
                            Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show();
                            et_pass.requestFocus();
                            return;
                        } else {
                            Email = et_email.getText().toString();
                            Password = et_pass.getText().toString();

                            progressBar.setVisibility(View.VISIBLE);
                            SignUpFirebase();
                        }
                        break;


                    case R.id.tv_login:
                        startActivity(new Intent(Firebase_signup.this, Firebase_login.class));
                        finish();
                        break;



                }
            }

            private void SignUpFirebase() {
                Signup.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(Firebase_signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Firebase_signup.this, "Sign Up successfull  " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Firebase_signup.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    startActivity(new Intent(Firebase_signup.this, mainmenu.class));

                                }
                            }
                        });
            }




            @Override
            protected void onResume() {
                super.onResume();
                progressBar.setVisibility(View.GONE);
            }
        }

