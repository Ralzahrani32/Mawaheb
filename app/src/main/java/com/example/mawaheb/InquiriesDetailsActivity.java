package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class InquiriesDetailsActivity extends AppCompatActivity {
    private TextView title;
    private TextView details;
    private TextView reply;
    private EditText replyBox;
    private ImageButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiries_details);
        getSupportActionBar().setTitle("Inquiry Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        reply = findViewById(R.id.reply);
        replyBox = findViewById(R.id.replyBox);
        send = findViewById(R.id.send);

        Intent intent = getIntent();
        Inquiries inquiry = (Inquiries) intent.getSerializableExtra("inquiry");
        title.setText(inquiry.getTitle());
        details.setText(inquiry.getDetails());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(replyBox.getText().toString().isEmpty()){
                    replyBox.setError("Enter Reply");
                }else{
                    reply.setText(replyBox.getText().toString());
                    inquiry.setStatus("Answered");
                    inquiry.setReply(replyBox.getText().toString());
                    FirebaseDatabase.getInstance().getReference("Inquiries").child(inquiry.getId()).setValue(inquiry).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Notification notification = new Notification();
                                String id = FirebaseDatabase.getInstance().getReference("Notifications").push().getKey();
                                notification.setId(id);
                                notification.setTitle("Reply "+ inquiry.getTitle());
                                notification.setDetails(replyBox.getText().toString());
                                notification.setUserId(inquiry.getCreatorId());
                                FirebaseDatabase.getInstance().getReference("Notifications").child(id).setValue(notification).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            replyBox.setText("");

                                            Toast.makeText(InquiriesDetailsActivity.this, "Reply Send Successfully", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(InquiriesDetailsActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(InquiriesDetailsActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



    }
}