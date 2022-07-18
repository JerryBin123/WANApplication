package com.example.wanapplication.mine;

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
import com.example.wanapplication.bean.mine.CollectArticleData;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;
import com.example.wanapplication.utils.HtmlUtils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CollectArticleRecyclerAdapter extends RecyclerView.Adapter {
    private List<CollectArticleData> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public CollectArticleRecyclerAdapter(List<CollectArticleData> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<CollectArticleData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(viewType, parent, false);
        if (viewType == R.layout.home_item){
            return new myHomeAdapter(view);
        }
        return new myQAAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        MineService service = RetrofitClient.getInstance().getService(MineService.class, context);
        switch (itemViewType){
            case R.layout.home_item:
                if (data.get(holder.getAdapterPosition()).getAuthor().equals("")){
                    ((myHomeAdapter)holder).author.setText("匿名");
                }else {
                    ((myHomeAdapter)holder).author.setText(data.get(holder.getAdapterPosition()).getAuthor());

                }
                ((myHomeAdapter)holder).title.setText(data.get(holder.getAdapterPosition()).getTitle());
                ((myHomeAdapter)holder).date.setText(data.get(holder.getAdapterPosition()).getNiceDate());
                ((myHomeAdapter)holder).chapter.setText(data.get(holder.getAdapterPosition()).getChapterName());
                ((myHomeAdapter)holder).collect.setImageResource(R.drawable.ic_collect_after);
                ((myHomeAdapter)holder).collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.unCollectPageArticle((int) data.get(holder.getAdapterPosition()).getId(),
                                data.get(holder.getAdapterPosition()).getOriginId())
                                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<BaseBean>() {
                                    @Override
                                    public void accept(BaseBean baseBean) throws Throwable {
                                        if (baseBean.getErrorCode() == 0){
                                            Log.d("测试", "accept: 取消收藏成功");
                                            data.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                            notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());
                                        }
                                    }
                                });
                    }
                });
                ((myHomeAdapter)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("link", data.get(holder.getAdapterPosition()).getLink());
                        intent.putExtra("title",data.get(holder.getAdapterPosition()).getTitle());
                        intent.putExtra("collect", true);
                        context.startActivity(intent);
                    }
                });
                break;
            case R.layout.qa_item:
                if (data.get(holder.getAdapterPosition()).getAuthor().equals("")){
                    ((myQAAdapter)holder).author.setText("匿名");
                }else {
                    ((myQAAdapter)holder).author.setText(data.get(holder.getAdapterPosition()).getAuthor());

                }
                ((myQAAdapter)holder).title.setText(data.get(holder.getAdapterPosition()).getTitle());
                ((myQAAdapter)holder).date.setText(data.get(holder.getAdapterPosition()).getNiceDate());
                ((myQAAdapter)holder).chapter.setText(data.get(holder.getAdapterPosition()).getChapterName());
                String desc = Html.fromHtml(data.get(holder.getAdapterPosition()).getDesc()).toString();
                HtmlUtils.removeAllBank(desc);
                ((myQAAdapter)holder).desc.setText(desc);
                ((myQAAdapter)holder).collect.setImageResource(R.drawable.ic_collect_after);
                ((myQAAdapter)holder).collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.unCollectPageArticle((int) data.get(holder.getAdapterPosition()).getId(),
                                data.get(holder.getAdapterPosition()).getOriginId())
                                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<BaseBean>() {
                                    @Override
                                    public void accept(BaseBean baseBean) throws Throwable {
                                        if (baseBean.getErrorCode() == 0){
                                            Log.d("测试", "accept: 取消收藏成功");
                                            data.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                            notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());
                                        }
                                    }
                                });
                    }
                });
                ((myQAAdapter)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra("link", data.get(holder.getAdapterPosition()).getLink());
                        intent.putExtra("title",data.get(holder.getAdapterPosition()).getTitle());
                        intent.putExtra("collect", true);
                        context.startActivity(intent);
                    }
                });
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if ("".equals(data.get(position).getDesc())){
            return R.layout.home_item;
        }else {
            return R.layout.qa_item;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myHomeAdapter extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView date;
        private TextView title;
        private TextView chapter;
        private ImageView collect;
        public myHomeAdapter(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.home_author);
            date = itemView.findViewById(R.id.home_date);
            title = itemView.findViewById(R.id.home_title);
            chapter = itemView.findViewById(R.id.home_chapter);
            collect = itemView.findViewById(R.id.home_collect);
        }
    }

    public class myQAAdapter extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView date;
        private TextView title;
        private TextView chapter;
        private TextView desc;
        private ImageView collect;
        public myQAAdapter(@NonNull View itemView) {
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
