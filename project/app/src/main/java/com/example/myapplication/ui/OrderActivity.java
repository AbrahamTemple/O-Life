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
import com.example.myapplication.data.model.EscortResponse;
import com.example.myapplication.data.model.RegisterResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.domain.Reserver;
import com.example.myapplication.event.RxTimer;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.fragment.OrderListFragment;
import com.example.myapplication.view.fragment.ShimmeFragment;
import com.example.myapplication.view.layout.ShapeLoadingDialog;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

@Route(path = "/olife/order")
public class OrderActivity extends AppCompatActivity implements Contract.View, ViewPager.OnPageChangeListener, NavigationTabStrip.OnTabStripSelectedIndexListener {

    @BindView(R.id.nts_top)
    NavigationTabStrip navigationTabStrip;

    private SharedPreferencesUtils sharedPreferences,tokenShared;
    private Presenter presenter;

    private ShapeLoadingDialog shapeLoadingDialog;

    private static int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initLoading();
        initRequest();
        initTab();
        initFrag();
    }

    private void initLoading(){
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.show();
    }

    private void initRequest(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        sharedPreferences = SharedPreferencesUtils.init(OrderActivity.this);
        tokenShared = SharedPreferencesUtils.init(this,"oauth");
    }

    private void initFrag(){
        replaceFragment(new ShimmeFragment());
        new RxTimer().timer(500,number -> {
            shapeLoadingDialog.dismiss();
            presenter.RedisEscort(tokenShared.getLong("id"),tokenShared.getString("token"));
        });
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
        Log.d("选项卡位置", String.valueOf(state));
    }

    @Override
    public void onStartTabSelected(String title, int index) {
    }

    @Override
    public void onEndTabSelected(String title, int index) {
        flag = index;
        if (flag == 0) {
            presenter.RedisEscort(tokenShared.getLong("id"),tokenShared.getString("token"));
        } else {
            presenter.RegisterGet(tokenShared.getLong("id"),tokenShared.getString("token"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void getDataSuccess(ResponseBody body) {
        try {
            String result = body.string();
            Log.e("网络请求", "响应结果: " + result);
            List<Reserver> item = new ArrayList<>();
            if (flag == 0){
                EscortResponse data = GsonUtils.fromJson(result,EscortResponse.class);
                if (data.getData().size()>0) {
                    data.getData().forEach(d -> item.add(new Reserver("预约陪诊",d.getAddress(),d.getName(),String.valueOf(d.getPhone()), d.getTiming(), d.getState(),flag)));
                    replaceFragment(new OrderListFragment(item));
                    return;
                }
            } else {
                RegisterResponse data = GsonUtils.fromJson(result,RegisterResponse.class);
                if (data.getData().size()>0) {
                    data.getData().forEach(d -> item.add(new Reserver("挂号订单",d.getAddress(),d.getName(),d.getSort(), d.getTime(), d.getState(),flag)));
                    replaceFragment(new OrderListFragment(item));
                    return;
                }
            }
            replaceFragment(new ShimmeFragment());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        shapeLoadingDialog.dismiss();
        System.out.println(throwable.getMessage());
    }
}
