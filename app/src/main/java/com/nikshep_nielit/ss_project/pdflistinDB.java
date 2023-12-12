package com.nikshep_nielit.ss_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class pdflistinDB extends AppCompatActivity {
    static ArrayList<StorageReference> pdfList=new ArrayList<StorageReference>();
    static Context context;
    static RecyclerView recyclerView;
    ImageView logoutU2;

//static myadapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflistin_db);
        recyclerView=findViewById(R.id.recyclerView);
        logoutU2 = findViewById(R.id.logoutU2);
        logoutU2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FirebaseAuth.getInstance().signOut();
                        Toast.makeText(pdflistinDB.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(pdflistinDB.this, select_user_type.class);
                        startActivity(intent);
                    }

        });
        context=getApplicationContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        GetPdfList();
        pdfList.clear();
    }

    public void GetPdfList(){


       FirebaseStorage storage = FirebaseStorage.getInstance();
       StorageReference reference= storage.getReference().child("pdfs");



        reference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        // Iterate through the list of items (files)
                        for (StorageReference item : listResult.getItems()) {
                            // Handle each item (e.g., add it to a list)
                            String pdfName = item.getName();
                            pdfList.add(item);
                            System.out.println(pdfName);
                            pdfListAdapter adapter = new pdfListAdapter(pdflistinDB.this, pdfList);
                            recyclerView.setAdapter(adapter);

                            // Add pdfName to your list or display it in the UI
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors
                    }
                });

    }

}