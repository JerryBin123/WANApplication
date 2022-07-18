package com.example.wanapplication.mine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.mine.CollectWebData;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CollectWebFragment extends Fragment {


    private View root;
    private CollectWebRecyclerAdapter collectWebRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_collect_web, container, false);
        }
        initViews();
        return root;
    }

    private void initViews() {
        RecyclerView recyclerView = root.findViewById(R.id.collect_web_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        MineService mineService = RetrofitClient.getInstance().getService(MineService.class, getContext());
        mineService.getCollectWeb().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<List<CollectWebData>>>() {
                    @Override
                    public void accept(BaseBean<List<CollectWebData>> listBaseBean) throws Throwable {
                        List<CollectWebData> data = listBaseBean.getData();
                        collectWebRecyclerAdapter = new CollectWebRecyclerAdapter(data, getActivity());
                        recyclerView.setAdapter(collectWebRecyclerAdapter);
                    }
                });
    }
}