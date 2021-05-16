package com.example.myapplication.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapplication.service.AmqpService;
import com.example.myapplication.service.WsService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/call/activity")
public class EmergencyActivity extends AppCompatActivity{

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.message_text)
    EditText msg_text;

    @BindView(R.id.linear_container)
    LinearLayout task1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        init();
        WsService.startConnection(this,"6");
    }

    public void init(){
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(String msg){
        if(task1.getChildCount()>6){
            task1.removeAllViews();
        }
        TextView textView = new TextView(this);
        textView.setTag(UUID.randomUUID());
        textView.setText(msg);
        task1.addView(textView);
    }

    @OnClick(R.id.publish_btn)
    public void getPublish(){
        String msg = msg_text.getText().toString();
        Map<String, Object> map = new HashMap<>();
        List<String> persons = new ArrayList<>();
        persons.add("6");
        persons.add("1");
        map.put("persons",persons);
        map.put("msg",msg);
        WsService.startSend(this,map);
    }

    @OnClick(R.id.restart_btn)
    public void getRestart(){
        WsService.startReConnection(this);
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

    @OnClick(R.id.call_btn)
    public void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            call();
            AmqpService.startPublish(this,"599931351@qq.com");
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
        WsService.startClose(this);
        super.onDestroy();
    }
}
