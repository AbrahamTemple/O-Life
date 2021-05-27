package com.example.myapplication.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.myapplication.view.layout.LoginLoadingView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.example.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/olife/login")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.frt_content)
    FrameLayout mFrtContent;

    Scene mSceneSignUp;

    Scene mSceneLogging;

    Scene mSceneHome;

    private int mTvSighUpWidth, mTvSighUpHeight;
    private int mDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mDuration = getResources().getInteger(R.integer.duration);

        mSceneSignUp = Scene.getSceneForLayout(mFrtContent, R.layout.scene_sign_up, this);
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
            loginView.postDelayed(() -> loginView.setStatus(LoginLoadingView.STATUS_LOGIN_SUCCESS), 4000);

            loginView.postDelayed(() -> TransitionManager.go(mSceneHome, new ChangeBounds().setDuration(mDuration).setInterpolator(new DecelerateInterpolator())), 6000);
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
}
