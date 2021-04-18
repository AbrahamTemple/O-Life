package com.example.sixshot.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.timonknispel.ktloadingbutton.KTLoadingButton;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

//    @BindView(R.id.test_button)
//    KTLoadingButton test_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpInit();
        init();
    }

    public void HttpInit(){
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public void init(){
//        test_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.test_button:
//                test_button.startLoading();

//                test_button.doResult(true,);
                //test_button.doResult(true,);
//                doSomething(v);
//                test_button.reset();
//                break;
            default:
                break;
        }
    }

    public void doSomething(View view) {
        Intent intent = new Intent(MainActivity.this,SearchActivity.class);
        startActivity(intent);
    }
}
