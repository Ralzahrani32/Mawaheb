package com.example.mawaheb;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        TalentAdapter adapter = new TalentAdapter(getActivity(),talents);
        recyclerView.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryText = nameBox.getText().toString();
                FirebaseDatabase.getInstance().getReference("Talents").orderByChild("title").startAt(queryText)
                        .endAt(queryText+"\uf8ff").addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        talents.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Talent talent = dataSnapshot.getValue(Talent.class);
                            talents.add(talent);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                }


                });
            }
        });


        return view;
    }
}
