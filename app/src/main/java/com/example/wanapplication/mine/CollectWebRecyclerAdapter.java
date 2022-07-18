package com.example.wanapplication.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.example.wanapplication.R;
import com.example.wanapplication.WebBannerActivity;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.mine.CollectWebData;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CollectWebRecyclerAdapter extends RecyclerView.Adapter<CollectWebRecyclerAdapter.myAdapter> {
    private List<CollectWebData> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private final List<SwipeLayout> mUnCloseList = new ArrayList<>();

    public CollectWebRecyclerAdapter(List<CollectWebData> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
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

    public void setData(List<CollectWebData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.collect_web_item, parent, false);
        return new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, int position) {
        MineService service = RetrofitClient.getInstance().getService(MineService.class, context);
        holder.name.setText(data.get(holder.getAdapterPosition()).getName());
        holder.url.setText(data.get(holder.getAdapterPosition()).getLink());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.deleteCollectWeb(data.get(holder.getAdapterPosition()).getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Throwable {
                                if (baseBean.getErrorCode() == 0){
                                    Log.d("测试", "accept: 删除成功");
                                    data.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());
                                }
                            }
                        });
            }
        });
        holder.web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebBannerActivity.class);
                intent.putExtra("name",data.get(holder.getAdapterPosition()).getName());
                intent.putExtra("url", data.get(holder.getAdapterPosition()).getLink());
                context.startActivity(intent);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = layoutInflater.inflate(R.layout.edit_dialog, null, false);
                EditText title = view1.findViewById(R.id.et_title);
                EditText editLink = view1.findViewById(R.id.et_link);
                editLink.setText(data.get(holder.getAdapterPosition()).getLink());
                title.setText(data.get(holder.getAdapterPosition()).getName());
                view1.findViewById(R.id.et_link);
                AlertDialog dialog = new AlertDialog.Builder(context).setView(view1).create();//创建对话框
                //分别设置三个button
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        service.editCollectWeb(data.get(holder.getAdapterPosition()).getId()
                                ,title.getText().toString()
                                ,editLink.getText().toString())
                                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<BaseBean<CollectWebData>>() {
                                    @Override
                                    public void accept(BaseBean<CollectWebData> collectWebDataBaseBean) throws Throwable {
                                        if (collectWebDataBaseBean.getErrorCode() == 0){
                                            data.get(holder.getAdapterPosition()).setLink(
                                                    collectWebDataBaseBean.getData().getLink());
                                            data.get(holder.getAdapterPosition()).setName(
                                                    collectWebDataBaseBean.getData().getName());
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL,"取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                });
                dialog.show();//显示对话框
            }
        });
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label"
                        , data.get(holder.getAdapterPosition()).getLink());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                holder.swipeLayout.close();
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

    public class myAdapter extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView url;
        private TextView delete;
        private SwipeLayout swipeLayout;
        private TextView edit;
        private View web;
        private TextView copy;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.collect_web_name);
            url = itemView.findViewById(R.id.collect_web_url);
            swipeLayout = itemView.findViewById(R.id.collect_web_sl);
            delete = itemView.findViewById(R.id.collect_web_delete);
            edit = itemView.findViewById(R.id.edit);
            web = itemView.findViewById(R.id.collect_web);
            copy = itemView.findViewById(R.id.tv_copy);
        }
    }
}
