package com.example.mawaheb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class InquiriesAdapter extends RecyclerView.Adapter<InquiriesAdapter.InquiriesViewHolder>{
    Context context;
    ArrayList<Inquiries> inquiries;

    public InquiriesAdapter(Context context, ArrayList<Inquiries> inquiries) {
        this.context = context;
        this.inquiries = inquiries;
    }

    @NonNull
    @Override
    public InquiriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inquiries,parent,false);
        return new InquiriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InquiriesAdapter.InquiriesViewHolder holder, int position) {
        holder.title.setText(inquiries.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return inquiries.size();
    }

    class InquiriesViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        public InquiriesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),InquiriesDetailsActivity.class);
                    intent.putExtra("inquiry", (Serializable) inquiries.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
