package com.example.wanapplication.mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanapplication.R;
import com.example.wanapplication.WebBannerActivity;
import com.example.wanapplication.bean.mine.OpenData;

import java.util.List;

public class OpenRecyclerAdapter extends RecyclerView.Adapter<OpenRecyclerAdapter.myAdapter> {
    private List<OpenData> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public OpenRecyclerAdapter(List<OpenData> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.open_item, parent, false);
        return new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, int position) {
        holder.project.setText(data.get(holder.getAdapterPosition()).getProject());
        holder.desc.setText(data.get(holder.getAdapterPosition()).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebBannerActivity.class);
                intent.putExtra("name",data.get(holder.getAdapterPosition()).getProject());
                intent.putExtra("url", data.get(holder.getAdapterPosition()).getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myAdapter extends RecyclerView.ViewHolder {
        private TextView project;
        private TextView desc;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            project = itemView.findViewById(R.id.open_project);
            desc = itemView.findViewById(R.id.open_desc);
        }
    }
}
