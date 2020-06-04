package com.example.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.Details;
import com.example.demo.Model.Data;
import com.example.demo.R;

import java.util.ArrayList;

public class Recyclerviewadapter extends RecyclerView.Adapter<Recyclerviewadapter.Viewholder> {



    private Context context;
    private ArrayList<Data> android;

    public Recyclerviewadapter(Context context, ArrayList<Data> android) {
        this.context = context;
        this.android = android;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);

        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {



        holder.txt_name.setText(android.get(position).getName());

        Glide.with(context).load(android.get(position).getAndroid_image_url()).into(holder.img_avtar);


        holder.layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("image_send",android.get(position).getAndroid_image_url());
                intent.putExtra("name_send",android.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return android.size();
    }


    public  class  Viewholder extends RecyclerView.ViewHolder{

        TextView txt_name;
        ImageView img_avtar;
        RelativeLayout layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.relative);

            txt_name = itemView.findViewById(R.id.tv_android);
            img_avtar = itemView.findViewById(R.id.img_android);
        }
    }
}
