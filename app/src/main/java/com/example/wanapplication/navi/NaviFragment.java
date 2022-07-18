package com.example.wanapplication.navi;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanapplication.FragmentViewPagerAdapter;
import com.example.wanapplication.R;
import com.example.wanapplication.home.BannerChecked;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class NaviFragment extends Fragment {

    private PageListener pageListener;

    public void setOnBannerListener(PageListener pageListener){
        this.pageListener = pageListener;
    }

    interface PageListener{
        void onPage(int page);
    }

    private View root;
    private ViewPager2 viewPager;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;
    private TabLayout tabLayout;
    private String[] titles = new String[]{"体系","导航"};
    private TabLayoutMediator tabLayoutMediator;
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
            root = inflater.inflate(R.layout.fragment_navi, container, false);
        }
        model = new ViewModelProvider(getActivity()).get(TestViewModel.class);
        initViews();
        return root;
    }

    private void initViews() {
        tabLayout = root.findViewById(R.id.tablayout);
        viewPager = root.findViewById(R.id.navi_viewpager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new KnowledgeFragment());
        fragments.add(new NavigationFragment());
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getActivity().getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(fragmentViewPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                model.getPage().setValue(position);
                if (pageListener != null){
                    pageListener.onPage(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }
        });
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, false, true,
                (tab, position) -> tab.setText(titles[position]));
        tabLayoutMediator.attach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageListener){
            pageListener = (PageListener) context;
            Log.d("测试", "onAttach: " + pageListener);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        pageListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
    }
}