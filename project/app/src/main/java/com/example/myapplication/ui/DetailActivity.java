package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.domain.Order;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.skydoves.elasticviews.ElasticAnimation;
import com.skydoves.elasticviews.ElasticButton;

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
    public String state;
    @Autowired
    public String info;
    @Autowired
    public String address;
    @Autowired
    public String server;

    @BindView(R.id.server_call)
    ElasticButton serverBtn;

    @Autowired
    public int action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        init();
    }


    public void init(){
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        if (action==0) {
            serverBtn.setText("联系他");
            binding.setVariable(BR.order, new Order(UUID.randomUUID().toString(),title,time,"+86 "+info,address,state,server));
        } else {
            serverBtn.setText("私聊他");
            binding.setVariable(BR.order, new Order(UUID.randomUUID().toString(),title,time,"诊科:"+info,address,state,server));
        }
    }

    @OnClick(R.id.server_call)
    public void toCall(View v){
        if (action==0) {
            new ElasticAnimation(v).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                    .setOnFinishListener(() -> {
                        Toast.makeText(this,"已拨打了电话",Toast.LENGTH_SHORT);
                    }).doAction();
        } else {
            new ElasticAnimation(v).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                    .setOnFinishListener(() -> {
                        Toast.makeText(this,"已经连接了信道",Toast.LENGTH_SHORT);
                    }).doAction();
        }
    }
}
