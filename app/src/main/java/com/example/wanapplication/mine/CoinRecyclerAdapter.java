package com.example.wanapplication.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.mine.CoinRecordBean;

import java.util.List;

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinRecyclerAdapter.myAdapter> {
    private List<CoinRecordBean> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public CoinRecyclerAdapter(List<CoinRecordBean> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.coin_item, parent, false);
        return new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, int position) {
        holder.reason.setText(data.get(holder.getAdapterPosition()).getReason());
        holder.date.setText(data.get(holder.getAdapterPosition()).getDesc());
        holder.grade.setText("+"+data.get(holder.getAdapterPosition()).getCoinCount());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myAdapter extends RecyclerView.ViewHolder {
        private TextView reason;
        private TextView date;
        private TextView grade;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            reason = itemView.findViewById(R.id.coin_reason);
            date = itemView.findViewById(R.id.coin_date);
            grade = itemView.findViewById(R.id.coin_count_grade);
        }
    }
}
