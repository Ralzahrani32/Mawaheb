package com.example.mawaheb;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageInquiriesFragment extends Fragment {

    ArrayList<Inquiries> inquiries = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_manage_inquiries, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        InquiriesAdapter adapter = new InquiriesAdapter(getActivity(),inquiries);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("Inquiries").orderByChild("status").equalTo("new").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                inquiries.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Inquiries inquiry = dataSnapshot.getValue(Inquiries.class);
                    Log.e("TdDDD",inquiry.getTitle());
                    inquiries.add(inquiry);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}