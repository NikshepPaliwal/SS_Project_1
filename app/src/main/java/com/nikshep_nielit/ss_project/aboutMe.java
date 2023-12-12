package com.nikshep_nielit.ss_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class aboutMe extends AppCompatActivity {
    TextView linkTextView,contactNo, linkedIn, resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        linkTextView = findViewById(R.id.linkTextView);
        contactNo = findViewById(R.id.contactNo);
//        linkedIn = findViewById(R.id.linkedIn);
        resume = findViewById(R.id.resume);
        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event, e.g., open a web browser
                Uri uri = Uri.parse("https://github.com/NikshepPaliwal");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

       contactNo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // Replace "123456789" with the actual phone number you want to call
               String phoneNumber = "tel:"+"9149188705";

               // Create an Intent to initiate a phone call
               Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));

               // Start the phone call activity
               startActivity(dialIntent);
           }
       });
//        linkedIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle the click event, e.g., open a web browser
//                Uri uri = Uri.parse("www.linkedin.com/in/nikshep-paliwal-5099971b3");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event, e.g., open a web browser
                Uri uri = Uri.parse("https://drive.google.com/file/d/1LNrgfPJlfezIsLxefkgx_CrdPgnRYwLJ/view?usp=sharing");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

    }
}