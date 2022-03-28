package com.example.mawaheb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TalentAdapter extends RecyclerView.Adapter<TalentAdapter.TalentViewHolder> {
    Context context;
    ArrayList<Talent> talents;

    public TalentAdapter(Context context, ArrayList<Talent> talents) {
        this.context = context;
        this.talents = talents;
    }
    @NonNull
    @Override
    public TalentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_talent,parent,false);
        return  new TalentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TalentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return talents.size();
    }

    class TalentViewHolder extends RecyclerView.ViewHolder{
        ImageView imageTalent;
        TextView title;
        public TalentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageTalent = itemView.findViewById(R.id.imageTalent);
        }
    }
}
