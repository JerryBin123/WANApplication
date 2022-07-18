package com.example.wanapplication.navi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wanapplication.FragmentViewPagerAdapter;
import com.example.wanapplication.R;
import com.example.wanapplication.bean.knowledge.KnowledgeData;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeArticleActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private List<String> titles = new ArrayList<>();
    private TabLayoutMediator tabLayoutMediator;
    private KnowledgeData data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_article);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        viewPager = findViewById(R.id.knowledge_article_viewpager);
        tabLayout = findViewById(R.id.knowledge_article_tabLayout);
        TextView textView = findViewById(R.id.knowledge_title);
        Intent intent = getIntent();
        int currPage = intent.getIntExtra("currPage", 0);
        data = intent.getParcelableExtra("data");
        Log.d("kkk", "initViews: " + data);

        textView.setText(data.getName());

        List<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < data.getChildren().size(); i++) {
            fragments.add(KnowledgeArticleFragment.newInstance(data.getChildren().get(i).getId()));
            titles.add(data.getChildren().get(i).getName());
        }
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(fragmentViewPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
        viewPager.setCurrentItem(currPage);
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, false, true,
                (tab, position) -> tab.setText(titles.get(position)));
        tabLayoutMediator.attach();
    }
}