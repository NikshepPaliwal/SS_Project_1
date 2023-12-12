package com.nikshep_nielit.ss_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_in_app extends AppCompatActivity {

    private EditText editTextemail, editTextPassword;
    Button buttonLogin;

    private FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in_app);
        TextView registerbtn, forget_password;
        registerbtn = findViewById(R.id.registerbtn);
        editTextemail = findViewById(R.id.editTextemail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        auth = FirebaseAuth.getInstance();
        forget_password = findViewById(R.id.forget_password);



        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget_password = new Intent(login_in_app.this, forgot_password.class);
                startActivity(forget_password);
            }
        });


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerbtn = new Intent(login_in_app.this, registration_in_app.class);
                startActivity(registerbtn);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextemail.getText().toString();
                String pass = editTextPassword.getText().toString();

                if ((user.length() == 0 || user == null) || (pass.length() == 0 || pass == null)) {
                    Toast.makeText(login_in_app.this, "please fill the all details.", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(user, pass);
                }

            }
        });


    }

    private void loginUser(String user, String pass) {


        auth.signInWithEmailAndPassword(user, pass).
                addOnCompleteListener(login_in_app.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login_in_app.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(login_in_app.this, MainActivity.class));
                            finish();
                        } else {
                            editTextemail.setText("");
                            editTextPassword.setText("");
                            Toast.makeText(login_in_app.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }
}