package com.example.wanapplication.navi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanapplication.R;
import com.example.wanapplication.WebActivity;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class KnowledgeArticleRecyclerAdapter extends RecyclerView.Adapter<KnowledgeArticleRecyclerAdapter.myAdapter> {
    private List<Datas> datas;
    private Context context;
    private LayoutInflater layoutInflater;

    public KnowledgeArticleRecyclerAdapter(List<Datas> datas, Context context) {
        this.datas = datas;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.knowledge_article_item, parent, false);
        return new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, int position) {
        if (datas.get(holder.getAdapterPosition()).getAuthor().equals("")){
            Log.d("aaa", "分享人: " + datas.get(holder.getAdapterPosition()).getShareUser());
            holder.author.setText(datas.get(holder.getAdapterPosition()).getShareUser());
        }else{
            Log.d("aaa", "作者: " + datas.get(holder.getAdapterPosition()).getAuthor());
            holder.author.setText(datas.get(holder.getAdapterPosition()).getAuthor());
        }
        holder.date.setText(datas.get(holder.getAdapterPosition()).getNiceDate());
        holder.title.setText(datas.get(holder.getAdapterPosition()).getTitle());
        holder.chapter.setText(datas.get(holder.getAdapterPosition()).getSuperChapterName() + "·" +
                datas.get(holder.getAdapterPosition()).getChapterName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("link", datas.get(holder.getAdapterPosition()).getLink());
                intent.putExtra("title",datas.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("collect", datas.get(holder.getAdapterPosition()).getCollect());
                Log.d("测试", "onClick: "+ datas.get(holder.getAdapterPosition()).getCollect());
                context.startActivity(intent);
            }
        });
        if (datas.get(holder.getAdapterPosition()).getCollect()){
            holder.collect.setImageResource(R.drawable.ic_collect_after);
        }
        MineService service = RetrofitClient.getInstance().getService(MineService.class, context);
        holder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datas.get(holder.getAdapterPosition()).getCollect()){
                    service.unCollectArticle(datas.get(holder.getAdapterPosition()).getId()).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Throwable {
                                    if (baseBean.getErrorCode() == 0){
                                        Log.d("测试", "accept: 取消收藏成功");
                                        holder.collect.setImageResource(R.drawable.ic_collect);
                                        datas.get(holder.getAdapterPosition()).setCollect(false);
                                    }
                                }
                            });
                }else{
                    service.collectArticle(datas.get(holder.getAdapterPosition()).getId()).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Throwable {
                                    if (baseBean.getErrorCode() == 0){
                                        Log.d("测试", "accept: 收藏成功" );
                                        holder.collect.setImageResource(R.drawable.ic_collect_after);
                                        datas.get(holder.getAdapterPosition()).setCollect(true);
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class myAdapter extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView date;
        private TextView title;
        private TextView chapter;
        private ImageView collect;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.knowledge_article_author);
            date = itemView.findViewById(R.id.knowledge_article_date);
            title = itemView.findViewById(R.id.knowledge_article_title);
            chapter = itemView.findViewById(R.id.knowledge_article_chapter);
            collect = itemView.findViewById(R.id.knowledge_article_collect);
        }
    }
}
