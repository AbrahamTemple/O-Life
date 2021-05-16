package com.example.myapplication.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.myapplication.app.MyApplication;
import com.example.myapplication.service.AmqpService;
import com.yalantis.taurus.PullToRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/ui/activity")
public class ViewActivity extends AppCompatActivity{

    public static final int REFRESH_DELAY = 3000;

    @Autowired
    public String tag;

    @BindView(R.id.pull_to_refresh)
    PullToRefreshView prv;

    @BindView(R.id.message_text)
    EditText msg_text;

    @BindView(R.id.linear_container)
    LinearLayout task1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        MyApplication.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        init();
    }

    public void init(){
        ARouter.getInstance().inject(this);
        prv.setOnRefreshListener(() -> prv.postDelayed(() -> prv.setRefreshing(false), REFRESH_DELAY));
        AmqpService.setQueue(tag);
        AmqpService.setRountingKey(tag);
        AmqpService.setIsAutoDete(true);
        AmqpService.setIsExclusive(true);
        AmqpService.startListener(this);
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(String msg){
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
        TextView textView = new TextView(this);
        textView.setTag(UUID.randomUUID());
        textView.setText(ft.format(now) + ' ' + msg);
        textView.setOnClickListener(v -> Log.d("得到路由key：",v.getTag().toString()));
        task1.addView(textView);
    }

    public void call() {
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:13106789112"));
            startActivity(intent);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @OnClick({R.id.shine_btn})
    public void getRouter(){
        ARouter.getInstance().build("/call/activity").navigation();
    }

    @OnClick(R.id.publish_btn)
    public void getPublish(){
        String msg = msg_text.getText().toString();
        AmqpService.setRountingKey("news");
        AmqpService.startPublish(this,msg);
    }

    @OnClick(R.id.call_btn)
    public void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            call();
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
                call();
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
        MyApplication.getInstance().finishActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication.getInstance().finishActivity(this);
    }
}
