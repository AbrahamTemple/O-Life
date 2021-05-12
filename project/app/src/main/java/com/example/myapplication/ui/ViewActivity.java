package com.example.myapplication.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.myapplication.R;
import com.example.myapplication.domain.Counter;
import com.example.myapplication.event.MyAmqp;
import com.google.android.material.button.MaterialButton;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.yalantis.taurus.PullToRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/ui/activity")
public class ViewActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REFRESH_DELAY = 3000;

    private ConnectionFactory factory;
    private String userName = "admin" ;
    private String passWord = "admin" ;
    private String hostName = "172.20.10.12" ;
    private String virtualHost = "/";
    private int portNum = 5672 ;
    private String exchangeName = "android_exchange" ;
    private String queueName = "android_queue";
    private String rountingKey = "news";

    @BindView(R.id.pull_to_refresh)
    PullToRefreshView prv;

    @BindView(R.id.call_btn)
    MaterialButton call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
        setupConnectionFactory();
        new Thread(() -> basicConsume()).start();
    }

    public void init(){
        prv.setOnRefreshListener(() -> prv.postDelayed(() -> prv.setRefreshing(false), REFRESH_DELAY));
        call.setOnClickListener(this);
    }

    private void setupConnectionFactory() {
        factory = new ConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(passWord);
        factory.setHost(hostName);
        factory.setPort(portNum);
        factory.setVirtualHost(virtualHost);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void OnEventProgress(String msg){
        System.out.println(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_btn:
                getPermission();
                break;
             default:
        }
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

    public void getPermission() {
        if (ContextCompat.checkSelfPermission(ViewActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ViewActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            call();
        }
    }

    /* 只有同意打开相关权限才可以开启本程序 */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    call();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void basicConsume(){

        try {
            Connection connection = factory.newConnection() ;
            final Channel channel = connection.createChannel() ;
            channel.exchangeDeclare(exchangeName , "direct" , true) ;
            channel.queueBind(queueName , exchangeName , rountingKey) ;
            /**
             * @param queue 队列名
             * @param autoAck true:自动应答, false:手动应答
             * @param callback 构造新实例并记录其与传入通道的关联
             */
            channel.basicConsume(queueName , false ,  new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);

                    String msg = new String(body) ;
                    channel.basicAck(envelope.getDeliveryTag() , false); //应答
                    EventBus.getDefault().post(msg); //发送到消息总线
                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
