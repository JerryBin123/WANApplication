package com.example.wanapplication.navi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.bean.knowledge.KnowledgeData;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.KnowledgeService;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class KnowledgeFragment extends Fragment implements NaviFragment.PageListener {


    private View root;
    private RecyclerView recyclerView;
    private KnowledgeRecyclerAdapter knowledgeRecyclerAdapter;
    private TestViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_knowledge, container, false);
        }
        initViews();
        model = new ViewModelProvider(getActivity()).get(TestViewModel.class);
        model.getPage().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("测试", "onChanged1: " + integer);
            }
        });
        return root;
    }

    private void initViews() {
        recyclerView = root.findViewById(R.id.knowledge_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        KnowledgeService knowledgeService = RetrofitClient.getInstance().getService(KnowledgeService.class, getContext());
        knowledgeService.getKnowledge().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<List<KnowledgeData>>>() {
                    @Override
                    public void accept(BaseBean<List<KnowledgeData>> listBaseBean) throws Throwable {
                        List<KnowledgeData> data = listBaseBean.getData();
                        Log.d("abc", "accept: " + data.get(0).getChildren().get(0).getName());
                        knowledgeRecyclerAdapter = new KnowledgeRecyclerAdapter(data, getActivity());
                        recyclerView.setAdapter(knowledgeRecyclerAdapter);
                    }
                });

    }

    @Override
    public void onPage(int page) {
        Log.d("测试", "onPage: " + page);
    }
}