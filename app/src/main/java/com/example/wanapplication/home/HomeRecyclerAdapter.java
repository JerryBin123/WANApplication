package com.example.wanapplication.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanapplication.LoginRegisterActivity;
import com.example.wanapplication.R;
import com.example.wanapplication.WebActivity;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.mine.CoinActivity;
import com.example.wanapplication.mine.MineAdapter;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;
import com.example.wanapplication.utils.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.myAdapter> {

    private List<Datas> data = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public HomeRecyclerAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Datas> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<Datas> newData){
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.home_item, parent, false);
        return new HomeRecyclerAdapter.myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, @SuppressLint("RecyclerView") int position) {
        MineService service = RetrofitClient.getInstance().getService(MineService.class, context);


        if (data.get(position).getAuthor().equals("")){
            Log.d("aaa", "分享人: " + data.get(position).getShareUser());
            holder.author.setText(data.get(position).getShareUser());
        }else{
            Log.d("aaa", "作者: " + data.get(position).getAuthor());
            holder.author.setText(data.get(position).getAuthor());
        }
        holder.date.setText(data.get(position).getNiceDate());
        holder.title.setText(data.get(position).getTitle());
        holder.chapter.setText(data.get(position).getChapterName() + "·" +
                data.get(position).getSuperChapterName());
        if (data.get(holder.getAdapterPosition()).getCollect()){
            holder.collect.setImageResource(R.drawable.ic_collect_after);
        }
        holder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(holder.getAdapterPosition()).getCollect()){
                    service.unCollectArticle(data.get(holder.getAdapterPosition()).getId()).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Throwable {
                                    if (baseBean.getErrorCode() == 0){
                                        Log.d("测试", "accept: 取消收藏成功");
                                        holder.collect.setImageResource(R.drawable.ic_collect);
                                        data.get(holder.getAdapterPosition()).setCollect(false);
                                    }else {
                                        context.startActivity(new Intent(context, LoginRegisterActivity.class));
                                    }
                                }
                            });
                }else{
                    service.collectArticle(data.get(holder.getAdapterPosition()).getId()).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Throwable {
                                    if (baseBean.getErrorCode() == 0){
                                        Log.d("测试", "accept: 收藏成功" );
                                        holder.collect.setImageResource(R.drawable.ic_collect_after);
                                        data.get(holder.getAdapterPosition()).setCollect(true);
                                    }else{
                                        context.startActivity(new Intent(context, LoginRegisterActivity.class));
                                    }
                                }
                            });
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("link", data.get(holder.getAdapterPosition()).getLink());
                intent.putExtra("title",data.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("collect", data.get(holder.getAdapterPosition()).getCollect());
                Log.d("测试", "onClick: "+ data.get(holder.getAdapterPosition()).getCollect());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class myAdapter extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView date;
        private TextView title;
        private TextView chapter;
        private ImageView collect;
        private TextView desc;
        private TextView top;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.home_author);
            date = itemView.findViewById(R.id.home_date);
            title = itemView.findViewById(R.id.home_title);
            chapter = itemView.findViewById(R.id.home_chapter);
            collect = itemView.findViewById(R.id.home_collect);
            desc = itemView.findViewById(R.id.home_desc);
            top = itemView.findViewById(R.id.home_top);
        }
    }
}
