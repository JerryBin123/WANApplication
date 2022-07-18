package com.example.wanapplication.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.example.wanapplication.R;
import com.example.wanapplication.WebBannerActivity;
import com.example.wanapplication.bean.ReadLaterData;
import com.example.wanapplication.utils.ReadLaterSqlite;

import java.util.List;

public class ReadLateRecyclerAdapter extends RecyclerView.Adapter<ReadLateRecyclerAdapter.myAdapter> {
    private List<ReadLaterData> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public ReadLateRecyclerAdapter(List<ReadLaterData> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<ReadLaterData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.read_later_item, parent, false);
        return new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter holder, int position) {
        holder.title.setText(data.get(holder.getAdapterPosition()).getTitle());
        holder.date.setText(data.get(holder.getAdapterPosition()).getDate());
        holder.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebBannerActivity.class);
                intent.putExtra("name",data.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("url", data.get(holder.getAdapterPosition()).getUrl());
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(data.get(holder.getAdapterPosition()).getId());
                data.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());
            }
        });
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label"
                        , data.get(holder.getAdapterPosition()).getUrl());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                holder.swipeLayout.close();
            }
        });
    }

    private void delete(int id) {
        SQLiteOpenHelper helper = ReadLaterSqlite.getmInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()){
            String sql = "delete from reads where _id = ?";
            db.execSQL(sql, new Object[]{id});
        }
        db.close();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myAdapter extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private TextView delete;
        private View url;
        private TextView copy;
        private SwipeLayout swipeLayout;
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.read_later_title);
            date = itemView.findViewById(R.id.read_later_date);
            delete = itemView.findViewById(R.id.read_later_delete);
            url = itemView.findViewById(R.id.read_later_url);
            copy = itemView.findViewById(R.id.tv_copy);
            swipeLayout = itemView.findViewById(R.id.sl);
        }
    }
}
