package com.example.wanapplication.mine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.mine.CoinListData;
import com.example.wanapplication.bean.mine.CoinRankBean;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CoinRankActivity extends AppCompatActivity {

    private MineService mineService;
    private RecyclerView recyclerView;
    private int currPage = 2;
    private CoinRankRecyclerAdapter coinRankRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_rank);
        initViews();
    }

    private void initViews() {
        coinRankRecyclerAdapter = new CoinRankRecyclerAdapter(CoinRankActivity.this);
        RefreshLayout refreshLayout = findViewById(R.id.coin_rank_smartRefresh);
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mineService.getCoinRankList(currPage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseBean<CoinListData>>() {
                            @Override
                            public void accept(BaseBean<CoinListData> coinListDataBaseBean) throws Throwable {
                                List<CoinRankBean> datas = coinListDataBaseBean.getData().getDatas();
                                Log.d("测试", "accept: " + datas);
                                coinRankRecyclerAdapter.addData(datas);
                                recyclerView.setAdapter(coinRankRecyclerAdapter);
                                currPage += 1;
                                refreshLayout.finishLoadMore();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.d("测试", "accept: " + throwable.getMessage());
                            }
                        });
            }
        });
        recyclerView = findViewById(R.id.coin_rank_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mineService = RetrofitClient.getInstance().getService(MineService.class, this);
        mineService.getCoinRankList(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<CoinListData>>() {
                    @Override
                    public void accept(BaseBean<CoinListData> coinListDataBaseBean) throws Throwable {
                        List<CoinRankBean> datas = coinListDataBaseBean.getData().getDatas();
                        coinRankRecyclerAdapter.updateData(datas);
                        recyclerView.setAdapter(coinRankRecyclerAdapter);
                    }
                });
    }
}