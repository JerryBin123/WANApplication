package com.example.wanapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.wanapplication.loginregister.LoginFragment;
import com.example.wanapplication.loginregister.LoginViewPagerAdapter;
import com.example.wanapplication.loginregister.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

public class LoginRegisterActivity extends AppCompatActivity {

    private ViewPager2 viewpager;
    private FragmentToFragment fragmentToFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        initViews();
    }

    private void initViews() {
        viewpager = findViewById(R.id.lrviewpager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        LoginViewPagerAdapter loginViewPagerAdapter = new LoginViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(loginViewPagerAdapter);
        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setSwitchFragment(FragmentToFragment fragmentToFragment){
        this.fragmentToFragment = fragmentToFragment;
    }
    public void forSkip(){
        if (fragmentToFragment != null){
            fragmentToFragment.switchFragment(viewpager);
        }
    }

    public interface FragmentToFragment{
        void switchFragment(ViewPager2 viewPager);
    }
}