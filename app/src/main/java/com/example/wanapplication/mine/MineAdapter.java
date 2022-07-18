package com.example.wanapplication.mine;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanapplication.R;
import com.example.wanapplication.UserWholeInfo;
import com.example.wanapplication.bean.BaseBean;
import com.example.wanapplication.bean.MineData;
import com.example.wanapplication.network.RetrofitClient;
import com.example.wanapplication.network.service.MineService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MineAdapter extends RecyclerView.Adapter<MineAdapter.myViewHodler> {

    private List<MineData> data;
    private Context context;
    private LayoutInflater layoutInflater;
    //可以考虑使用ARouter
    private Class[] jumpActivity = {CoinActivity.class,MineShareActivity.class,CollectionActivity.class,
            ReadLaterActivity.class,NewRecordActivity.class, OpenActivity.class,
            AboutMeActivity.class,SettingActivity.class};
    private MineService mineService;

    public MineAdapter(Context context, List<MineData> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MineAdapter.myViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.mine_item, parent, false);
        return new myViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MineAdapter.myViewHodler holder, int position) {
        if (data.get(position).getName() == "我的积分"){
            holder.coinCount.setVisibility(View.VISIBLE);
            mineService = RetrofitClient.getInstance().getService(MineService.class, context);
            mineService.getUserInfo().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseBean<UserWholeInfo>>() {
                        @Override
                        public void accept(BaseBean<UserWholeInfo> userWholeInfoBaseBean) throws Throwable {
                            if (userWholeInfoBaseBean.getErrorCode() == 0) {
                                holder.coinCount.setText(userWholeInfoBaseBean.getData().getCoinInfo().getCoinCount()+"");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Throwable {
                            Log.d("测试", "accept: " + throwable.getMessage());
                        }
                    });
        }
        Log.d("liu", "onBindViewHolder: " + data.get(position));
        holder.imageView.setImageResource(data.get(position).getImageId());

        holder.textView.setText(data.get(position).getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, data.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        int i = holder.getAdapterPosition();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, jumpActivity[i]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class myViewHodler extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private TextView coinCount;

        public myViewHodler(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_mine_rv);
            imageView = itemView.findViewById(R.id.iv_mine_rv);
            coinCount = itemView.findViewById(R.id.mine_coin_count);
        }
    }
}
