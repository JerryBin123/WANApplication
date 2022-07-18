package com.example.wanapplication.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.ReadLaterData;
import com.example.wanapplication.bean.mine.ReadRecordData;
import com.example.wanapplication.utils.ReadLaterSqlite;
import com.example.wanapplication.utils.ReadRecordSqlite;

import java.util.ArrayList;
import java.util.List;

public class ReadRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private List<ReadRecordData> data = new ArrayList<>();
    private ReadRecordRecyclerAdapter readRecordRecyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_record);
        initViews();
    }

    private void initViews() {
        querydb();
        recyclerView = findViewById(R.id.read_record_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        readRecordRecyclerAdapter = new ReadRecordRecyclerAdapter(data, this);
        recyclerView.setAdapter(readRecordRecyclerAdapter);
        TextView deleteAll = findViewById(R.id.read_record_delete_all);
        deleteAll.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @SuppressLint("Range")
    private void querydb() {
        SQLiteOpenHelper sqLiteOpenHelper = ReadRecordSqlite.getmInstance(this);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            Cursor cursor = db.rawQuery("select * from records", null);

            if (cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    Log.d("测试", "querydb: " + id);
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    Log.d("测试", "querydb: " + title);
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String url = cursor.getString(cursor.getColumnIndex("url"));
                    ReadRecordData readRecordData = new ReadRecordData(id, title, date, url);
                    data.add(readRecordData);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.read_record_delete_all:
                delete();
                readRecordRecyclerAdapter.setData(new ArrayList<>());
                recyclerView.setAdapter(readRecordRecyclerAdapter);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    private void delete() {
        SQLiteOpenHelper helper = ReadRecordSqlite.getmInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()){
            String sql = "delete from records";
            db.execSQL(sql);
        }
        db.close();
    }
}