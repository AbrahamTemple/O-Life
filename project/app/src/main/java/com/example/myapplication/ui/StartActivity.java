package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.data.model.LoginResponse;
import com.example.myapplication.data.model.UserResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.event.RxTimer;
import com.example.myapplication.router.RoutePath;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.schibsted.spain.parallaxlayerlayout.AnimatedTranslationUpdater;
import com.schibsted.spain.parallaxlayerlayout.ParallaxLayerLayout;
import com.schibsted.spain.parallaxlayerlayout.SensorTranslationUpdater;

import java.io.IOException;

import okhttp3.ResponseBody;

@Route(path = "/olife/start")
public class StartActivity extends AppCompatActivity implements Contract.View{

    private ParallaxLayerLayout parallaxLayout;
    private SensorTranslationUpdater translationUpdater;

    private Presenter presenter;
    private SharedPreferencesUtils baseShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        parallaxLayout = (ParallaxLayerLayout) findViewById(R.id.parallax);
        RadioGroup updaterGroup = (RadioGroup) findViewById(R.id.updater_group);

        translationUpdater = new SensorTranslationUpdater(this);

        updaterGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.updater_sensor_button) {
                parallaxLayout.setTranslationUpdater(translationUpdater);
            } else if (checkedId == R.id.updater_auto_button) {
                System.out.println("auto");
                parallaxLayout.setTranslationUpdater(new AnimatedTranslationUpdater(0.5f));
            }
        });

        updaterGroup.check(R.id.updater_auto_button);

        // Resets orientation when clicked
        parallaxLayout.setOnClickListener(v -> translationUpdater.reset());

        RxTimer rxTimer = new RxTimer();
        rxTimer.timer(4000, number -> {
            ARouter.getInstance().build(RoutePath.HOME.toString()).navigation();
            finish();
        });

        initRequest();
    }

    private void initRequest(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        baseShared = SharedPreferencesUtils.init(this,"base");
        presenter.loginAuth("vue","vue","cli","sec");
    }

    @Override
    protected void onResume() {
        super.onResume();
        translationUpdater.registerSensorManager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        translationUpdater.unregisterSensorManager();
    }

    @Override
    public void getDataSuccess(ResponseBody body) {
        try {
            String result = body.string();
            Log.e("网络请求", "响应结果: " + result);
            if (result.length()<200) {
                LoginResponse data = GsonUtils.fromJson(result, LoginResponse.class);
                baseShared.putString("render", data.getData().getAccessToken());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {

    }
}
