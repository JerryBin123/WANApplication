package com.example.wanapplication.mine;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanapplication.LoginRegisterActivity;
import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.mine.CollectArticleBean;
import com.example.wanapplication.bean.mine.CollectArticleData;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CollectArticleFragment extends Fragment {

    private View root;
    private CollectArticleRecyclerAdapter collectArticleRecyclerAdapter;
    private RecyclerView recyclerView;
    private MineService mineService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_collect_article, container, false);
        }
        initViews();
        return root;
    }

    private void initViews() {
        RefreshLayout refreshLayout = root.findViewById(R.id.collect_smartRefresh);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineService.getCollectArticle(0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseBean<CollectArticleBean>>() {
                            @Override
                            public void accept(BaseBean<CollectArticleBean> collectArticleBeanBaseBean) throws Throwable {
                                if (collectArticleBeanBaseBean.getErrorCode() == 0) {
                                    List<CollectArticleData> datas = collectArticleBeanBaseBean.getData().getDatas();
                                    collectArticleRecyclerAdapter.setData(datas);
                                    recyclerView.setAdapter(collectArticleRecyclerAdapter);
                                    refreshLayout.finishRefresh();
                                } else {
                                    startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.d("测试", "accept: "+throwable.getMessage());
                            }
                        });
            }
        });


        recyclerView = root.findViewById(R.id.collect_article_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mineService = RetrofitClient.getInstance().getService(MineService.class, getActivity());
        mineService.getCollectArticle(0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<CollectArticleBean>>() {
                    @Override
                    public void accept(BaseBean<CollectArticleBean> collectArticleBeanBaseBean) throws Throwable {
                        if (collectArticleBeanBaseBean.getErrorCode() == 0){
                            List<CollectArticleData> datas = collectArticleBeanBaseBean.getData().getDatas();
                            collectArticleRecyclerAdapter = new CollectArticleRecyclerAdapter(datas, getActivity());
                            recyclerView.setAdapter(collectArticleRecyclerAdapter);
                        }else {
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("测试", "accept: "+throwable.getMessage());
                    }
                });
    }
}