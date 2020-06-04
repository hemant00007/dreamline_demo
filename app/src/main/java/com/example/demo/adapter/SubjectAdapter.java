package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.Model.SubjectModel;
import com.example.demo.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewholder> {

    List<SubjectModel> result;
    private Context context;

    public SubjectAdapter(List<SubjectModel> result, Context context) {
        this.result = result;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_subject,parent,false);

        return new SubjectViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewholder holder, int position) {
        holder.subjectname.setText(result.get(position).getSubjectName());

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    class SubjectViewholder extends RecyclerView.ViewHolder{
        TextView subjectname;

        public SubjectViewholder(@NonNull View itemView) {
            super(itemView);

            subjectname = itemView.findViewById(R.id.subjec_name);
        }
    }
}
