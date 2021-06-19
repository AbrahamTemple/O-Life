package com.example.myapplication.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import com.adam.gpsstatus.GpsStatusProxy;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.data.model.PhoneResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.domain.Phone;
import com.example.myapplication.event.MyPhoneStateListener;
import com.example.myapplication.service.AmqpService;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.mingle.widget.ShapeLoadingDialog;
import com.skyfishjy.library.RippleBackground;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;


@Route(path = "/olife/call")
public class CallActivity extends AppCompatActivity implements Contract.View {

    private String TAG = "CallActivity";
    private SharedPreferencesUtils sharedPreferences,tokenShared;
    private Presenter presenter;

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

    private ShapeLoadingDialog shapeLoadingDialog;

    private GpsStatusProxy proxy;
    private LocationManager locationManager;

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

    public void init() {
        myPhoneStateListener = new MyPhoneStateListener();
        manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        initGetNetStatus();
        initRequest();
    }

    public void initAmqp() {
        AmqpService.setQueue(tag);
        AmqpService.setRountingKey(tag);
        AmqpService.setIsAutoDete(true);
        AmqpService.setIsExclusive(true);
        AmqpService.startListener(this);
    }

    private void initRequest(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        sharedPreferences = SharedPreferencesUtils.init(CallActivity.this);
        sharedPreferences.clear();
        tokenShared = SharedPreferencesUtils.init(this,"oauth");
    }

    public void initGetNetStatus() {
        proxy = GpsStatusProxy.getInstance(getApplicationContext());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            List<String> permissionsNeeded = new ArrayList<String>();
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), 1);
            return;
        }
        proxy.register();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(Phone phone) {
        if (task1.getChildCount() > 16) {
            task1.removeAllViewsInLayout();
        }
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        TextView textView = new TextView(this);
        textView.setTag(phone.getSender());
        Random rand = new Random();
        textView.setTextColor(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        textView.setText(ft.format(phone.getTime()) + ' ' + phone.getPhoneNum());
        textView.setOnClickListener(v -> Toast.makeText(this, "被服务者：" + textView.getTag().toString(), Toast.LENGTH_SHORT).show());
        task1.addView(textView);
    }

    @OnClick(R.id.call_btn)
    public void getCall() {
        state.startRippleAnimation();
        startShakeByViewAnim(logo, 0.9f, 1.1f, 10f, 1000);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            presenter.getPone(tokenShared.getString("token"));
            manager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    //网络状况检测
    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            proxy.notifyLocation(location);
//            Log.d("网络状态已刷新",location.getProvider());
//            Toast.makeText(CallActivity.this, "网络状态已刷新 "+location.getProvider(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
//            Log.d("网络状态发生改变",provider);
//            Toast.makeText(CallActivity.this, "网络状态正在改变 HTTP "+status, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("网络状态检测已启用",provider);
            Toast.makeText(CallActivity.this, "网络状态检测已启用 "+provider, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("网络状态检测已关闭",provider);
            Toast.makeText(CallActivity.this, "网络状态检测已关闭 "+provider, Toast.LENGTH_SHORT).show();
        }
    };

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
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } catch (SecurityException e) {
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
//                presenter.getPone(BuildConfig.ACCESS_TOKEN);
                Toast.makeText(this, "通话权限已打开", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    List<String> permissionsNeeded = new ArrayList<String>();
                    permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
                    ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), 1);
                    return;
                }
                proxy.register();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        proxy.unRegister();
        super.onDestroy();
    }

    @Override
    public void getDataSuccess(ResponseBody body) {
        Toast.makeText(CallActivity.this, "成功匹配到电话", Toast.LENGTH_SHORT).show();
        try {
            String result = body.string();
            Log.e("网络请求", "响应结果: " + result);
            PhoneResponse data = GsonUtils.fromJson(result, PhoneResponse.class);
            call(data.getData());
            Phone phone = new Phone(tokenShared.getString("username"),data.getData(),new Date());
            EventBus.getDefault().post(phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        Toast.makeText(CallActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
