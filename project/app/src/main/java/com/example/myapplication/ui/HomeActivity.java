package com.example.myapplication.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.myapplication.view.adapter.RecommendAdapter;
import com.example.myapplication.view.items.LocalImageHolderView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.view.items.HomeRecommend;
import com.example.myapplication.view.layout.MyPowerMenu;
import com.example.myapplication.view.layout.SpruceRecyclerView;
import com.yalantis.taurus.PullToRefreshView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = "/olife/home")
public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    public static final int REFRESH_DELAY = 3000;

    @BindView(R.id.convenientBanner)
    ConvenientBanner banner;

    @BindView(R.id.home_recommend)
    RecyclerView recommend;

    @BindView(R.id.pull_to_refresh)
    PullToRefreshView prv;



    private MyPowerMenu myPowerMenu;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initRefresh();
        initNav();
        initBanner();
        initRecycler();
    }

    private void initRefresh(){
        prv.setOnRefreshListener(() -> prv.postDelayed(() -> prv.setRefreshing(false), REFRESH_DELAY));
    }

    private void initNav(){
        myPowerMenu = new MyPowerMenu(this,this);
        myPowerMenu.init();
    }

    private void initRecycler(){
        List<HomeRecommend> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HomeRecommend r = new HomeRecommend(R.drawable.ramain_register,"春江医院","春江医院是治理男科不育不孕的国家一级医院","治愈患者：6700万");
            lists.add(r);
        }
        RecommendAdapter recommendAdapter = new RecommendAdapter(lists);
        new SpruceRecyclerView(this, recommend, recommendAdapter, false).init();
    }

    private void initBanner(){
        loadTestDatas();
        //本地图片例子
        banner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public Holder createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_localimage;
                    }
                }, localImages)
                .setPageIndicator(new int[]{R.drawable.point_unfocused_small, R.drawable.point_focused_big})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this);
    }

    @OnClick(R.id.callout_index)
    public void toCallActivity(){
        ARouter.getInstance().build("/olife/call")
                .withString("tag", UUID.randomUUID().toString())
                .navigation();
    }

    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 4; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startTurning(); //开始自动翻页
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopTurning();// 停止自动翻页
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"点击了第"+position+"个", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onHamburger(View view) {
        myPowerMenu.onHamburger(view);
    }

    public void onProfile(View view) {
        myPowerMenu.onProfile(view);
    }

    public void onIcon(View view){
        myPowerMenu.onIcon(view);
    }
}
