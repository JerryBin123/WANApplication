package com.example.wanapplication.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanapplication.LoginRegisterActivity;
import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.mine.CoinData;
import com.example.wanapplication.bean.mine.CoinRankBean;
import com.example.wanapplication.bean.mine.CoinRecordBean;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CoinActivity extends AppCompatActivity implements View.OnClickListener {

    private MineService mineService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
        initViews();
        initListener();
        initRecyclerView();
    }

    private void initListener() {
        ImageView rank = findViewById(R.id.coin_rank);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rank.setOnClickListener(this);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.coin_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mineService.getCoinList(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<CoinData>>() {
                    @Override
                    public void accept(BaseBean<CoinData> coinDataBaseBean) throws Throwable {
                        if (coinDataBaseBean.getErrorCode() == 0){
                            Log.d("coin", "accept: " + coinDataBaseBean.getData().getDatas());
                            List<CoinRecordBean> datas = coinDataBaseBean.getData().getDatas();
                            CoinRecyclerAdapter coinRecyclerAdapter = new CoinRecyclerAdapter(datas, CoinActivity.this);
                            recyclerView.setAdapter(coinRecyclerAdapter);
                        }
                    }
                });
    }

    private void initViews() {
        TextView coinCount = findViewById(R.id.coin_count);
        mineService = RetrofitClient.getInstance().getService(MineService.class, this);
        mineService.getCoinRecord().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<CoinRankBean>>() {
                    @Override
                    public void accept(BaseBean<CoinRankBean> coinRankBeanBaseBean) throws Throwable {
                        Log.d("err", "accept: " + coinRankBeanBaseBean.getData());
                        if (coinRankBeanBaseBean.getErrorCode() == 0){
                            coinCount.setText(coinRankBeanBaseBean.getData().getCoinCount() + "");
                        }else{
                            finish();
                            startActivity(new Intent(CoinActivity.this, LoginRegisterActivity.class));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("err", "accept: " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.coin_rank:
                Intent intent = new Intent(this, CoinRankActivity.class);
                startActivity(intent);
                break;
        }
    }
}