package com.example.wanapplication.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wanapplication.R;
import com.example.wanapplication.WebBannerActivity;

public class AboutMeActivity extends AppCompatActivity {

    private TextView githubURL;
    private TextView jianshuURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        initViews();
        initListener();
    }

    private void initViews() {
        githubURL = findViewById(R.id.tv_github);
        jianshuURL = findViewById(R.id.tv_jianshu);
    }

    private void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.ll_github).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutMeActivity.this
                        , WebBannerActivity.class);
                intent.putExtra("name","");
                intent.putExtra("url", githubURL.getText());
                startActivity(intent);
            }
        });
        findViewById(R.id.ll_jianshu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutMeActivity.this
                        , WebBannerActivity.class);
                intent.putExtra("name","");
                intent.putExtra("url", jianshuURL.getText());
                startActivity(intent);
            }
        });
    }
}