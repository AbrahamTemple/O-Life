package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.domain.Counter;
import com.example.myapplication.service.MyIntentService;
import com.example.myapplication.ui.view.MyPowerMenu;
import com.example.myapplication.util.PowerMenuUtils;
import com.royrodriguez.transitionbutton.TransitionButton;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.skyfishjy.library.RippleBackground;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn1)
    Button btn1;

    @BindView(R.id.btn2)
    TransitionButton btn2;

    @BindView(R.id.ser_content)
    RippleBackground rbg1;

    @BindView(R.id.linear_container)
    LinearLayout task1;

    private int textTag;

    private MyPowerMenu myPowerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
        myPowerMenu.init();
    }

    public void init() {
        textTag = 0;
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        rbg1.setOnClickListener(this);
        myPowerMenu = new MyPowerMenu(this,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                ARouter.getInstance().build("/test/activity")
                        .withInt("codeName", 16)
                        .withString("name", "Abraham")
                        .navigation();
                Log.d("MainActivity","已触发了路由path跳转");
                break;
            case R.id.btn2:
                // 开启过度按钮的进场动画
                btn2.startAnimation();

                // 在动画执行过程中要做一些什么
                final Handler handler = new Handler();
                handler.postDelayed(() -> {

                    boolean isSuccessful = true;

                    if (isSuccessful) {
                        btn2.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, () -> {
                            //Uri uri = getIntent().getData();
                            Uri uri = Uri.parse("/test/activity");
                            ARouter.getInstance().build(uri)
                                    .withInt("codeName", 23)
                                    .withString("name", "Yan")
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
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(Counter counter){
        TextView textView = (TextView)task1.findViewWithTag(counter.getTag());
        textView.setText(counter.getProgress() + "ms");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onHamburger(View view) {
        myPowerMenu.onHamburger(view);
    }

    public void onProfile(View view) {
        myPowerMenu.onProfile(view);
    }
}
