package com.example.wanapplication.mine;

import android.annotation.SuppressLint;
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

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.wanapplication.R;
import com.example.wanapplication.WebActivity;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.bean.mine.CollectArticleData;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShareArticleRecyclerAdapter extends RecyclerSwipeAdapter<ShareArticleRecyclerAdapter.myAdapter> {

    private List<Datas> data = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private final List<SwipeLayout> mUnCloseList = new ArrayList<>();
    private MineService mineService;

    public ShareArticleRecyclerAdapter(List<Datas> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        mineService = RetrofitClient.getInstance().getService(MineService.class, context);
    }
    public void closeAll(SwipeLayout layout) {
        for (SwipeLayout swipeLayout : mUnCloseList) {
            if (layout == swipeLayout) {
                continue;
            }
            if (swipeLayout.getOpenStatus() != SwipeLayout.Status.Open) {
                continue;
            }
            swipeLayout.close();
        }
    }

    public void setData(List<Datas> data) {
        this.data = data;
    }

    public void addData(List<Datas> newData){
        data.addAll(newData);
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.share_article_item, parent, false);
        return new ShareArticleRecyclerAdapter.myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, @SuppressLint("RecyclerView") int position) {

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
                Log.d("测试", "accept: " + data.get(holder.getAdapterPosition()).getId() + "   ");
                if (data.get(holder.getAdapterPosition()).getCollect()){
                    mineService.unCollectArticle(data.get(holder.getAdapterPosition()).getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Throwable {
                                    if (baseBean.getErrorCode() == 0){
                                        Log.d("测试", "accept: 取消收藏成功");
                                        holder.collect.setImageResource(R.drawable.ic_collect);
                                        data.get(holder.getAdapterPosition()).setCollect(false);
                                    }
                                }
                            });
                }else{
                    mineService.collectArticle(data.get(holder.getAdapterPosition()).getId())
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Throwable {
                                    if (baseBean.getErrorCode() == 0) {
                                        Log.d("测试", "accept: 收藏成功");
                                        holder.collect.setImageResource(R.drawable.ic_collect_after);
                                        data.get(holder.getAdapterPosition()).setCollect(true);
                                    }
                                }
                            });
                }
            }
        });


        holder.homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("link", data.get(position).getLink());
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mineService.deleteArticle(data.get(holder.getAdapterPosition()).getId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) throws Throwable {
                        Log.d("测试", "accept: " + data.get(holder.getAdapterPosition()).getId());
                        data.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());
                    }
                });
            }
        });
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                closeAll(layout);
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                mUnCloseList.add(layout);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {
                mUnCloseList.remove(layout);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return position;
    }


    public class myAdapter extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView date;
        private TextView title;
        private TextView chapter;
        private SwipeLayout swipeLayout;
        private TextView delete;
        private View homeView;
        private ImageView collect;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.home_author);
            date = itemView.findViewById(R.id.home_date);
            title = itemView.findViewById(R.id.home_title);
            chapter = itemView.findViewById(R.id.home_chapter);
            swipeLayout = itemView.findViewById(R.id.share_article_sl);
            delete = itemView.findViewById(R.id.tv_delete);
            homeView = itemView.findViewById(R.id.home_view);
            collect = itemView.findViewById(R.id.home_collect);
        }
    }
}
