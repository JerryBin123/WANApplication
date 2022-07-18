package com.example.wanapplication.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.wanapplication.LoginRegisterActivity;
import com.example.wanapplication.MainActivity;
import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.MineShareBean;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.Data;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.home.HomeRecyclerAdapter;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MineShareActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_share);
        initViews();
        initListener();
    }

    private void initListener() {
        ImageView shareArticle = findViewById(R.id.share_article);
        ImageView back = findViewById(R.id.mine_share_back);
        back.setOnClickListener(this);
        shareArticle.setOnClickListener(this);
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.mine_share_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MineService mineService = RetrofitClient.getInstance().getService(MineService.class, this);
        mineService.getMineShareArticleList(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<MineShareBean>>() {
                    @Override
                    public void accept(BaseBean<MineShareBean> mineShareBeanBaseBean) throws Throwable {
                        Log.d("mine_share", "测hi是: " + mineShareBeanBaseBean.getData());
                        if (mineShareBeanBaseBean.getErrorCode() == 0){
                            List<Datas> datas = mineShareBeanBaseBean.getData().getData().getDatas();
                            ShareArticleRecyclerAdapter shareArticleRecyclerAdapter = new ShareArticleRecyclerAdapter(datas, MineShareActivity.this);
                            recyclerView.setAdapter(shareArticleRecyclerAdapter);
                        }else{
                            finish();
                            startActivity(new Intent(MineShareActivity.this, LoginRegisterActivity.class));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("mine_share", "accept: " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share_article:
                Intent intent = new Intent(this, ShareActicleActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_share_back:
                onBackPressed();
                break;
        }
    }
}