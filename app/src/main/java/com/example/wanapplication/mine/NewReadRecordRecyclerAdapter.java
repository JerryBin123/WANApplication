package com.example.wanapplication.mine;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanapplication.R;
import com.example.wanapplication.WebBannerActivity;
import com.example.wanapplication.bean.mine.NewRecordData;
import com.example.wanapplication.bean.mine.ReadRecordData;
import com.example.wanapplication.utils.ReadRecordSqlite;

import java.util.List;

public class NewReadRecordRecyclerAdapter extends RecyclerView.Adapter<NewReadRecordRecyclerAdapter.myAdapter> {
    private List<NewRecordData> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public NewReadRecordRecyclerAdapter(List<NewRecordData> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<NewRecordData> data) {
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
    }

    private void delete(int id) {
        SQLiteOpenHelper helper = ReadRecordSqlite.getmInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()){
            String sql = "delete from records where _id = ?";
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
        public myAdapter(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.read_later_title);
            date = itemView.findViewById(R.id.read_later_date);
            delete = itemView.findViewById(R.id.read_later_delete);
            url = itemView.findViewById(R.id.read_later_url);
        }
    }
}
