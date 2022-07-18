package com.example.wanapplication.qa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.example.wanapplication.home.HomeRecyclerAdapter;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;
import com.example.wanapplication.utils.HtmlUtils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QARecyclerAdapter extends RecyclerView.Adapter<QARecyclerAdapter.myAdapter> {
    private List<Datas> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public QARecyclerAdapter(List<Datas> data, Context context) {
        this.data = data;
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
    public QARecyclerAdapter.myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.qa_item, parent, false);
        return new QARecyclerAdapter.myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QARecyclerAdapter.myAdapter holder, @SuppressLint("RecyclerView") int position) {
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
        Log.d("lab", "onBindViewHolder: "+ data.get(position).getDesc());
//        holder.desc.setText(HtmlUtils.delHTMLTag(data.get(position).getDesc()));
        String desc = Html.fromHtml(data.get(position).getDesc()).toString();
        HtmlUtils.removeAllBank(desc);
        holder.desc.setText(desc);

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
                intent.putExtra("link", data.get(position).getLink());
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
        private TextView desc;
        private ImageView collect;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.qa_author);
            date = itemView.findViewById(R.id.qa_date);
            title = itemView.findViewById(R.id.qa_title);
            chapter = itemView.findViewById(R.id.qa_chapter);
            desc = itemView.findViewById(R.id.qa_desc);
            collect = itemView.findViewById(R.id.qa_collect);
        }
    }
}
