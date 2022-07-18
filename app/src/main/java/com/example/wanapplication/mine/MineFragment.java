package com.example.wanapplication.mine;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanapplication.LoginRegisterActivity;
import com.example.wanapplication.R;
import com.example.wanapplication.UserWholeInfo;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.Data;
import com.example.wanapplication.bean.MineData;
import com.example.wanapplication.loginregister.LoginInformation;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MineFragment extends Fragment implements View.OnClickListener {


    private View root;
    private List<MineData> data = new ArrayList<>();
    private MineService mineService;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_mine, container, false);
        }
        intent = new Intent(getActivity(), PersonActivity.class);
        initData();
        initView();
        return root;
    }

    private void initView() {
        View goLogin = root.findViewById(R.id.go_to_login);
        ImageView person = root.findViewById(R.id.iv_person);
        TextView unLogin = root.findViewById(R.id.unlogin);
        View loginInfo = root.findViewById(R.id.login_info);
        TextView loginGrade = root.findViewById(R.id.login_grade);
        TextView loginRank = root.findViewById(R.id.login_rank);
        TextView loginName = root.findViewById(R.id.login_name);
        root.findViewById(R.id.iv_message).setOnClickListener(this);

        mineService = RetrofitClient.getInstance().getService(MineService.class, getActivity());
        mineService.getUserInfo().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<UserWholeInfo>>() {
                    @Override
                    public void accept(BaseBean<UserWholeInfo> userWholeInfoBaseBean) throws Throwable {
                        if (userWholeInfoBaseBean.getErrorCode() == 0){
                            loginName.setText(userWholeInfoBaseBean.getData().getUserInfo().getUsername());
                            loginGrade.setText("等级："+userWholeInfoBaseBean.getData().getCoinInfo().getLevel());
                            loginRank.setText("排名："+userWholeInfoBaseBean.getData().getCoinInfo().getRank());
                            goLogin.setVisibility(View.GONE);
                            loginInfo.setVisibility(View.VISIBLE);
                            person.setVisibility(View.VISIBLE);
                            intent.putExtra("id", userWholeInfoBaseBean.getData().getUserInfo().getId()+"");
                            intent.putExtra("name", userWholeInfoBaseBean.getData().getUserInfo().getUsername());
                        }else{
                            goLogin.setVisibility(View.VISIBLE);
                            loginInfo.setVisibility(View.GONE);
                            person.setVisibility(View.GONE);
                        }
                    }
                });

        Data user = LoginInformation.getInstance().getLoginInformation();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_mine);
        goLogin.setOnClickListener(this);
        person.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MineAdapter(MineFragment.this.getContext(), data));
    }

    private void initData() {
        MineData mineData1 = new MineData("我的积分", R.drawable.ic_coin);
        data.add(mineData1);
        MineData mineData2 = new MineData("我的分享", R.drawable.ic_share_article);
        data.add(mineData2);
        MineData mineData3 = new MineData("我的收藏", R.drawable.ic_collect);
        data.add(mineData3);
        MineData mineData4 = new MineData("我的书签", R.drawable.ic_read_later);
        data.add(mineData4);
        MineData mineData5 = new MineData("阅读历史", R.drawable.ic_read_record);
        data.add(mineData5);
        MineData mineData6 = new MineData("开源项目", R.drawable.ic_github);
        data.add(mineData6);
        MineData mineData7 = new MineData("关于作者", R.drawable.ic_about);
        data.add(mineData7);
        MineData mineData8 = new MineData("系统设置", R.drawable.ic_setting);
        data.add(mineData8);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.go_to_login:
                startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                break;
            case R.id.iv_person:
                startActivity(intent);
                break;
            case R.id.iv_message:
                startActivity(new Intent(getActivity(),MessageActivity.class));
                break;
        }
    }
}