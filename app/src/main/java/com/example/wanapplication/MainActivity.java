package com.example.wanapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.wanapplication.home.HomeFragment;
import com.example.wanapplication.home.NewHomeFragment;
import com.example.wanapplication.mine.MineFragment;
import com.example.wanapplication.navi.NaviFragment;
import com.example.wanapplication.qa.NewQAFragment;
import com.example.wanapplication.qa.QAFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {


    private ViewPager2 viewPager;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Log.d("liu", "onCreate: 刚刚创建了");
        int fragmentId = getIntent().getIntExtra("fragmentId", 0);
        viewPager.setCurrentItem(fragmentId);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.d("liu", "onCreateView: 创建了view" + name);
        return super.onCreateView(parent, name, context, attrs);
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewHomeFragment());
        fragments.add(new NewQAFragment());
        fragments.add(new NaviFragment());
        fragments.add(new MineFragment());
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(fragmentViewPagerAdapter);
        bottomNavigationView.setOnItemSelectedListener(this);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (menuItem != null){
                    menuItem.setChecked(false);
                }else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
                //viewPager.setUserInputEnabled(viewPager.getCurrentItem() != 2);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bottom_id_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.bottom_id_qa:
                viewPager.setCurrentItem(1);
                break;
            case R.id.bottom_id_navi:
                viewPager.setCurrentItem(2);
                break;
            case R.id.bottom_id_mine:
                viewPager.setCurrentItem(3);
                break;
        }
        return false;
    }

}