package com.example.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class AmqpService extends IntentService {

    private static final String ACTION_LISTEN = "com.example.myapplication.service.action.LISTEN";
    private static final String ACTION_PUBLIC = "com.example.myapplication.service.action.PUBLIC";

    private static final String EXTRA_MESSAGE = "com.example.myapplication.service.extra.MESSAGE";

    private static ConnectionFactory factory;
    private static final String host = "172.20.10.12" ; //一定不可为localhost
    private static final int port = 5672 ;
    private static final String username = "admin" ;
    private static final String password = "admin" ;
    private static final String virtualHost = "/";
    private static final String exchange = "android_exchange" ;
    private static String queue = "android_queue";
    private static String rountingKey = "news";
    private static boolean isAutoDete = false; //结束进程后自动删除队列
    private static boolean isExclusive = false; //排斥其它队列，不允许其它队列访问
    private static Map<String,Object> args = null; //附带参数

    public AmqpService() {
        super("AmqpService");
    }

    /**
     * 开始监听
     * @param context 上下文
     */
    public static void startListener(Context context) {
        Intent intent = new Intent(context, AmqpService.class);
        intent.setAction(ACTION_LISTEN);
        setupConnectionFactory();
        context.startService(intent);
    }

    /**
     *  开始发布
     * @param context 上下文
     * @param msg 消息
     */
    public static void startPublish(Context context, String msg) {
        Intent intent = new Intent(context, AmqpService.class);
        intent.setAction(ACTION_PUBLIC);
        intent.putExtra(EXTRA_MESSAGE, msg);
        setupConnectionFactory();
        context.startService(intent);
    }

    /**
     * 构建连接
     */
    private static void setupConnectionFactory() {
        factory = new ConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
    }

    /**
     * 异步线程：监听消息或发布消息
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            switch (Objects.requireNonNull(intent.getAction())){
                case ACTION_LISTEN: basicConsume(); break;
                case ACTION_PUBLIC:
                    String message = intent.getStringExtra(EXTRA_MESSAGE);
                    assert message != null;
                    basicPublish(message); break;
                default: Log.e("AmqpService","ACTION NOT FOUND");
            }
        }
    }

    /**
     *  发布消息
     * @param msg 消息
     */
    private void basicPublish(String msg){
        try {
            Connection connection = factory.newConnection() ;
            Channel channel = connection.createChannel() ;
            channel.basicPublish(exchange , rountingKey  ,null , msg.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     *  监听消息
     */
    private void basicConsume(){
        try {
            Connection connection = factory.newConnection() ;
            final Channel channel = connection.createChannel() ;
            channel.exchangeDeclare(exchange, "direct" , true) ;
            channel.queueDeclare(queue,true,isExclusive,isAutoDete,args);
            channel.queueBind(queue, exchange, rountingKey) ;
            /**
             * @param queue 队列名
             * @param autoAck true:自动应答, false:手动应答
             * @param callback 构造新实例并记录其与传入通道的关联
             */
            channel.basicConsume(queue , false ,  new DefaultConsumer(channel){
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

    public static void setQueue(String queue) {
        AmqpService.queue = queue;
    }

    public static void setRountingKey(String rountingKey) {
        AmqpService.rountingKey = rountingKey;
    }

    public static void setIsAutoDete(boolean isAutoDete) {
        AmqpService.isAutoDete = isAutoDete;
    }

    public static void setIsExclusive(boolean isExclusive) {
        AmqpService.isExclusive = isExclusive;
    }

    public static void setArgs(Map<String, Object> args) {
        AmqpService.args = args;
    }
}
