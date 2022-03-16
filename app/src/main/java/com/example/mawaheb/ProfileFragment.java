package com.example.mawaheb;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {

    private ImageView SelectImage;
    private EditText Name;
    private EditText Bio;
    private EditText Email;
    private EditText Phone;
    private EditText Talents;
    private Button Update;
    private DatabaseReference mDatabase;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SelectImage = view.findViewById(R.id.select_image);
        Name = view.findViewById(R.id.name);
        Bio = view.findViewById(R.id.bio);
        Email = view.findViewById(R.id.email);
        Phone = view.findViewById(R.id.phone);
        Talents = view.findViewById(R.id.talents);
        Update = view.findViewById(R.id.update);
        mDatabase = FirebaseDatabase.getInstance().getReference();

       SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,142);
            }
        });
        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    user = task.getResult().getValue(User.class);
                    Name.setText(user.getName());
                    Bio.setText(user.getBio()+"");
                    Email.setText(user.getEmail()+"");
                    Phone.setText(user.getPhone()+"");
                    Talents.setText(user.getTalents()+"");

                }else{
                    Toast.makeText(getActivity(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    Uri uri;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 142 && resultCode == RESULT_OK){
            uri = data.getData();
            SelectImage.setImageURI(uri);
        }
    }
}

