package com.example.wanapplication.navi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanapplication.R;
import com.example.wanapplication.WebActivity;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.bean.knowledge.KnowledgeData;
import com.example.wanapplication.bean.knowledge.NavigationBean;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class NavigationRecyclerAdapter extends RecyclerView.Adapter<NavigationRecyclerAdapter.myAdapter> {
    private List<NavigationBean> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public NavigationRecyclerAdapter(List<NavigationBean> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.navigation_item, parent, false);
        return new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, int position) {
        holder.textView.setText(data.get(position).getName());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        holder.flexboxLayout.removeAllViews();
        for (int i = 0; i < data.get(holder.getAdapterPosition()).getArticles().size(); i++) {
            TextView tv = new TextView(context);
            tv.setPadding(50, 10, 50, 10);
            tv.setText(data.get(holder.getAdapterPosition()).getArticles().get(i).getTitle());
//            Datas datas = data.get(holder.getAdapterPosition()).getArticles().get(i);
//            Log.d("hk", "onBindViewHolder: " + datas.getLink());
            tv.setMaxEms(10);
            tv.setSingleLine();
            tv.setBackgroundResource(R.drawable.knowledge_item_background);
            tv.setLayoutParams(layoutParams);
            int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Log.d("hk", "onClick: " + datas.getLink());
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("link",  data.get(holder.getAdapterPosition()).getArticles().get(finalI).getLink());
                    context.startActivity(intent);
                }
            });
            holder.flexboxLayout.addView(tv, layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myAdapter extends RecyclerView.ViewHolder {
        private FlexboxLayout flexboxLayout;
        private TextView textView;

        public myAdapter(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.navigation_tv);
            flexboxLayout = itemView.findViewById(R.id.navigation_fbl);
        }
    }
}
