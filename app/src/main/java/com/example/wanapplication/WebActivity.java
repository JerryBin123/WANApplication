package com.example.wanapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.wanapplication.bean.ReadLaterData;
import com.example.wanapplication.bean.mine.NewRecordData;
import com.example.wanapplication.mine.ReadRecordDatabase;
import com.example.wanapplication.utils.ReadLaterSqlite;
import com.example.wanapplication.utils.ReadRecordSqlite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WebActivity extends AppCompatActivity {

    private String title;
    private String link;
    private boolean isCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        title = intent.getStringExtra("title");
        isCollect = intent.getBooleanExtra("collect", false);
        Log.d("测试", "onCreate: "+isCollect);
        WebView webView = (WebView) findViewById(R.id.wv_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小

        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        //访问网页
        webView.loadUrl(link);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
        initListener();
        insertRecord();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String readDate = formatter.format(date);
        ReadRecordDatabase.Companion.getInstance(this)
                .getReadRecordDao().insert(new NewRecordData( title,readDate, link));
    }

    private void initListener() {
        FloatingActionButton back = findViewById(R.id.web_back);
        FloatingActionButton cancel = findViewById(R.id.web_cancel);
        FloatingActionButton collect = findViewById(R.id.web_collect);
        FloatingActionButton readLater = findViewById(R.id.web_read_later);
        back.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                back.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                collect.setVisibility(View.VISIBLE);
                readLater.setVisibility(View.VISIBLE);
                if (isCollect){
                    collect.setImageResource(R.drawable.ic_collect_after);
                }
                if (querydb()){
                    readLater.setImageResource(R.drawable.ic_read_later_after);
                }
                return true;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.setVisibility(View.VISIBLE);
                readLater.setVisibility(View.GONE);
                collect.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            }
        });
        readLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (querydb()){
                    delete(link);
                    readLater.setImageResource(R.drawable.ic_read_later);
                }else{
                    insert();
                    readLater.setImageResource(R.drawable.ic_read_later_after);
                }
            }
        });
    }

    private void insert() {
        SQLiteOpenHelper sqLiteOpenHelper = ReadLaterSqlite.getmInstance(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String readDate = formatter.format(date);
        if (db.isOpen()){
            String sql = "insert into reads(title, date, url) values(?,?,?)";
            db.execSQL(sql,new Object[]{title,readDate,link});
        }
        db.close();
    }

    private void insertRecord() {
        SQLiteOpenHelper sqLiteOpenHelper = ReadRecordSqlite.getmInstance(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String readDate = formatter.format(date);
        if (db.isOpen()){
            String sql = "insert into records(title, date, url) values(?,?,?)";
            db.execSQL(sql,new Object[]{title,readDate,link});
        }
        db.close();
    }

    @SuppressLint("Range")
    private Boolean querydb() {
        SQLiteOpenHelper sqLiteOpenHelper = ReadLaterSqlite.getmInstance(this);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        if (db.isOpen()){
            Cursor cursor = db.rawQuery("select * from reads where url = ? and title = ?"
                    , new String[]{link, title});

            if (cursor.getCount() > 0){
                return true;
            }
        }
        return false;
    }

    private void delete(String url) {
        SQLiteOpenHelper helper = ReadLaterSqlite.getmInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()){
            String sql = "delete from reads where url = ?";
            db.execSQL(sql, new Object[]{url});
        }
        db.close();
    }
}