package com.example.wanapplication.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.home.ArticleBean;
import com.example.wanapplication.bean.home.Datas;
import com.example.wanapplication.bean.home.HotWordData;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.HomeService;
import com.google.android.flexbox.FlexboxLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchActivity extends AppCompatActivity {

    private FlexboxLayout flexboxLayout;
    private HomeService service;
    private RecyclerView searchRecycler;
    private EditText word;
    private ImageView close;
    private SharedPreferences sp;
    private ImageView back;
    private Set<String> saveWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sp = getSharedPreferences("history", MODE_PRIVATE);
        saveWords = sp.getStringSet("historyWord", new HashSet<>());
        initViews();
        initHistoryViews();
        initListener();
    }

    private void initHistoryViews() {
        FlexboxLayout historyFlexboxLayout = findViewById(R.id.fbl_history);
        Set<String> historyWord = sp.getStringSet("historyWord", new HashSet<>());
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        historyFlexboxLayout.removeAllViews();
        for (String i : historyWord) {
            TextView tv = new TextView(SearchActivity.this);
            tv.setPadding(50, 10, 50, 10);
            tv.setText(i);
            tv.setMaxEms(10);
            tv.setSingleLine();
            tv.setBackgroundResource(R.drawable.knowledge_item_background);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager imm = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    }
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    service.getSearchArticle(0, tv.getText().toString()).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ArticleBean>() {
                                @Override
                                public void accept(ArticleBean articleBean) throws Throwable {
                                    if (articleBean.getErrorCode() == 0) {
                                        word.setText(tv.getText().toString());
                                        saveWords.add(tv.getText().toString());

                                        saveToSp(saveWords);

                                        findViewById(R.id.search).setVisibility(View.GONE);
                                        findViewById(R.id.history_search).setVisibility(View.GONE);
                                        searchRecycler.setVisibility(View.VISIBLE);
                                        List<Datas> datas = articleBean.getData().getDatas();
                                        HomeRecyclerAdapter homeRecyclerAdapter =
                                                new HomeRecyclerAdapter(SearchActivity.this);
                                        homeRecyclerAdapter.setData(datas);
                                        searchRecycler.setAdapter(homeRecyclerAdapter);
                                    }
                                }
                            });
                }
            });
            historyFlexboxLayout.addView(tv, layoutParams);
        }
    }

    private void initListener() {
        findViewById(R.id.clear_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWords.clear();

                saveToSp(saveWords);
                initHistoryViews();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.setText("");
                close.setVisibility(View.GONE);
                findViewById(R.id.search).setVisibility(View.VISIBLE);
                findViewById(R.id.history_search).setVisibility(View.VISIBLE);
                searchRecycler.setVisibility(View.GONE);
            }
        });
        word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                close.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = word.getText().toString();
                saveWords.add(s);
                Log.d("测试", "onClick: " + saveWords);
                saveToSp(saveWords);
                initHistoryViews();
                InputMethodManager imm = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                }
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                service.getSearchArticle(0, s).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ArticleBean>() {
                            @Override
                            public void accept(ArticleBean articleBean) throws Throwable {
                                if (articleBean.getErrorCode() == 0) {
                                    findViewById(R.id.search).setVisibility(View.GONE);
                                    findViewById(R.id.history_search).setVisibility(View.GONE);
                                    searchRecycler.setVisibility(View.VISIBLE);
                                    List<Datas> datas = articleBean.getData().getDatas();
                                    HomeRecyclerAdapter homeRecyclerAdapter =
                                            new HomeRecyclerAdapter(SearchActivity.this);
                                    homeRecyclerAdapter.setData(datas);
                                    searchRecycler.setAdapter(homeRecyclerAdapter);
                                }
                            }
                        });
            }
        });
    }

    private void initViews() {
        back = findViewById(R.id.iv_back);
        close = findViewById(R.id.iv_close);
        word = findViewById(R.id.search_word);
        searchRecycler = findViewById(R.id.search_result_recycler);
        flexboxLayout = findViewById(R.id.fbl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        searchRecycler.setLayoutManager(linearLayoutManager);
        service = RetrofitClient.getInstance().getService(HomeService.class, this);
        service.getHotWord().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<List<HotWordData>>>() {
                    @Override
                    public void accept(BaseBean<List<HotWordData>> listBaseBean) throws Throwable {
                        List<HotWordData> data = listBaseBean.getData();
                        LinearLayout.LayoutParams layoutParams =
                                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(10, 5, 10, 5);
                        flexboxLayout.removeAllViews();
                        for (int i = 0; i < data.size(); i++) {
                            TextView tv = new TextView(SearchActivity.this);
                            tv.setPadding(50, 10, 50, 10);
                            tv.setText(data.get(i).getName());
                            tv.setMaxEms(10);
                            tv.setSingleLine();
                            tv.setBackgroundResource(R.drawable.knowledge_item_background);
                            tv.setLayoutParams(layoutParams);
                            int finalI = i;
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    InputMethodManager imm = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    }
                                    // 隐藏软键盘
                                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                                    service.getSearchArticle(0, tv.getText().toString()).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<ArticleBean>() {
                                                @Override
                                                public void accept(ArticleBean articleBean) throws Throwable {
                                                    if (articleBean.getErrorCode() == 0) {
                                                        word.setText(tv.getText().toString());
                                                        saveWords.add(tv.getText().toString());

                                                        saveToSp(saveWords);
                                                        initHistoryViews();

                                                        findViewById(R.id.search).setVisibility(View.GONE);
                                                        findViewById(R.id.history_search).setVisibility(View.GONE);
                                                        searchRecycler.setVisibility(View.VISIBLE);
                                                        List<Datas> datas = articleBean.getData().getDatas();
                                                        HomeRecyclerAdapter homeRecyclerAdapter =
                                                                new HomeRecyclerAdapter(SearchActivity.this);
                                                        homeRecyclerAdapter.setData(datas);
                                                        searchRecycler.setAdapter(homeRecyclerAdapter);
                                                    }
                                                }
                                            });
                                }
                            });
                            flexboxLayout.addView(tv, layoutParams);
                        }
                    }
                });

    }

    private void saveToSp(Set<String> data) {
        boolean isSuccess = sp.edit().clear().putStringSet("historyWord", data).commit();
        if (isSuccess){
            Log.i("AAA","success");
        }
    }
}