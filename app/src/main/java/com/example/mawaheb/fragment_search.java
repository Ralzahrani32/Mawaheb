package com.example.mawaheb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class fragment_search extends Fragment {
    private EditText nameBox;
    private ImageButton search;
    private RecyclerView recyclerView;
    ArrayList<Talent> talents = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        nameBox = view.findViewById(R.id.nameBox);
        search = view.findViewById(R.id.search);

       recyclerView =view.findViewById(R.id.recyclerView);


        return view;
    }
}
