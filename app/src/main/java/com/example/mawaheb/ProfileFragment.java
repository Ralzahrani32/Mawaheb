package com.example.mawaheb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private ImageView SelectImage;
    private EditText Name;
    private EditText Bio;
    private EditText Email;
    private EditText Phone;
    private EditText Talents;
    private Button Update;

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

        return view;
    }
}