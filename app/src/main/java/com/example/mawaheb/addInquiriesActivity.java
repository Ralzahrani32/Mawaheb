package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class addInquiriesActivity extends AppCompatActivity {
    EditText title;
    EditText details;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inquiries);
        getSupportActionBar().setTitle("Add Inquiry");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(title.getText())){
                    title.setError("Enter title");
                }else if(TextUtils.isEmpty(details.getText())){
                    details.setError("Enter details");
                }else{                    Inquiries inquiry = new Inquiries();
                    String id = FirebaseDatabase.getInstance().getReference("Inquiries").push().getKey();

                    inquiry.setId(id);
                    inquiry.setTitle(title.getText().toString());
                    inquiry.setDetails(details.getText().toString());
                    inquiry.setCreatorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    inquiry.setStatus("new");

                    FirebaseDatabase.getInstance().getReference("Inquiries").child(inquiry.getId()).setValue(inquiry).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(addInquiriesActivity.this, "Inquiry Send Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(addInquiriesActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}