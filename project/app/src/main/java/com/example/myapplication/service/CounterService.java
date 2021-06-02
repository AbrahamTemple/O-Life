package com.example.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.myapplication.domain.Counter;

import org.greenrobot.eventbus.EventBus;

public class CounterService extends IntentService {

    private static final String ACTION_COUNTER = "com.example.myapplication.service.action.COUNTER";

    private static final String EXTRA_SEC = "com.example.myapplication.service.extra.SECOND";

    private static final String EXTRA_TAG = "com.example.myapplication.service.extra.TAG";

    private static final String EXTRA_DATA = "com.example.myapplication.service.extra.DATA";

    private static final int SLEEP_TIME = 1;

    public CounterService() {
        super("CounterService");
    }

    public static void startDownload(Context context, int second,int tag) {
        Intent intent = new Intent(context, CounterService.class);
        intent.setAction(ACTION_COUNTER);
        intent.putExtra(EXTRA_SEC, second);
        intent.putExtra(EXTRA_TAG,tag);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_COUNTER.equals(action)) {
                final int second = intent.getIntExtra(EXTRA_SEC, 0);
                final int tag = intent.getIntExtra(EXTRA_TAG,0);
                handleActionFoo(second,tag);
            }
        }
    }

    private void handleActionFoo(int sec,int tag) {
        int millis = sec * 1000;
        Counter counter = new Counter(0,tag);

        for (int i = millis;i >= 0; i-=SLEEP_TIME){
            counter.setProgress(i);
            EventBus.getDefault().post(counter);
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
