package com.example.mawaheb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        holder.title.setText(talents.get(position).getTitle());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("TalentsImages").child(talents.get(position).getId()+".jpeg");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context)
                        .load(uri)
                        .into(holder.imageTalent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),Talent_Details_Activity2.class);
                    intent.putExtra("talent",talents.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
