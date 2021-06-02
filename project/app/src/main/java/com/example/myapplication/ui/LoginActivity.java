package com.example.myapplication.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapplication.domain.Counter;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.view.layout.LoginLoadingView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/olife/login")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.frt_content)
    FrameLayout mFrtContent;

    Scene mSceneSignUp;

    Scene mSceneLogging;

    Scene mSceneHome;

    private int mTvSighUpWidth, mTvSighUpHeight;
    private int mDuration;

    private TextView nameEdit,passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        HideUtil.init(this);
        initLogin();
    }

    public void initLogin(){
        mDuration = getResources().getInteger(R.integer.duration);

//        View form = LayoutInflater.from(this).inflate(R.layout.scene_sign_up, null);
//        nameEdit = form.findViewById(R.id.log_name);
//        passEdit = form.findViewById(R.id.log_pass_same);

        mSceneSignUp = Scene.getSceneForLayout(mFrtContent, R.layout.scene_sign_up, this);
        initEditListener();
//        mSceneSignUp.enter();
//        nameEdit = (EditText) mSceneSignUp.getSceneRoot().findViewById(R.id.log_name);
//        passEdit = (EditText) mSceneSignUp.getSceneRoot().findViewById(R.id.log_pass_same);
        mSceneSignUp.setEnterAction(() -> {
            final LoginLoadingView loginView = (LoginLoadingView) mFrtContent.findViewById(R.id.login_view);
            ViewTreeObserver vto = loginView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(() -> setSize(loginView.getMeasuredWidth(), loginView.getMeasuredHeight()));
            loginView.setOnClickListener(LoginActivity.this);
        });

        mSceneLogging = Scene.getSceneForLayout(mFrtContent, R.layout.scene_logging, this);
        mSceneLogging.setEnterAction(() -> {
            final LoginLoadingView loginView = (LoginLoadingView) mFrtContent.findViewById(R.id.login_view);
            loginView.postDelayed(() -> loginView.setStatus(LoginLoadingView.STATUS_LOGGING), mDuration);
//            EventBus.getDefault().post(loginView);
            System.out.println(nameEdit.getText().toString() +" "+ passEdit.getText().toString());
            if (nameEdit.getText().toString().equals("AbrahamVong") && passEdit.getText().toString().equals("123456")) {
                loginView.postDelayed(() -> loginView.setStatus(LoginLoadingView.STATUS_LOGIN_SUCCESS), 4000);
                loginView.postDelayed(() -> TransitionManager.go(mSceneHome, new ChangeBounds().setDuration(mDuration).setInterpolator(new DecelerateInterpolator())), 6000);
            } else {
                loginView.postDelayed(() -> loginView.setStatus(LoginLoadingView.STATUS_LOGIN_FAIL), 4000);
                loginView.postDelayed(() -> TransitionManager.go(mSceneSignUp, new ChangeBounds().setDuration(mDuration).setInterpolator(new DecelerateInterpolator())), 6000);
            }
        });

        mSceneHome = Scene.getSceneForLayout(mFrtContent, R.layout.activity_home, this);
        mSceneHome.setEnterAction(() -> {
            ValueAnimator animator = ValueAnimator.ofInt(0, 255);
            animator.setDuration(mDuration);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(animation -> System.out.println(animation.getAnimatedValue()));
            animator.start();
            finish();
        });

        TransitionManager.go(mSceneSignUp);
    }

    public void initEditListener(){
        mSceneSignUp.enter();
        nameEdit = (EditText) mSceneSignUp.getSceneRoot().findViewById(R.id.log_name);
        passEdit = (EditText) mSceneSignUp.getSceneRoot().findViewById(R.id.log_pass_same);
//        nameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Log.e("点击ET_phone", "没有响应");
//                nameEdit.setText(v.getText());
//                return false;
//            }
//        });

//        nameEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                String delText = s.toString().substring(start, start + count);
//                Log.i("beforeTextChanged: ",delText);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String addText = s.toString().substring(start, start + count);
//                Log.i("onTextChanged: ",addText);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.i("afterTextChanged: ",s.toString());
//                nameEdit.setText(s.toString());
//            }
//        });
//        passEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                passEdit.setText(s.toString());
//            }
//        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEventProgress(LoginLoadingView loginView) {
        mSceneSignUp.exit();
    }

    private void setSize(int width, int height) {
        mTvSighUpWidth = width;
        mTvSighUpHeight = height;
    }

    @Override
    public void onClick(final View v) {
        float finalRadius = (float) Math.hypot(mFrtContent.getWidth(), mFrtContent.getHeight());

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        Animator anim = ViewAnimationUtils.createCircularReveal(mFrtContent, x + mTvSighUpWidth / 2, y - mTvSighUpHeight / 2, 100, finalRadius);
        mFrtContent.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorBg));
        anim.setDuration(mDuration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFrtContent.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        anim.start();

        TransitionManager.go(mSceneLogging, new ChangeBounds().setDuration(mDuration).setInterpolator(new DecelerateInterpolator()));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mSceneSignUp.exit();
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        System.out.println("before:"+s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        System.out.println("onchange:"+s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        System.out.println("after:"+s.toString());
    }
}
