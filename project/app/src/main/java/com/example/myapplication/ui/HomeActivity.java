package com.example.myapplication.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.myapplication.data.model.HospitalResponse;
import com.example.myapplication.data.model.PhoneResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.adapter.RecommendAdapter;
import com.example.myapplication.view.items.LocalImageHolderView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.view.items.HomeRecommend;
import com.example.myapplication.view.layout.MyPowerMenu;
import com.example.myapplication.view.layout.SpruceRecyclerView;
import com.yalantis.taurus.PullToRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;


@Route(path = "/olife/home")
public class HomeActivity extends AppCompatActivity implements OnItemClickListener, Contract.View{

    public static final int REFRESH_DELAY = 3000;

    @BindView(R.id.convenientBanner)
    ConvenientBanner banner;

    @BindView(R.id.home_recommend)
    RecyclerView recommend;

    @BindView(R.id.pull_to_refresh)
    PullToRefreshView prv;

    private SharedPreferencesUtils sharedPreferences;
    private Presenter presenter;

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
        initNet();
    }

    private void initRefresh(){
        prv.setOnRefreshListener(() -> prv.postDelayed(() -> prv.setRefreshing(false), REFRESH_DELAY));
    }

    private void initNav(){
        myPowerMenu = new MyPowerMenu(this,this);
        myPowerMenu.init();
    }

    private void initNet(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        sharedPreferences = SharedPreferencesUtils.init(HomeActivity.this);
        sharedPreferences.clear();
        presenter.getAllHospital("e43aaac3-46f0-41ad-bfdf-51e769899c97");
    }

    private void initRecycler(HospitalResponse hospitalResponse){
        List<HomeRecommend> lists = new ArrayList<>();
        hospitalResponse.getData().forEach(d->{
            HomeRecommend r = new HomeRecommend(R.drawable.ramain_register,d.getName(),d.getAddress(),"联系电话："+d.getPhone());
            lists.add(r);
        });
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

    @Override
    public void getDataSuccess(ResponseBody body) {
        Toast.makeText(HomeActivity.this, "页面渲染成功", Toast.LENGTH_SHORT).show();
        try {
            String result = body.string();
            Log.e("网络请求", "响应结果: " + result);
            HospitalResponse data = GsonUtils.fromJson(result, HospitalResponse.class);
            initRecycler(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        Toast.makeText(HomeActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
