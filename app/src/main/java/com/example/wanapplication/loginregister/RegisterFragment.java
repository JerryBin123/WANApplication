package com.example.wanapplication.loginregister;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wanapplication.MainActivity;
import com.example.wanapplication.R;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.Data;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.LoginService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RegisterFragment extends Fragment {


    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null){
            root = inflater.inflate(R.layout.fragment_register, container, false);
        }
        initViews();
        return root;
    }

    private void initViews() {
        Button register = root.findViewById(R.id.register);
        EditText username = root.findViewById(R.id.register_id_name);
        EditText password = root.findViewById(R.id.register_id_pwd);
        EditText repassword = root.findViewById(R.id.register_id_repwd);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginService service = RetrofitClient.getInstance().getService(LoginService.class, getActivity());
                service.register(username.getText().toString()
                        ,password.getText().toString()
                        ,repassword.getText().toString()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseBean<Data>>() {
                            @Override
                            public void accept(BaseBean<Data> dataBaseBean) throws Throwable {
                                if (dataBaseBean.getErrorCode() == 0){
                                    service.login(username.getText().toString()
                                            , password.getText().toString()).subscribe(new Consumer<BaseBean<Data>>() {
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
                                }else{

                                }
                            }
                        });
            }
        });
    }
}