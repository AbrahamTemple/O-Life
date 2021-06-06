package com.example.myapplication.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.royrodriguez.transitionbutton.TransitionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/olife/register")
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_btn)
    TransitionButton btn;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_btn)
    public void RouteList(){
        btn.startAnimation();
        // 在动画执行过程中要做一些什么
        handler = new Handler();
        handler.postDelayed(() -> {

            boolean isSuccessful = true;

            if (isSuccessful) {
                btn.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, () -> {
                    Uri uri = Uri.parse("/olife/list");
                    ARouter.getInstance().build(uri)
                            .withInt("action", 1)
                            .withLong("id",-1)
                            .navigation();
                    finish();
                });
            } else {
                btn.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
            }
        }, 2000);
    }

}
