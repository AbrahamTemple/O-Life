package com.example.myapplication.event;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * 监听电话拨打状态
 */
public class MyPhoneStateListener extends PhoneStateListener {

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        switch(state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i("info","空闲状态");
                break;

            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i("info","通话挂起");
                EventBus.getDefault().post("通话中...");
                break;

            case TelephonyManager.CALL_STATE_RINGING:
                Log.i("info","成功拨通");
                EventBus.getDefault().post("已通话");
                break;
            default: Log.i("info","ERROR");
        }
    }
}
