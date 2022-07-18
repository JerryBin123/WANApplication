package com.example.wanapplication.mine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wanapplication.MainActivity;
import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.Data;
import com.example.wanapplication.home.HomeFragment;
import com.example.wanapplication.loginregister.LoginInformation;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.LoginService;
import com.example.wanapplication.utils.CacheUtil;
import com.youth.banner.Banner;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private SwitchCompat bannerBtn;
    private SwitchCompat topBtn;
    private Intent intent;
    private SharedPreferences setting;
    private TextView cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
    }

    private void initViews() {
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        intent = new Intent();
        intent.setAction("com.example.bannerCheck");
        TextView logoutView = findViewById(R.id.logout);
        bannerBtn = findViewById(R.id.banner_btn);
        boolean banner = setting.getBoolean("banner", true);
        bannerBtn.setChecked(banner);
        topBtn = findViewById(R.id.setting_top);

        boolean top = setting.getBoolean("top",true);
        topBtn.setChecked(top);
        logoutView.setOnClickListener(this);
        bannerBtn.setOnClickListener(this);
        topBtn.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        cache = findViewById(R.id.tv_cache);
        try {
            cache.setText(CacheUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.delete_cache).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                AlertDialog dialog = new AlertDialog.Builder(this).create();//创建对话框
                dialog.setTitle("确定要退出登录吗？");//设置对话框标题
                //分别设置三个button
                dialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginService loginService = RetrofitClient.getInstance().getService(LoginService.class, SettingActivity.this);
                        loginService.logout().subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<BaseBean<Data>>() {
                                    @Override
                                    public void accept(BaseBean<Data> dataBaseBean) throws Throwable {
                                        LoginInformation loginInformation = LoginInformation.getInstance();
                                        loginInformation.setLoginInformation(dataBaseBean.getData());
                                        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                                        intent.putExtra("fragmentId", 3);
                                        startActivity(intent);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Throwable {
                                        Log.d("测试", "accept: " + throwable.getMessage());
                                    }
                                });
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL,"取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                });
                dialog.show();//显示对话框
                break;

            case R.id.banner_btn:
                intent.putExtra("check",bannerBtn.isChecked());
                sendBroadcast(intent);
                setting.edit().putBoolean("banner",bannerBtn.isChecked()).commit();
                break;
            case R.id.setting_top:
                Intent intentTop = new Intent("com.top");
                intentTop.putExtra("top",topBtn.isChecked());
                sendBroadcast(intentTop);
                setting.edit().putBoolean("top",topBtn.isChecked()).commit();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.delete_cache:
                AlertDialog cacheDialog = new AlertDialog.Builder(this).create();//创建对话框
                cacheDialog.setTitle("确定要清除缓存吗？");//设置对话框标题
                //分别设置三个button
                cacheDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheUtil.clearAllCache(SettingActivity.this);
                        try {
                            cache.setText(CacheUtil.getTotalCacheSize(SettingActivity.this));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                cacheDialog.setButton(DialogInterface.BUTTON_NEUTRAL,"取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                });
                cacheDialog.show();//显示对话框
                break;
        }
    }
}