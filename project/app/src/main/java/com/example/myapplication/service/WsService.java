package com.example.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.example.myapplication.util.GsonUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.Objects;


public class WsService extends IntentService {

    private final String TAG = this.getClass().getSimpleName();

    private static final String ACTION_CONNECTION = "com.example.myapplication.service.action.CONNECTION";
    private static final String ACTION_SEND = "com.example.myapplication.service.action.SEND";
    private static final String ACTION_RECONNECTION = "com.example.myapplication.service.action.RECONNECTION";
    private static final String ACTION_CLOSE = "com.example.myapplication.service.action.CLOSE";

    private static final String EXTRA_BODY = "com.example.myapplication.service.extra.BODY";

    private WebSocketClient webSocketClient;
    private static String host = "172.20.10.12:8083"; //一定不可以是localhost
    private static String userId = "-1";
    private static String uri = "ws://"+host+"/websocket/"+userId;

    public WsService() {
        super("WsService");
    }

    public static void startConnection(Context context,String userId) {
        Intent intent = new Intent(context, WsService.class);
        intent.setAction(ACTION_CONNECTION);
        setUserId(userId);
        context.startService(intent);
    }

    public static void startSend(Context context,Map<String, Object> map) {
        String body = GsonUtils.toJson(map);
        Intent intent = new Intent(context, WsService.class);
        intent.setAction(ACTION_CONNECTION);
        intent.putExtra(EXTRA_BODY, body);
        context.startService(intent);
    }

    public static void startReConnection(Context context) {
        Intent intent = new Intent(context, WsService.class);
        intent.setAction(ACTION_RECONNECTION);
        context.startService(intent);
    }

    public static void startClose(Context context) {
        Intent intent = new Intent(context, WsService.class);
        intent.setAction(ACTION_CLOSE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            switch (Objects.requireNonNull(intent.getAction())){
                case ACTION_CONNECTION: connectionChannel();
                    break;
                case ACTION_SEND:
                    String body = intent.getStringExtra(EXTRA_BODY);
                    send(body);
                    break;
                case ACTION_RECONNECTION: reconnetionChannel();
                    break;
                case ACTION_CLOSE: closeChannel();
                    break;
                default: Log.d(TAG, "ACTION NOT FOUND");
            }
        }
    }

    /**
     * 连接通道，借助http协议升级成websocket协议
     * 通过onMessage回调函数接收当前通道的消息
     */
    private void connectionChannel(){
        URI serverURI = URI.create(uri);
        webSocketClient = new WebSocketClient(serverURI) {
            @Override
            public void onOpen(ServerHandshake handShake) {
                Log.d(TAG, "连接成功");
                Log.d(TAG,"连接状态："+handShake.getHttpStatusMessage());
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, "收到消息");
                Log.d(TAG,"消息内容为："+message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d(TAG, "连接关闭");
                Log.d(TAG,reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.d(TAG, "连接发生错误");
                Log.d(TAG,"原因是："+ex);
            }
        };
        webSocketClient.connect();
    }

    /**
     * 如果是群发则是{persons:null,msg:"message"}
     * 如果是定点则是{persons:[user1,user2],msg:"message"}
     * @param body 对应MsgDTO的json字符串
     */
    private void send(String body){
        webSocketClient.send(body);
        Log.d(TAG, "消息已发送到服务器");
    }

    /**
     * 重新连接
     */
    private void reconnetionChannel(){
        webSocketClient.reconnect();
    }

    /**
     * 关闭连接
     */
    private void closeChannel(){
        if(webSocketClient.isOpen() && !webSocketClient.isClosing()) {
            webSocketClient.close();
            Log.d(TAG, "已经成功离线");
        }
    }

    public static void setHost(String host) {
        WsService.host = host;
    }

    public static void setUserId(String userId) {
        WsService.userId = userId;
    }
}
