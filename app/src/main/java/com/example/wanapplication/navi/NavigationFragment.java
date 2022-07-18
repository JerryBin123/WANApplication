package com.example.wanapplication.navi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.bean.knowledge.KnowledgeData;
import com.example.wanapplication.bean.knowledge.NavigationBean;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.KnowledgeService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NavigationFragment extends Fragment {


    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_navigation, container, false);
        }
        initViews();
        return root;
    }

    private void initViews() {
        RecyclerView recyclerView = root.findViewById(R.id.navigation_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        KnowledgeService navigationService = RetrofitClient.getInstance().getService(KnowledgeService.class, getContext());
        navigationService.getNavi().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<List<NavigationBean>>>() {
                    @Override
                    public void accept(BaseBean<List<NavigationBean>> listBaseBean) throws Throwable {
                        List<NavigationBean> data = listBaseBean.getData();
                        NavigationRecyclerAdapter navigationRecyclerAdapter = new NavigationRecyclerAdapter(data, getActivity());
                        recyclerView.setAdapter(navigationRecyclerAdapter);
                    }
                });
    }
}