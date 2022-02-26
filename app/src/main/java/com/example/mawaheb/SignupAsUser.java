package com.example.mawaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignupAsUser extends AppCompatActivity {
private FirebaseAuth mAuth;
private ImageView mSelectImage;
private EditText mName;
private EditText mEmail;
private EditText mPhone;
private Spinner mTalents;
private EditText mPassword;
private EditText mConfirmPassword;
private Button mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_as_user);
        getSupportActionBar().setTitle("Signup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth=FirebaseAuth.getInstance();

        mSelectImage=findViewById(R.id.select_image);
        mName=findViewById(R.id.name);
        mEmail=findViewById(R.id.email);
        mPhone=findViewById(R.id.phone);
        mTalents=findViewById(R.id.talents);
        mPassword=findViewById(R.id.password);
        mConfirmPassword=findViewById(R.id.confirmpassword);
        mSignup=findViewById(R.id.signup);
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,142);
            }
        });
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri==null){
                    Toast.makeText(SignupAsUser.this,"Select image",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(mName.getText())) {
                    mName.setError("Enter Name");
                }else if(TextUtils.isEmpty(mEmail.getText())) {
                    mEmail.setError("Enter Email");
                }else if(TextUtils.isEmpty(mPhone.getText())) {
                    mPhone.setError("Enter Phone");
                }else if(TextUtils.isEmpty(mPassword.getText())) {
                    mPassword.setError("Enter Password");
                }else if(TextUtils.isEmpty(mConfirmPassword.getText())) {
                    mConfirmPassword.setError("Enter Confirm Password");
                }else if(!mConfirmPassword.getText().toString().equals(mPassword.getText().toString())){
                    mConfirmPassword.setError("Password Not Match");
                    mPassword.setError("Password Not Match");
                } else{  mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(),mPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignupAsUser.this, "User Create Successfully", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(SignupAsUser.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                }


            }
        });

    }

    Uri uri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==142 && resultCode==RESULT_OK){
            uri=data.getData();
            mSelectImage.setImageURI(uri);

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
