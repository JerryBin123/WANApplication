package com.example.wanapplication.navi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.KnowledgeService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class KnowledgeArticleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private View root;

    public KnowledgeArticleFragment() {
        // Required empty public constructor
    }

    public static KnowledgeArticleFragment newInstance(int param1) {
        KnowledgeArticleFragment fragment = new KnowledgeArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_knowledge_article, container, false);
        }
        initViews();
        return root;
    }

    private void initViews() {
        RecyclerView recyclerView = root.findViewById(R.id.knowledge_article_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        KnowledgeService articleService = RetrofitClient.getInstance().getService(KnowledgeService.class, getContext());
        articleService.getArticle(0,mParam1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArticleBean>() {
                    @Override
                    public void accept(ArticleBean articleBean) throws Throwable {
                        List<Datas> datas = articleBean.getData().getDatas();
                        KnowledgeArticleRecyclerAdapter knowledgeArticleRecyclerAdapter =
                                new KnowledgeArticleRecyclerAdapter(datas, getActivity());
                        recyclerView.setAdapter(knowledgeArticleRecyclerAdapter);
                    }
                });
    }
}