package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

import com.bumptech.glide.Glide;
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

public class ProfileCompanyFragment extends Fragment {
    private ImageView mSelectImage;
    private EditText mName;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mTalents;
    private EditText mLecinceNumber;

    private Button update;
    private Button logout;
    private Button addInquiry;
    private DatabaseReference mDatabase;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_company, container, false);
        mSelectImage = view.findViewById(R.id.select_image);
        mName = view.findViewById(R.id.name);
        mEmail = view.findViewById(R.id.email);
        mPhone = view.findViewById(R.id.phone);
        mTalents = view.findViewById(R.id.talents);
        mLecinceNumber = view.findViewById(R.id.lecinceNumber);
        update = view.findViewById(R.id.update);
        logout = view.findViewById(R.id.logout);
        addInquiry = view.findViewById(R.id.addInquiry);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSelectImage.setOnClickListener(new View.OnClickListener() {
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
                    mName.setText(user.getName());
                    mEmail.setText(user.getEmail()+"");
                    mPhone.setText(user.getPhone()+"");
                    mTalents.setText(user.getTalent()+"");
                    mLecinceNumber.setText(user.getLecinceNumber()+"");
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("UsersImages").child(user.getUID()+".jpeg");
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getContext())
                                    .load(uri)
                                    .into(mSelectImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    });
                }else{
                    Toast.makeText(getActivity(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
                }

}