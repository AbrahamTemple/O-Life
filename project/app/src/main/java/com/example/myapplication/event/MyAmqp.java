package com.example.myapplication.event;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MyAmqp {

    private ConnectionFactory factory;
    private String userName = "admin" ;
    private String passWord = "admin" ;
    private String hostName = "127.0.0.1" ;
    private String virtualHost = "/";
    private int portNum = 5672 ;
    private String exchangeName = "android_exchange" ;
    private String rountingKey = "news";

    public void init(){
        factory = new ConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(passWord);
        factory.setHost(hostName);
        factory.setPort(portNum);
        factory.setVirtualHost(virtualHost);
    }


    private void basicConsume(){

        try {
            Connection connection = factory.newConnection() ;
            final Channel channel = connection.createChannel() ;
            channel.exchangeDeclare(exchangeName , "direct" , true) ;
            String queueName = "android_queue";
            System.out.println(queueName);
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
                    System.out.println(msg);
                    EventBus.getDefault().post(msg);
//                    Message uiMessage = handler.obtainMessage();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("msg", msg);
//                    uiMessage.setData(bundle);
//                    handler.sendMessage(uiMessage);

                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

//    private void basicPublish(){
//
//        try {
//            Connection connection = factory.newConnection() ;
//            Channel channel = connection.createChannel() ;
//            byte[] msg = "hello girl friend!".getBytes() ;//消息发布
//            channel.basicPublish(exchangeName , rountingKey  ,null , msg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void basicConsume(){
//
//        try {
//            Connection connection = factory.newConnection() ;
//            final Channel channel = connection.createChannel() ;
//            //声明了一个交换和一个服务器命名的队列，然后将它们绑定在一起。
//            channel.exchangeDeclare(exchangeName , "fanout" , true) ;
//            String queueName = channel.queueDeclare().getQueue() ;
//            Log.e("TAG" , queueName + " :queueName") ;
//            channel.queueBind(queueName , exchangeName , rountingKey) ;
//            //实现Consumer的最简单方法是将便捷类DefaultConsumer子类化。可以在basicConsume 调用上传递此子类的对象以设置订阅：
//            channel.basicConsume(queueName , false , "administrator" , new DefaultConsumer(channel){
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    super.handleDelivery(consumerTag, envelope, properties, body);
//
//                    String rountingKey = envelope.getRoutingKey() ;//路由密钥
//                    String contentType = properties.getContentType() ;//contentType字段，如果尚未设置字段，则为null。
//                    String msg = new String(body,"UTF-8") ;//接收到的消息
//                    long deliveryTag = envelope.getDeliveryTag() ;//交付标记
//
//                    Log.e("TAG" , rountingKey+"：rountingKey") ;
//                    Log.e("TAG" , contentType+"：contentType") ;
//                    Log.e("TAG" , msg+"：msg") ;
//                    Log.e("TAG" , deliveryTag+"：deliveryTag") ;
//                    Log.e("TAG" , consumerTag+"：consumerTag") ;
//                    Log.e("TAG" , envelope.getExchange()+"：exchangeName") ;
//
//                    channel.basicAck(deliveryTag , false);
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }
//
//    }

}
