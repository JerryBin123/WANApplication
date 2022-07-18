package com.example.wanapplication.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanapplication.R;
import com.example.wanapplication.WebBannerActivity;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ShareActicleActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText title;
    private EditText link;
    private MineService mineService;
    private String linkString;
    private String titleString;
    private TextView refreshTitle;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_acticle);
        initViews();
        initListener();
    }

    private void initViews() {
        mineService = RetrofitClient.getInstance().getService(MineService.class, this);
        title = findViewById(R.id.share_article_title);
        link = findViewById(R.id.share_article_link);
        refreshTitle = findViewById(R.id.refresh_title);
        back = findViewById(R.id.iv_back);
    }

    private void initListener() {
        Button button = findViewById(R.id.share_article_button);
        button.setOnClickListener(this);
        TextView openLink = findViewById(R.id.share_article_open_link);
        openLink.setOnClickListener(this);
        refreshTitle.setOnClickListener(this);
        back.setOnClickListener(this);
        link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String webTitle = getWebTitle(editable.toString());
//                        title.setText(webTitle);
//                        ShareActicleActivity.this.runOnUiThread();
//                    }
//                }).start();
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        String webTitle = getWebTitle(editable.toString());
                        emitter.onNext(webTitle);
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Throwable {
                                title.setText(s);
                            }
                        });


            }
        });
    }

    private String getWebTitle(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements head = document.select("head");
            Elements titleLinks = head.get(0).select("title");
            return titleLinks.get(0).text();
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share_article_button:
                mineService.shareArticle(title.getText().toString(),link.getText().toString())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Throwable {
                                if (baseBean.getErrorCode() == 0){
                                    startActivity(new Intent(ShareActicleActivity.this,
                                            MineShareActivity.class));
                                    finish();
                                }
                            }
                        });
                break;
            case R.id.share_article_open_link:
                titleString = title.getText().toString();
                linkString = link.getText().toString();
                Intent intent = new Intent(this, WebBannerActivity.class);
                intent.putExtra("name",titleString);
                intent.putExtra("url", linkString);
                Log.d("测试", "onClick: " + linkString);
                startActivity(intent);
                break;
            case R.id.refresh_title:
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        String webTitle = getWebTitle(link.getText().toString());
                        emitter.onNext(webTitle);
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Throwable {
                                title.setText(s);
                            }
                        });
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}