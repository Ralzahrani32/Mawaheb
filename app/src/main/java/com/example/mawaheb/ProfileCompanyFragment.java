package com.example.mawaheb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    }
}