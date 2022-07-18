package com.example.wanapplication.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.wanapplication.R;
import com.example.wanapplication.bean.mine.OpenData;

import java.util.ArrayList;
import java.util.List;

public class OpenActivity extends AppCompatActivity {
    private List<OpenData> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        initDatas();
        initViews();
    }

    private void initDatas() {
        list.add(new OpenData("goweii/RxHttp", "对RxJava2+Retrofit2+OkHttp3的封装", "https://github.com/goweii/RxHttp"));
        list.add(new OpenData("goweii/SwipeBack", "Android Activity滑动返回。支持4个方向，支持下层Activity联动和自定义动效", "https://github.com/goweii/SwipeBack"));
        list.add(new OpenData("goweii/LazyFragment", "懒加载Fragment，提供可见和隐藏的回调，支持在ViewPager中多重嵌套，支持support和androidx", "https://github.com/goweii/LazyFragment"));
        list.add(new OpenData("goweii/ActionBarEx", "高拓展高自定义性ActionBar，完美替代Android系统默认", "https://github.com/goweii/ActionBarEx"));
        list.add(new OpenData("goweii/AnyLayer", "用于替代Android自带Dialog和PopupWindow", "https://github.com/goweii/AnyLayer"));
        list.add(new OpenData("goweii/AnyDialog", "Android高定制性，高易用性Dialog。", "https://github.com/goweii/AnyDialog"));
        list.add(new OpenData("goweii/VisualEffect", "Used to achieve visual effects in Android, such as Gaussian blur, mosaic, watermark, etc.", "https://github.com/goweii/VisualEffect"));
        list.add(new OpenData("goweii/RevealLayout", "揭示效果布局，可以指定2个子布局，以圆形揭示效果切换选中状态", "https://github.com/goweii/RevealLayout"));
        list.add(new OpenData("goweii/PercentImageView", "自由指定宽高比的ImageView", "https://github.com/goweii/PercentImageView"));
        list.add(new OpenData("goweii/Blurred", "Android高斯模糊库", "https://github.com/goweii/Blurred"));
        list.add(new OpenData("goweii/AnyPermission", "Android权限申请（运行时权限、未知应用安装权限、悬浮窗权限、显示通知和访问通知权限）", "https://github.com/goweii/AnyPermission"));
        list.add(new OpenData("goweii/KeyboardCompat", "Android软键盘兼容库，支持开启关闭监听和EditText获取焦点时布局上移", "https://github.com/goweii/KeyboardCompat"));
        list.add(new OpenData("JakeWharton/butterknife", "Bind Android views and callbacks to fields and methods.", "https://github.com/JakeWharton/butterknife"));
        list.add(new OpenData("greenrobot/EventBus", "Event bus for Android and Java that simplifies communication between Activities, Fragments, Threads, Services, etc. Less code, better quality.", "https://github.com/greenrobot/EventBus"));
        list.add(new OpenData("google/gson", "A Java serialization/deserialization library to convert Java Objects into JSON and back", "https://github.com/google/gson"));
        list.add(new OpenData("franmontiel/PersistentCookieJar", "A persistent CookieJar implementation for OkHttp 3 based on SharedPreferences.", "https://github.com/franmontiel/PersistentCookieJar"));
        list.add(new OpenData("bumptech/glide", "An image loading and caching library for Android focused on smooth scrolling", "https://github.com/bumptech/glide"));
        list.add(new OpenData("CymChad/BaseRecyclerViewAdapterHelper", "BRVAH:Powerful and flexible RecyclerAdapter", "https://github.com/CymChad/BaseRecyclerViewAdapterHelper"));
        list.add(new OpenData("scwang90/SmartRefreshLayout", "下拉刷新、上拉加载、二级刷新、淘宝二楼、RefreshLayout、OverScroll，Android智能下拉刷新框架，支持越界回弹、越界拖动，具有极强的扩展性，集成了几十种炫酷的Header和 Footer。", "https://github.com/scwang90/SmartRefreshLayout"));
        list.add(new OpenData("vinc3m1/RoundedImageView", "A fast ImageView that supports rounded corners, ovals, and circles.", "https://github.com/vinc3m1/RoundedImageView"));
        list.add(new OpenData("hdodenhof/CircleImageView", "A circular ImageView for Android", "https://github.com/hdodenhof/CircleImageView"));
        list.add(new OpenData("hackware1993/MagicIndicator", "A powerful, customizable and extensible ViewPager indicator framework. As the best alternative of ViewPagerIndicator, TabLayout and PagerSlidingTabStrip —— 强大、可定制、易扩展的 ViewPager 指示器框架。是ViewPagerIndicator、TabLayout、PagerSlidingTabStrip的最佳替代品。支持角标，更支持在非ViewPager场景下使用（使用hide()、show()切换Fragment或使用setVisibility切换FrameLayout里的View等）", "https://github.com/hackware1993/MagicIndicator"));
        list.add(new OpenData("chrisbanes/PhotoView", "Implementation of ImageView for Android that supports zooming, by various touch gestures.", "https://github.com/chrisbanes/PhotoView"));
        list.add(new OpenData("zhanghai/MaterialProgressBar", "Material Design ProgressBar with consistent appearance", "https://github.com/zhanghai/MaterialProgressBar"));
        list.add(new OpenData("google/flexbox-layout", "Flexbox for Android", "https://github.com/google/flexbox-layout"));
        list.add(new OpenData("youth5201314/banner", "Android广告图片轮播控件，支持无限循环和多种主题，可以灵活设置轮播样式、动画、轮播和切换时间、位置、图片加载框架等！", "https://github.com/youth5201314/banner"));
        list.add(new OpenData("Kennyc1012/MultiStateView", "Android View that displays different content based on its state", "https://github.com/Kennyc1012/MultiStateView"));
        list.add(new OpenData("JakeWharton/DiskLruCache", "Java implementation of a Disk-based LRU cache which specifically targets Android compatibility.", "https://github.com/JakeWharton/DiskLruCache"));
        list.add(new OpenData("daimajia/AndroidSwipeLayout", "The Most Powerful Swipe Layout!", "https://github.com/daimajia/AndroidSwipeLayout"));
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.open_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        OpenRecyclerAdapter openRecyclerAdapter = new OpenRecyclerAdapter(list, this);
        recyclerView.setAdapter(openRecyclerAdapter);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}