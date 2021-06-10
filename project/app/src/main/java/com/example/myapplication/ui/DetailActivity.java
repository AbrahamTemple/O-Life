package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.domain.Order;
import com.skydoves.elasticviews.ElasticAnimation;
import com.skydoves.elasticviews.ElasticButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/olife/detail")
public class DetailActivity extends AppCompatActivity {

    @Autowired
    public String title;
    @Autowired
    public String time;
    @Autowired
    public String address;
    @Autowired
    public String state;
    @Autowired
    public String server;

    @BindView(R.id.server_call)
    ElasticButton callBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        init();
    }

    public void init(){
        System.out.println(title+"\t"+time+"\t"+address+"\t"+state+"\t"+server);
//        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
//        binding.setOrder(new Order(title,new Date(time),address,state,server));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(time);
            ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
            binding.setVariable(BR.order,new Order(UUID.randomUUID().toString(),title,date,address,state,server));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.server_call)
    public void toCall(View v){
        new ElasticAnimation(v).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                .setOnFinishListener(() -> {
                    System.out.println("已拨打了电话");
                }).doAction();
    }
}
