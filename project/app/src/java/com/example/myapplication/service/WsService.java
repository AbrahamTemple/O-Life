package com.example.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.example.myapplication.domain.MsgDto;
import com.example.myapplication.util.GsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Objects;


public class WsService extends IntentService {

    private final String TAG = this.getClass().getSimpleName();

    private static final String ACTION_CONNECTION = "com.example.myapplication.service.action.CONNECTION";
    private static final String ACTION_SEND = "com.example.myapplication.service.action.SEND";
    private static final String ACTION_RECONNECTION = "com.example.myapplication.service.action.RECONNECTION";
    private static final String ACTION_CLOSE = "com.example.myapplication.service.action.CLOSE";

    private static final String EXTRA_BODY = "com.example.myapplication.service.extra.BODY";
    private static final String EXTRA_UID = "com.example.myapplication.service.extra.UID";

    private static volatile WebSocketClient webSocketClient;
    private static String host = "121.37.178.107:8083"; //一定不可以是localhost
    private static String uri = "ws://"+host+"/websocket/";
    private static String localMessage = "";

    public WsService() {
        super("WsService");
    }

    public static void startConnection(Context context,String userId) {
        Intent intent = new Intent(context, WsService.class);
        intent.setAction(ACTION_CONNECTION);
        intent.putExtra(EXTRA_UID,userId);
        context.startService(intent);
    }


    public static void startSend(Context context, MsgDto body) {
        Intent intent = new Intent(context, WsService.class);
        intent.setAction(ACTION_SEND);
        intent.putExtra(EXTRA_BODY, GsonUtils.toJson(body));
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
                case ACTION_CONNECTION:
                    if (intent.getStringExtra(EXTRA_UID)!=null){
                        String uid = intent.getStringExtra(EXTRA_UID);
                        uri += uid;
                        connectionChannel(uri);
                    }
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
    private void connectionChannel(String uri){
        System.out.println(uri);
        URI serverURI = URI.create(uri);
        webSocketClient = new WebSocketClient(serverURI) {
            @Override
            public void onOpen(ServerHandshake handShake) {
                Log.i(TAG, "连接成功："+uri);
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG,"收到服务器消息："+message);
                if (!message.equals(localMessage)) {
                    EventBus.getDefault().post(new MsgDto(null, message, true));
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e(TAG, "连接已关闭");
            }

            @Override
            public void onError(Exception ex) {
                Log.e(TAG,"连接发生错误："+ex);
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
        if (webSocketClient!=null && webSocketClient.isOpen()){
            webSocketClient.send(body);
            MsgDto dto = GsonUtils.fromJson(body, MsgDto.class);
            EventBus.getDefault().post(new MsgDto(dto.getPersons(),dto.getMsg(),dto.getIsm()));
            localMessage = dto.getMsg();
        }
        Log.i(TAG, "消息已发送到服务器");
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

    public static void setUri(String uri) {
        WsService.uri = uri;
    }
}
