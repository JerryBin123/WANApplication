package com.example.wanapplication.loginregister;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wanapplication.LoginRegisterActivity;
import com.example.wanapplication.MainActivity;
import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.Data;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.LoginService;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;

import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Retrofit;


public class LoginFragment extends Fragment implements View.OnClickListener {


    private View root;
    private EditText loginName;
    private EditText loginPwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_login, container, false);
        }
        initViews();
        return root;
    }

    private void initViews() {
        Button btn = root.findViewById(R.id.login_btn);
        loginName = root.findViewById(R.id.login_id_name);
        loginPwd = root.findViewById(R.id.login_id_pwd);
        loginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("13539097640"))
                loginPwd.setText("19991025as");
            }
        });

        btn.setOnClickListener(this);
        root.findViewById(R.id.tv_go_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                String username = loginName.getText().toString();
                String password = loginPwd.getText().toString();
                LoginService loginService = RetrofitClient.getInstance().getService(LoginService.class, getContext());
                loginService.login(username, password).subscribe(new Consumer<BaseBean<Data>>() {
                    @Override
                    public void accept(BaseBean<Data> dataBaseBean) throws Throwable {
                        if (dataBaseBean.getErrorCode() == 0){
                            LoginInformation loginInformation = LoginInformation.getInstance();
                            loginInformation.setLoginInformation(dataBaseBean.getData());
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("fragmentId",3);
                            startActivity(intent);
                        }else {
                            Log.d("liu", "accept: " + dataBaseBean.getErrorMsg());
                        }
                    }
                });
                break;
            case R.id.tv_go_register:
                LoginRegisterActivity activity = (LoginRegisterActivity) getActivity();
                activity.setSwitchFragment(new LoginRegisterActivity.FragmentToFragment() {
                    @Override
                    public void switchFragment(ViewPager2 viewPager) {
                        viewPager.setCurrentItem(1);
                    }
                });
                activity.forSkip();
                break;
        }
    }
}