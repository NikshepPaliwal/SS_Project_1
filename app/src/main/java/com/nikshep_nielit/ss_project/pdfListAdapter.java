package com.nikshep_nielit.ss_project;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class pdfListAdapter extends RecyclerView.Adapter<pdfListAdapter.ViewHolder>{
    ArrayList<StorageReference> list;
    Context context;

    pdfListAdapter(Context context, ArrayList<StorageReference> list) {

        this.context = context;
        this.list=list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_for_pdf_lidt,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull pdfListAdapter.ViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.pdfName.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(list.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        TextView pdfName;
//      ImageView download;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           pdfName =itemView.findViewById(R.id.pdfName);
//           download = itemView.findViewById(R.id.download);


        }
    }
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference= storage.getReference();
    private void downloadPdf(StorageReference item) {
        StorageReference pdfRef = reference.child("pdfs");

        // Specify local file path where the downloaded file will be saved
        File localFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), item.getName() + ".pdf");

        pdfRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // File downloaded successfully
                Toast.makeText(context, "PDF downloaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(context, "Failed to download PDF", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
