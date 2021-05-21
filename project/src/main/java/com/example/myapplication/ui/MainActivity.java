package com.example.myapplication.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.app.MyApplication;
import com.example.myapplication.data.model.BannerResponse;
import com.example.myapplication.data.network.config.NetWorkInit;
import com.example.myapplication.domain.Counter;
import com.example.myapplication.service.MyIntentService;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.layout.MyPowerMenu;
import com.royrodriguez.transitionbutton.TransitionButton;
import com.skyfishjy.library.RippleBackground;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn1)
    Button btn1;

    @BindView(R.id.btn2)
    TransitionButton btn2;

    @BindView(R.id.btn3)
    Button btn3;

    @BindView(R.id.ser_content)
    RippleBackground rbg1;

    @BindView(R.id.linear_container)
    LinearLayout task1;

    @BindView(R.id.title_main)
    TextView tag;

    private Handler handler;

    private int textTag;

    private MyPowerMenu myPowerMenu;

    private SharedPreferencesUtils sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    public void init() {
        textTag = 0;
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        rbg1.setOnClickListener(this);
        NetWorkInit.getInstance().init();
        myPowerMenu = new MyPowerMenu(this,this);
        myPowerMenu.init();
        sharedPreferences = SharedPreferencesUtils.init(MainActivity.this);
        sharedPreferences.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                ARouter.getInstance().build("/test/activity")
                        .withInt("codeName", 16)
                        .withString("name", "Abraham")
                        .withString("url",sharedPreferences.getString("6"))
                        .navigation();
                Log.d("MainActivity","已触发了路由path跳转");
                break;
            case R.id.btn2:
                // 开启过度按钮的进场动画
                btn2.startAnimation();

                // 在动画执行过程中要做一些什么
                handler = new Handler();
                handler.postDelayed(() -> {

                    boolean isSuccessful = true;

                    if (isSuccessful) {
                        btn2.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, () -> {
                            //Uri uri = getIntent().getData();
                            Uri uri = Uri.parse("/test/activity");
                            ARouter.getInstance().build(uri)
                                    .withInt("codeName", 23)
                                    .withString("name", "Yan")
                                    .withString("url",sharedPreferences.getString("20"))
                                    .navigation();
                            Log.d("MainActivity","已触发了路由uri跳转");
                        });
                    } else {
                        btn2.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                    }
                }, 2000);
                break;
            case R.id.ser_content:
                rbg1.startRippleAnimation();
                int num = new Random().nextInt(3)%(3) + 1;//生成1-3之间的随机数
                TextView textView = new TextView(this);
                textView.setTag(textTag);
                textView.setText(num * 1000 + "ms");
                task1.addView(textView);
                MyIntentService.startDownload(this,num,textTag);
                textTag++;
                break;
            case R.id.btn3:
                ARouter.getInstance().build("/ui/activity")
                        .withString("tag", UUID.randomUUID().toString())
                        .navigation();
                break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(Counter counter){
        Call<ResponseBody> call = NetWorkInit.getRequest().getBanner();
        TextView textView = (TextView) task1.findViewWithTag(counter.getTag());
        textView.setText(counter.getProgress() + "ms");
        if(counter.getProgress() == 0){
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        try {
                            String result = response.body().string();
                            Log.e("网络请求", "响应结果: " + result);
                            BannerResponse data = GsonUtils.fromJson(result, BannerResponse.class);
                            data.getData().forEach(d ->{
                                sharedPreferences.putString(d.getId()+"",d.getUrl());
                            });
                            Toast.makeText(MainActivity.this, "网址已被保存", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        MyApplication.getInstance().AppExit();
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
