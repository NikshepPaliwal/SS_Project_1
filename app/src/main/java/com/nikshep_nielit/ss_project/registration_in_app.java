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

public class registration_in_app extends AppCompatActivity {

    EditText editTextemail,editTextPassword,confirmPassword;
    Button buttonRegister;
    TextView forget_password, loginpage;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_in_app);
        editTextemail=(EditText) findViewById(R.id.editTextemail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
        buttonRegister=findViewById(R.id.buttonreg);
        auth= FirebaseAuth.getInstance();
        forget_password = findViewById(R.id.forget_password);
        loginpage = findViewById(R.id.loginpage);

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget_password = new Intent(registration_in_app.this, forgot_password.class);
                startActivity(forget_password);
            }
        });


        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerbtn = new Intent(registration_in_app.this, login_in_app.class);
                startActivity(registerbtn);
                finish();
            }
        });


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user= editTextemail.getText().toString();
                String pass= editTextPassword.getText().toString();
                String cPass=confirmPassword.getText().toString();
                if(user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(registration_in_app.this, "Fill all fields",Toast.LENGTH_SHORT).show();
//                    cToast("Fill all fields");
                } else if (editTextPassword.length()<6) {
                    Toast.makeText(registration_in_app.this, "Password should be atleast 6 digits",Toast.LENGTH_SHORT).show();
//                    cToast("Password should be atleast 6 digits");
                }
                else if(!pass.equals(cPass)){
                    Toast.makeText(registration_in_app.this, "Password not matched", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(user,pass);
                }
            }
        });



    }

    private void registerUser(String editTextemail, String editTextPassword) {
        auth.createUserWithEmailAndPassword(editTextemail,editTextPassword).addOnCompleteListener(registration_in_app.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(registration_in_app.this, "Registration Successful",Toast.LENGTH_SHORT).show();
//                    cToast("Registration Successful");
                    startActivity(new Intent(registration_in_app.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(registration_in_app.this, "Registration failed",Toast.LENGTH_SHORT).show();
//                    cToast("Registration failed");
                }
            }
        });
    }
}
