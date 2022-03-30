package com.example.mawaheb;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

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
                    mTalents.setText(user.getTalents()+"");
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

        addInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), addInquiriesActivity.class);
                startActivity(intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri_select == null){
                    Toast.makeText(getActivity(), "Select image", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mName.getText())){
                    mName.setError("Enter Name");
                }else if(TextUtils.isEmpty(mEmail.getText())){
                    mEmail.setError("Enter Email");
                }else if(TextUtils.isEmpty(mPhone.getText())){
                    mPhone.setError("Enter Phone");
                }else if(TextUtils.isEmpty(mLecinceNumber.getText())){
                    mLecinceNumber.setError("Enter Lecince Number");
                }else{

                    mSelectImage.setDrawingCacheEnabled(true);
                    mSelectImage.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) mSelectImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("UsersImages").child(user.getUID()+".jpeg");

                    UploadTask uploadTask = storageRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            user.setName(mName.getText().toString());
                            user.setPhone(mPhone.getText().toString());
                            user.setEmail(mEmail.getText().toString());
                            user.setTalents(mTalents.getText().toString());
                            user.setLecinceNumber(mLecinceNumber.getText().toString());
                            user.setUserType("Company");

                            mDatabase.child("users").child(user.getUID()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Company  Information Edited Successfully", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getActivity(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });


                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setMessage("Do you want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(),loginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        return view;
    }
    Uri uri_select;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 142 && resultCode == RESULT_OK){
            uri_select = data.getData();
            mSelectImage.setImageURI(uri_select);
        }

    }

}