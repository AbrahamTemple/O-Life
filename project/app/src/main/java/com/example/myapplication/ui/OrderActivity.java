package com.example.myapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapplication.R;
import com.example.myapplication.data.model.ReserverResponse;
import com.example.myapplication.view.fragment.OrderListFragment;
import com.example.myapplication.view.fragment.ShimmeFragment;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/olife/order")
public class OrderActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, NavigationTabStrip.OnTabStripSelectedIndexListener {

    @BindView(R.id.nts_top)
    NavigationTabStrip navigationTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initTab();
        replaceFragment(new ShimmeFragment());
    }

    public void initTab(){
        navigationTabStrip.setTabIndex(0, true);
        navigationTabStrip.setTitleSize(55);
        navigationTabStrip.setStripColor(Color.RED);
        navigationTabStrip.setStripWeight(12);
        navigationTabStrip.setStripFactor(4);
        navigationTabStrip.setStripType(NavigationTabStrip.StripType.LINE);
        navigationTabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        navigationTabStrip.setCornersRadius(6);
        navigationTabStrip.setAnimationDuration(300);
        navigationTabStrip.setInactiveColor(Color.BLACK);
        navigationTabStrip.setActiveColor(Color.BLUE);
        navigationTabStrip.setOnPageChangeListener(this);
        navigationTabStrip.setOnTabStripSelectedIndexListener(this);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_order, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("onPageScrolled", String.valueOf(position));
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("onPageSelected", String.valueOf(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d("onPageScrollStateChanged", String.valueOf(state));
    }

    @Override
    public void onStartTabSelected(String title, int index) {
    }

    @Override
    public void onEndTabSelected(String title, int index) {
        Log.d("onEndTabSelected", String.valueOf(index));
        if (index == 0) {
            List<ReserverResponse.Reserver> item1 = new ArrayList<>();
            ReserverResponse.Reserver c = new ReserverResponse.Reserver(1, "预约陪诊", new Date(), "广东省广州市海珠区海心沙", "等待接单", "吴佳杰");
            ReserverResponse.Reserver d = new ReserverResponse.Reserver(2, "预约陪诊", new Date(), "上海市外滩", "已接单", "老板");
            item1.add(c);
            item1.add(d);
            ReserverResponse data1 = new ReserverResponse(item1, 200, "OK");
            replaceFragment(new OrderListFragment(data1));
        } else {
            List<ReserverResponse.Reserver> item0 = new ArrayList<>();
            ReserverResponse.Reserver a = new ReserverResponse.Reserver(1, "预约挂号", new Date(), "美国加州弗里亚", "正在排队", "农承彬");
            ReserverResponse.Reserver b = new ReserverResponse.Reserver(2, "预约挂号", new Date(), "加拿大蒙特利尔", "已完成", "儿子");
            item0.add(a);
            item0.add(b);
            ReserverResponse data = new ReserverResponse(item0, 200, "OK");
            replaceFragment(new OrderListFragment(data));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
