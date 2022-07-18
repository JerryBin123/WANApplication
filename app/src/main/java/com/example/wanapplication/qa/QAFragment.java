package com.example.wanapplication.qa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.NewQAService;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class QAFragment extends Fragment implements QAContact.IQAView {


    private View root;
    private QARecyclerAdapter qaRecyclerAdapter;
    private NewQAService qaService;
    private RecyclerView recyclerView;
    private int currPage;
    private QAPresenter qaPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_q_a, container, false);
        }
        initViews();
        return root;
    }

    private void initViews() {
        RefreshLayout refreshLayout = root.findViewById(R.id.qa_smart_refresh);
        ClassicsHeader.REFRESH_HEADER_PULLING = getString(R.string.header_pulldown);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.header_pulldown);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.header_pulldown);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.header_pulldown);
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setTextSizeTitle(10);
        classicsHeader.setDrawableSize(13);
        classicsHeader.setEnableLastTime(false);
        refreshLayout.setRefreshHeader(classicsHeader);
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                qaPresenter.getQA(0);
                refreshLayout.finishRefresh();
                currPage = 0;
//                qaService.getQA(0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<ArticleBean>() {
//                            @Override
//                            public void accept(ArticleBean articleBean) throws Throwable {
//                                List<Datas> datas = articleBean.getData().getDatas();
//                                qaRecyclerAdapter.setData(datas);
//                                recyclerView.setAdapter(qaRecyclerAdapter);
//                                currPage = 0;
//                                refreshLayout.finishRefresh();
//                            }
//                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                qaPresenter.loadMoreQA(++currPage);
//                qaService.getQA(currPage + 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<ArticleBean>() {
//                            @Override
//                            public void accept(ArticleBean articleBean) throws Throwable {
//                                List<Datas> datas = articleBean.getData().getDatas();
//                                qaRecyclerAdapter.addData(datas);
//                                recyclerView.setAdapter(qaRecyclerAdapter);
//                                currPage += datas.size();
//                                if (currPage > articleBean.getData().getTotal()){
//                                    refreshLayout.finishLoadMoreWithNoMoreData();
//                                }
//                            }
//                        });
                refreshLayout.finishLoadMore();
            }
        });
        recyclerView = root.findViewById(R.id.qa_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        List<Datas> datas = new ArrayList<>();
        qaRecyclerAdapter = new QARecyclerAdapter(datas, getActivity());
        recyclerView.setAdapter(qaRecyclerAdapter);

        qaPresenter = new QAPresenter(getContext(), this);
        qaPresenter.getQA(0);


        qaService = RetrofitClient.getInstance().getService(NewQAService.class, getContext());
//        qaService.getQA(0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<ArticleBean>() {
//                    @Override
//                    public void accept(ArticleBean articleBean) throws Throwable {
//                        List<Datas> datas = articleBean.getData().getDatas();
//                        qaRecyclerAdapter = new QARecyclerAdapter(datas, getActivity());
//                        recyclerView.setAdapter(qaRecyclerAdapter);
//                    }
//                });


    }

    @Override
    public void getSuccess(List<Datas> datas) {
            qaRecyclerAdapter.setData(datas);
    }

    @Override
    public void getLoadMoreSuccess(List<Datas> datas) {
        qaRecyclerAdapter.addData(datas);
    }

    @Override
    public void getError(Throwable throwable) {

    }
}