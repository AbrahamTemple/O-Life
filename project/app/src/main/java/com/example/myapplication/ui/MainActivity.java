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

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.app.MyApplication;
import com.example.myapplication.data.model.PhoneResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.domain.Counter;
import com.example.myapplication.service.CounterService;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Contract.View {

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

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
        init();
    }

    public void init() {
        textTag = 0;
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        rbg1.setOnClickListener(this);
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
                Log.d("MainActivity","??????????????????path??????");
                break;
            case R.id.btn2:
                // ?????????????????????????????????
                btn2.startAnimation();

                // ??????????????????????????????????????????
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
                            Log.d("MainActivity","??????????????????uri??????");
                        });
                    } else {
                        btn2.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                    }
                }, 2000);
                break;
            case R.id.ser_content:
                rbg1.startRippleAnimation();
                int num = new Random().nextInt(3)%(3) + 1;//??????1-3??????????????????
                TextView textView = new TextView(this);
                textView.setTag(textTag);
                textView.setText(num * 1000 + "ms");
                task1.addView(textView);
//                MyIntentService.startDownload(this,num,textTag);
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

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
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

    @Override
    public void getDataSuccess(ResponseBody body) {
        Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
        try {
            String result = body.string();
            Log.e("????????????", "????????????: " + result);
            PhoneResponse data = GsonUtils.fromJson(result, PhoneResponse.class);
            System.out.println(data.getData());
            Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
