package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddTalentActivity extends AppCompatActivity {
    private ImageView selectImage;
    private EditText title;
    private EditText details;
    private Spinner talents;
    private Button add;

    ArrayList<Section> sections = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_talent);
        getSupportActionBar().setTitle("Add Talent");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectImage = findViewById(R.id.select_image);
        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        talents = findViewById(R.id.talents);
        add = findViewById(R.id.add);

        ArrayAdapter<Section> arrayAdapter = new ArrayAdapter<Section>(this, android.R.layout.simple_spinner_dropdown_item,sections);

        talents.setAdapter(arrayAdapter);

        FirebaseDatabase.getInstance().getReference("Sections").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sections.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Section section = dataSnapshot.getValue(Section.class);
                    sections.add(section);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,142);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = FirebaseDatabase.getInstance().getReference("Talents").push().getKey();
                Talent talent = new Talent();
                talent.setTitle(title.getText().toString());
                talent.setDetails(details.getText().toString());
                Section section = (Section) talents.getSelectedItem();
                talent.setType(section.getId());
                talent.setId(id);
                talent.setCreatorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                selectImage.setDrawingCacheEnabled(true);
                selectImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) selectImage.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("TalentsImages").child(id+".jpeg");
                UploadTask uploadTask = storageRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        FirebaseDatabase.getInstance().getReference("Talents").child(id).setValue(talent).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AddTalentActivity.this, "Talent Added Successfully", Toast.LENGTH_SHORT).show();
                                } else{
                                    Toast.makeText(AddTalentActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    Uri uri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 142 && resultCode == RESULT_OK){
            uri = data.getData();
            selectImage.setImageURI(uri);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
