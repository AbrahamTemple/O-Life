package com.example.myapplication.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.event.MyPhoneStateListener;
import com.example.myapplication.service.AmqpService;
import com.skyfishjy.library.RippleBackground;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = "/olife/call")
public class CallActivity extends AppCompatActivity {

    private String TAG = "CallActivity";
    private String PhoneNumber = "13106789112";

    @Autowired
    public String tag;

    @BindView(R.id.ser_content)
    RippleBackground state;

    @BindView(R.id.centerImage)
    ImageView logo;

    @BindView(R.id.msg_container)
    LinearLayout task1;

    private MyPhoneStateListener myPhoneStateListener;
    private TelephonyManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
        initAmqp();
    }

    public void init(){
        myPhoneStateListener = new MyPhoneStateListener();
        manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }

    public void initAmqp(){
        AmqpService.setQueue(tag);
        AmqpService.setRountingKey(tag);
        AmqpService.setIsAutoDete(true);
        AmqpService.setIsExclusive(true);
        AmqpService.startListener(this);
    }


    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(String msg){
        if (task1.getChildCount()>16){
            task1.removeAllViewsInLayout();
        }
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
        TextView textView = new TextView(this);
        textView.setTag("帅胤");
        Random rand = new Random();
        textView.setTextColor(Color.rgb(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));
        textView.setText(ft.format(now) + ' ' + msg);
        textView.setOnClickListener(v -> Toast.makeText(this, "服务者："+textView.getTag().toString(), Toast.LENGTH_SHORT).show());
        task1.addView(textView);
    }

    @OnClick(R.id.call_btn)
    public void getCall(){
        state.startRippleAnimation();
        startShakeByViewAnim(logo, 0.9f, 1.1f, 10f, 1000);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            call(PhoneNumber);
            AmqpService.setRountingKey(tag);
            AmqpService.startPublish(this,PhoneNumber);
            manager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    // 抖动动画
    private void startShakeByViewAnim(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        if (view == null) {
            return;
        }

        //由小变大
        Animation scaleAnim = new ScaleAnimation(scaleSmall, scaleLarge, scaleSmall, scaleLarge);
        //从左向右
        Animation rotateAnim = new RotateAnimation(-shakeDegrees, shakeDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnim.setDuration(duration);
        rotateAnim.setDuration(duration / 10);
        rotateAnim.setRepeatMode(Animation.REVERSE);
        rotateAnim.setRepeatCount(10);

        AnimationSet smallAnimationSet = new AnimationSet(false);
        smallAnimationSet.addAnimation(scaleAnim);
        smallAnimationSet.addAnimation(rotateAnim);

        view.startAnimation(smallAnimationSet);
    }

    public void call(String phoneNumber) {
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+phoneNumber));
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    /* 只有同意打开相关权限才可以开启本程序 */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "必须同意打开权限才能使用一键拨号", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                call(PhoneNumber);
            } else {
                Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
