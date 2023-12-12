package com.nikshep_nielit.ss_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class select_user_type extends AppCompatActivity {
    Button user_1, user_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);
        user_1 = findViewById(R.id.user_1);
        user_2 = findViewById(R.id.user_2);

        user_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(select_user_type.this, login_in_app.class);
                startActivity(i);
            }
        });

        user_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(select_user_type.this, login_user_2.class);
                startActivity(j);
            }
        });

    }
}