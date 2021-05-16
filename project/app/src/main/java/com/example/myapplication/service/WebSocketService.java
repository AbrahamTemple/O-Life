package com.example.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.app.MyApplication;
import com.example.myapplication.vo.WsStatus;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class WebSocketService extends IntentService {

    private final String TAG = this.getClass().getSimpleName();

    /**
     * Service Action
     */

    private static final String ACTION_CONNECTION = "com.example.myapplication.service.action.CONNECTION";
    private static final String ACTION_RECONNECTION = "com.example.myapplication.service.action.RECONNECTION";

    /**
     * WebSocket Init
     */
    private static final int FRAME_QUEUE_SIZE = 5;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final String DEF_TEST_URL = "ws:localhost:8083/websocket/1";//测试服默认地址
    private static final String DEF_RELEASE_URL = "ws:localhost:8083/websocket/1";//正式服默认地址
    private static final String DEF_URL = BuildConfig.DEBUG ? DEF_TEST_URL : DEF_RELEASE_URL;
    private String url;
    private WsStatus status; //连接状态
    private WebSocket ws; //协议
    private WsListener listener; //监听
    private int reconnectCount = 0;//重连次数
    private long minInterval = 3000;//重连最小时间间隔
    private long maxInterval = 60000;//重连最大时间间隔

    public WebSocketService() {
        super("WebSocketService");
    }

    /**
     * 连接
     * @param context 上下文
     */
    public static void startWsConection(Context context) {
        Intent intent = new Intent(context, WebSocketService.class);
        intent.setAction(ACTION_CONNECTION);
        context.startService(intent);
    }

    /**
     * 重连
     * @param context 上下文
     */
    public static void startWsReConection(Context context) {
        Intent intent = new Intent(context, WebSocketService.class);
        intent.setAction(ACTION_RECONNECTION);
        context.startService(intent);
    }

    /**
     * 连接或重连
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            switch (Objects.requireNonNull(intent.getAction())){
                case ACTION_CONNECTION:
                    try {
                        /**
                         * configUrl其实是缓存在本地的连接地址
                         * 这个缓存本地连接地址是app启动的时候通过http请求去服务端获取的,
                         * 每次app启动的时候会拿当前时间与缓存时间比较,超过6小时就再次去服务端获取新的连接地址更新本地缓存
                         */
                        String configUrl = "";
                        url = TextUtils.isEmpty(configUrl) ? DEF_URL : configUrl;
                        ws = new WebSocketFactory().createSocket(url, CONNECT_TIMEOUT)
                                .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
                                .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
                                .addListener(listener = new WsListener())//添加回调监听
                                .connectAsynchronously();//异步连接
                        setStatus(WsStatus.CONNECTING);
                        Log.d(TAG, "第一次连接");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ACTION_RECONNECTION:
//                    if (!isNetConnect()) {
//                        reconnectCount = 0;
//                        Log.d(TAG,"重连失败网络不可用");
//                        return;
//                    }

                    //这里缺个登录判断
                    if (ws != null &&
                            !ws.isOpen() &&//当前连接断开了
                            getStatus() != WsStatus.CONNECTING) {//不是正在重连状态

                        reconnectCount++;
                        setStatus(WsStatus.CONNECTING);

                        long reconnectTime = minInterval;
                        if (reconnectCount > 3) {
                            url = DEF_URL;
                            long temp = minInterval * (reconnectCount - 2);
                            reconnectTime = temp > maxInterval ? maxInterval : temp;
                        }

                        Log.d(TAG,"准备开始第"+reconnectCount+"次重连,重连间隔"+reconnectTime+" -- url:"+url);
                        try {
                            ws = new WebSocketFactory().createSocket(url, CONNECT_TIMEOUT)
                                    .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
                                    .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
                                    .addListener(listener = new WsListener())//添加回调监听
                                    .connectAsynchronously();//异步连接
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    disconnect();
            }
        }
    }

    /**
     * 继承默认的监听空实现WebSocketAdapter,重写我们需要的方法
     * onTextMessage 收到文字信息
     * onConnected 连接成功
     * onConnectError 连接失败
     * onDisconnected 连接关闭
     */
    class WsListener extends WebSocketAdapter {
        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            super.onTextMessage(websocket, text);
            Log.d(TAG,text);
        }


        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers)
                throws Exception {
            super.onConnected(websocket, headers);
            Log.d(TAG,"连接成功");
            setStatus(WsStatus.CONNECT_SUCCESS);
            cancelReconnect();
        }


        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception)
                throws Exception {
            super.onConnectError(websocket, exception);
            Log.d(TAG,"连接错误");
            setStatus(WsStatus.CONNECT_FAIL);
        }


        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer)
                throws Exception {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
            Log.d(TAG,"断开连接");
            setStatus(WsStatus.CONNECT_FAIL);
        }
    }

    public void disconnect(){
        if(ws != null)
            ws.disconnect();
    }

    private void cancelReconnect() {
        reconnectCount = 0;
    }

    public WsStatus getStatus() {
        return status;
    }

    public void setStatus(WsStatus status) {
        this.status = status;
    }

//    public boolean isNetConnect() {
//        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getContext()
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity != null) {
//            NetworkInfo info = connectivity.getActiveNetworkInfo();
//            if (info != null && info.isConnected()) {
//                // 当前网络是连接的
//                if (info.getState() == NetworkInfo.State.CONNECTED) {
//                    // 当前所连接的网络可用
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
