package com.example.wanapplication.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.mine.CoinRankBean;

import java.util.ArrayList;
import java.util.List;

public class CoinRankRecyclerAdapter extends RecyclerView.Adapter<CoinRankRecyclerAdapter.myAdapter> {
    private List<CoinRankBean> data = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private int maxValue = 0;
    private double percentage = 0;

    public void addData(List<CoinRankBean> datas) {
        data.addAll(datas);
//        notifyDataSetChanged();
    }


    public CoinRankRecyclerAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.coin_rank_item, parent, false);
        return new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, int position) {
        holder.progressBar.setMax(maxValue * 1000);
        holder.imageView.setImageDrawable(null);
        if ("1".equals(data.get(holder.getAdapterPosition()).getRank())){
            Log.d("测试", "onBindViewHolder: " + data.get(holder.getAdapterPosition()).getRank());
            holder.imageView.setImageResource(R.drawable.ic_rank_1);
        }
        if ("2".equals(data.get(holder.getAdapterPosition()).getRank())){
            holder.imageView.setImageResource(R.drawable.ic_rank_2);
        }
        if ("3".equals(data.get(holder.getAdapterPosition()).getRank())){
            holder.imageView.setImageResource(R.drawable.ic_rank_3);
        }
        holder.coinCount.setText(""+data.get(holder.getAdapterPosition()).getCoinCount());
        holder.coinRank.setText(data.get(holder.getAdapterPosition()).getRank());
        holder.progressBar.setProgress(data.get(holder.getAdapterPosition()).getCoinCount() * 1000);
        holder.name.setText(data.get(holder.getAdapterPosition()).getUsername());
    }

    public void updateData(List<CoinRankBean> data){
        this.data = data;
        maxValue = data.get(0).getCoinCount();
        notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myAdapter extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView name;
        private TextView coinCount;
        private ImageView imageView;
        private TextView coinRank;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.coin_rank_progress);
            name = itemView.findViewById(R.id.coin_name);
            coinCount = itemView.findViewById(R.id.coin_rank_count);
            imageView = itemView.findViewById(R.id.coin_count_iv);
            coinRank = itemView.findViewById(R.id.coin_rank_tv);
        }
    }

}
