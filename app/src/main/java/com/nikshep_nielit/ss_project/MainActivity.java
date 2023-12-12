package com.nikshep_nielit.ss_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button testButton, logout, aboutMe;
    FirebaseUser user;
    ImageView imageView;
    Uri filePath;
    TextView url;

    private static final int PICK_PDF_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testButton = findViewById(R.id.testButton);
        logout = findViewById(R.id.logout);
        imageView = findViewById(R.id.imageView);
        aboutMe = findViewById(R.id.aboutMe);
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, aboutMe.class);
                startActivity(i);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, login_in_app.class);
            startActivity(intent);
            finish();
        }else {

            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(filePath==null){
                        Toast.makeText(MainActivity.this, "Select PDF first", Toast.LENGTH_SHORT).show();
                    }else {
                        uploadtofirebase();
                    }

                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user == null) {
                        Toast.makeText(MainActivity.this, "Please Login first.", Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseAuth.getInstance().signOut();
                        user = null;
                        if (user == null) {
                            Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Intent intent = new Intent(MainActivity.this, select_user_type.class);
                        startActivity(intent);

                    }
                }
            });
        }
    }
    public void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
              filePath = data.getData();
                // Now you have the selected file URI (uri)
                Toast.makeText(this, "Selected file: " + filePath.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadtofirebase() {
        Random random = new Random();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait....");
        progressDialog.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imgUploader= storage.getReference("pdfs").child("example"+random.nextInt());

        imgUploader.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgUploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                progressDialog.dismiss();

                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference node = db.getReference("User");
                                    FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
                                    String userId = firebaseuser.getUid();
                                    uriConstructorForRealtime realtimeUri= new uriConstructorForRealtime(uri.toString());
                                    node.child(userId).setValue(realtimeUri);

                                    Toast.makeText(MainActivity.this, "Pdf uploaded successfully", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per= (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded "+ (int)per+" %");

                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(MainActivity.this, "uploading cancel", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}