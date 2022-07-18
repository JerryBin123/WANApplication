package com.example.wanapplication.home;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wanapplication.R;
import com.example.wanapplication.WebBannerActivity;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.BannerData;
import com.example.wanapplication.bean.home.BannerListData;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.HomeService;
import com.example.wanapplication.widget.TestActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    private View root;
    private HomeService homeService;
    private HomeRecyclerAdapter homeRecyclerAdapter;
    private RecyclerView recyclerView;
    private int currPage;
    private RecyclerView topRecycler;
    private SharedPreferences setting;
    private ActivityResultLauncher<ScanOptions> scanOptionsActivityResultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanOptionsActivityResultLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if (result.getContents() == null) {
                        Log.d("测试", "Cancelled: ");
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("测试", "onActivityResult: " + result.getContents());
                        Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_home, container, false);
        }

        setting = getActivity().getSharedPreferences("setting", Context.MODE_PRIVATE);
        initBanner();
        initViews();
        initListener();
        //progressDialog.dismiss();
        return root;
    }


    private void initListener() {
        root.findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
        root.findViewById(R.id.iv_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanOptionsActivityResultLauncher.launch(new ScanOptions());
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("测试", "Cancelled: ");
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("测试", "onActivityResult: "+ result.getContents());
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("测试", "result: ");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initBanner() {
        Banner banner = root.findViewById(R.id.banner);
        homeService = RetrofitClient.getInstance().getService(HomeService.class,getContext());
        homeService.getBanners().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerListData>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BannerListData bannerListData) {
                banner.setAdapter(new BannerImageAdapter<BannerData>(bannerListData.getData()) {
                    @Override
                    public void onBindView(BannerImageHolder holder, BannerData data, int position, int size) {
                        //图片加载自己实现
                        Glide.with(holder.itemView)
                                .load(data.getImagePath())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .into(holder.imageView);
                    }
                })
                        .addBannerLifecycleObserver(getActivity())//添加生命周期观察者
                        .setIndicator(new CircleIndicator(getActivity()));
                //更多使用方法仔细阅读文档，或者查看demo
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        Intent intent = new Intent(getActivity(), WebBannerActivity.class);
                        intent.putExtra("name",bannerListData.getData().get(position).getTitle());
                        intent.putExtra("url", bannerListData.getData().get(position).getUrl());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("liu", "onError: "+ e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
        BannerChecked bannerChecked = new BannerChecked();
        bannerChecked.setOnBannerListener(new BannerChecked.OnBannerListener() {
            @Override
            public void onBanner(Boolean open) {
                if (open){
                    banner.setVisibility(View.VISIBLE);
                }else {
                    banner.setVisibility(View.GONE);
                }
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.bannerCheck");
        getActivity().registerReceiver(bannerChecked,intentFilter);

        boolean bannerOpen = setting.getBoolean("banner", true);
        if (bannerOpen){
            banner.setVisibility(View.VISIBLE);
        }else{
            banner.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        homeRecyclerAdapter = new HomeRecyclerAdapter(getContext());
        RefreshLayout refreshLayout = root.findViewById(R.id.smart_refresh);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                homeService.getArticle(0).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ArticleBean>() {
                            @Override
                            public void accept(ArticleBean articleBean) throws Throwable {
                                List<Datas> datas = articleBean.getData().getDatas();
                                homeRecyclerAdapter.setData(datas);
                                recyclerView.setAdapter(homeRecyclerAdapter);
                                currPage = 0;
                                refreshLayout.finishRefresh();
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                homeService.getArticle(currPage + 1).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ArticleBean>() {
                            @Override
                            public void accept(ArticleBean articleBean) throws Throwable {
                                List<Datas> datas = articleBean.getData().getDatas();
                                homeRecyclerAdapter.addData(datas);
                                recyclerView.setAdapter(homeRecyclerAdapter);
                                currPage += datas.size();
                                if (currPage > articleBean.getData().getTotal()){
                                    refreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            }
                        });
                refreshLayout.finishLoadMore();
            }
        });
            
        topRecycler = root.findViewById(R.id.top_recyclerview);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        topRecycler.setLayoutManager(linearLayoutManager1);
        homeService.getTopArticle().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<List<Datas>>>() {
                    @Override
                    public void accept(BaseBean<List<Datas>> listBaseBean) throws Throwable {
                        if (listBaseBean.getErrorCode() == 0){
                            List<Datas> data = listBaseBean.getData();
                            HomeTopRecyclerAdapter homeTopRecyclerAdapter = new HomeTopRecyclerAdapter(getContext());
                            homeTopRecyclerAdapter.setData(data);
                            topRecycler.setAdapter(homeTopRecyclerAdapter);
                        }
                    }
                });


        TopChecked topChecked = new TopChecked();
        topChecked.setOnTopListener(new TopChecked.OnTopListener() {
            @Override
            public void onTop(Boolean top) {
                if (top){
                    topRecycler.setVisibility(View.VISIBLE);
                }else{
                    topRecycler.setVisibility(View.GONE);
                }
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.top");
        getActivity().registerReceiver(topChecked,intentFilter);

        boolean top = setting.getBoolean("top", true);
        if (top){
            topRecycler.setVisibility(View.VISIBLE);
        }else{
            topRecycler.setVisibility(View.GONE);
        }

        recyclerView = root.findViewById(R.id.home_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);



        homeService.getArticle(0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ArticleBean articleBean) {
                        List<Datas> datas = articleBean.getData().getDatas();
                        currPage = datas.size();
                        homeRecyclerAdapter.addData(datas);
                        recyclerView.setAdapter(homeRecyclerAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("liu", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}