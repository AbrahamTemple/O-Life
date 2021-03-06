package com.example.myapplication.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cleveroad.loopbar.adapter.SimpleCategoriesAdapter;
import com.cleveroad.loopbar.widget.LoopBarView;
import com.cleveroad.loopbar.widget.OnItemClickListener;
import com.example.myapplication.BuildConfig;
import com.example.myapplication.data.model.HospitalResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.domain.Counter;
import com.example.myapplication.router.LoginCallbackImpl;
import com.example.myapplication.router.RoutePath;
import com.example.myapplication.service.CounterService;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.fragment.compose.AboutFragment;
import com.example.myapplication.view.fragment.compose.HomeFragment;
import com.example.myapplication.view.fragment.RecycleFragment;
import com.example.myapplication.view.fragment.ShimmeFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.view.fragment.compose.UserFragment;
import com.example.myapplication.view.layout.ShapeLoadingDialog;
import com.example.myapplication.view.model.LoopBarItemsFactory;
import com.yalantis.taurus.PullToRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;


@Route(path = "/olife/home")
public class HomeActivity extends AppCompatActivity implements Contract.View, OnItemClickListener {

    public static final int REFRESH_DELAY = 3000;

    @BindView(R.id.pull_to_refresh)
    PullToRefreshView prv;

    private SharedPreferencesUtils sharedPreferences,tokenShared,baseShared;
    private Presenter presenter;

    private ShapeLoadingDialog shapeLoadingDialog;

    private HomeFragment home;

    @BindView(R.id.endlessView)
    LoopBarView loopBarView;

    private SimpleCategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        HideUtil.init(this);
        init();
    }

    private void init() {
        initLoading();
        initFragment();
        initView();
        initRefresh();
        initRequest();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(Counter counter) {
        switch (counter.getTag()){
            case 404:
                if(counter.getProgress() == 0) {
                    shapeLoadingDialog.dismiss();
                    home.replaceFragment(new ShimmeFragment());
                    Toast.makeText(HomeActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case 401:
                if(counter.getProgress() == 0) {
                    shapeLoadingDialog.dismiss();
                    home.replaceFragment(new ShimmeFragment());
                    Toast.makeText(HomeActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case 406:
                if(counter.getProgress() == 0) {
                    shapeLoadingDialog.dismiss();
                    home.replaceFragment(new ShimmeFragment());
                    Toast.makeText(HomeActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case 200:
                if(counter.getProgress() == 0) {
                    shapeLoadingDialog.dismiss();
                    String dataString = sharedPreferences.getString("all_hospital");
                    HospitalResponse data = GsonUtils.fromJson(dataString, HospitalResponse.class);
                    home.replaceFragment(new RecycleFragment(data));
                    Toast.makeText(HomeActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void initFragment(){
        home = new HomeFragment(this);
        replaceFragment(home);
        home.replaceFragment(new ShimmeFragment());
    }

    private void initView(){
        categoriesAdapter = new SimpleCategoriesAdapter(LoopBarItemsFactory.getCategoryItems(this));
        loopBarView.setCategoriesAdapter(categoriesAdapter);
        loopBarView.addOnItemClickListener(this);
    }

    private void initLoading(){
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("?????????...");
        shapeLoadingDialog.show();
    }

    private void initRefresh(){
        prv.setOnRefreshListener(() -> prv.postDelayed(() -> prv.setRefreshing(false), REFRESH_DELAY));
    }

    private void initRequest(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        sharedPreferences = SharedPreferencesUtils.init(HomeActivity.this);
        tokenShared = SharedPreferencesUtils.init(this,"oauth");
        sharedPreferences.clear();
        baseShared = SharedPreferencesUtils.init(this,"base");
        presenter.getAllHospital(baseShared.getString("render")); //BuildConfig.ACCESS_TOKEN
    }

    @Override
    protected void onResume() {
        super.onResume();
        home.getBanner().startTurning(); //??????????????????
    }

    @Override
    protected void onPause() {
        super.onPause();
        home.getBanner().stopTurning(); //??????????????????
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void getDataSuccess(ResponseBody body) {
        try {
            String result = body.string();
            Log.e("????????????", "????????????: " + result);
            HospitalResponse data = GsonUtils.fromJson(result, HospitalResponse.class);
            String dataString = GsonUtils.toJson(data);
            CounterService.startDownload(this,1, 200);
            sharedPreferences.putString("all_hospital",dataString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        System.out.println(throwable.getMessage());
        if (throwable.getMessage().length()<80) {
            String[] httpStatus = throwable.getMessage().split("[ ]");
            CounterService.startDownload(this, 1, Integer.valueOf(httpStatus[1]));
        } else {
            CounterService.startDownload(this, 1, 404);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemClicked(int position) {
        System.out.println(position);
        switch (position){
            case 0:
                String dataString = sharedPreferences.getString("all_hospital");
                home = new HomeFragment(this);
                replaceFragment(home);
                if(!dataString.isEmpty()){
                    HospitalResponse data = GsonUtils.fromJson(dataString, HospitalResponse.class);
                    home.replaceFragment(new RecycleFragment(data));
                } else {
                    if(tokenShared.getString("token")!=null){
                        presenter.getAllHospital(tokenShared.getString("token"));
                    } home.replaceFragment(new ShimmeFragment());
                }
                break;
            case 1:
                ARouter.getInstance().build(RoutePath.CALL.toString())
                        .withString("tag", UUID.randomUUID().toString()).navigation(this,new LoginCallbackImpl());
                break;
            case 2:
                ARouter.getInstance().build(RoutePath.ESCORT.toString()).navigation(this,new LoginCallbackImpl());
                break;
            case 3:
                ARouter.getInstance().build(RoutePath.ORDER.toString()).navigation(this,new LoginCallbackImpl());
                break;
            case 4:
                replaceFragment(new UserFragment(this,tokenShared.getString("username")));
                break;
            case 5:
                replaceFragment(new AboutFragment());
                break;
            default: replaceFragment(new ShimmeFragment());
        }
    }
}
