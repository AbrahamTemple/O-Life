package com.example.myapplication.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.domain.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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
            binding.setVariable(BR.order,new Order(title,date,address,state,server));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
