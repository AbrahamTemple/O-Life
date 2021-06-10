package com.example.myapplication.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.data.model.LoginResponse;
import com.example.myapplication.data.model.UserResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.data.network.block.Model;
import com.example.myapplication.data.network.block.Presenter;
import com.example.myapplication.data.network.scheduler.SchedulerProvider;
import com.example.myapplication.router.RoutePath;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.layout.LoginLoadingView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

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
import android.widget.Toast;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

@Route(path = "/olife/login")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, Contract.View{

    @BindView(R.id.frt_content)
    FrameLayout mFrtContent;

    Scene mSceneSignUp;

    Scene mSceneLogging;

    Scene mSceneHome;

    private int mTvSighUpWidth, mTvSighUpHeight;
    private int mDuration;

    private TextView nameEdit,passEdit,passEdit1;

    private SharedPreferencesUtils sharedPreferences,tokenShared;
    private Presenter presenter;

    private View anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        HideUtil.init(this);
        initLogin();
        initResquest();
    }

    public void initLogin(){
        mDuration = getResources().getInteger(R.integer.duration);
        initSceneSignUp();
        initSceneHome();
        TransitionManager.go(mSceneSignUp);
    }

    private void initResquest(){
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
        sharedPreferences = SharedPreferencesUtils.init(LoginActivity.this);
        tokenShared = SharedPreferencesUtils.init(this,"oauth");
    }

    @Override
    public void onClick(final View v) {
        nameEdit = (EditText) mSceneSignUp.getSceneRoot().findViewById(R.id.log_name);
        passEdit1 = (EditText) mSceneSignUp.getSceneRoot().findViewById(R.id.log_pass);
        passEdit = (EditText) mSceneSignUp.getSceneRoot().findViewById(R.id.log_pass_same);
        System.out.println(nameEdit.getText().toString() +" "+ passEdit.getText().toString());
        presenter.loginAuth(nameEdit.getText().toString(),passEdit.getText().toString());
        anim = v;
    }

    @Override
    public void getDataSuccess(ResponseBody body) {
        try {
            String result = body.string();
            Log.e("网络请求", "响应结果: " + result);
            if (result.length()<200) {
                LoginResponse data = GsonUtils.fromJson(result, LoginResponse.class);
                initSceneLogging(data); //进行登录验证并附带动画
                startSignAnim(anim); //启动加载动画
                tokenShared.putString("token", data.getData().getAccessToken());
                presenter.getLoginUser(data.getData().getAccessToken());
            } else {
                UserResponse data = GsonUtils.fromJson(result, UserResponse.class);
                tokenShared.putLong("id",data.getData().getId());
                tokenShared.putString("username",data.getData().getUsername());
                tokenShared.putString("sex",data.getData().getSex());
                tokenShared.putString("address",data.getData().getAddress());
                tokenShared.putLong("phone",data.getData().getPhone());
                tokenShared.putString("identify",data.getData().getIdentify());
                tokenShared.putString("clientId",data.getData().getClientId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataFail(Throwable throwable) {
        Toast.makeText(this,"登陆时发生错误",Toast.LENGTH_SHORT);
    }

    private void initSceneSignUp(){
        mSceneSignUp = Scene.getSceneForLayout(mFrtContent, R.layout.scene_sign_up, this);
        mSceneSignUp.setEnterAction(() -> {
            final LoginLoadingView loginView = (LoginLoadingView) mFrtContent.findViewById(R.id.login_view);
            ViewTreeObserver vto = loginView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(() -> setSize(loginView.getMeasuredWidth(), loginView.getMeasuredHeight()));
            loginView.setOnClickListener(LoginActivity.this);
        });
    }

    private void initSceneLogging(LoginResponse data){
        mSceneLogging = Scene.getSceneForLayout(mFrtContent, R.layout.scene_logging, this);
        mSceneLogging.setEnterAction(() -> {
            final LoginLoadingView loginView = (LoginLoadingView) mFrtContent.findViewById(R.id.login_view);
            loginView.postDelayed(() -> loginView.setStatus(LoginLoadingView.STATUS_LOGGING), mDuration);
            if (data.getStatus() == 200) {
                loginView.postDelayed(() -> loginView.setStatus(LoginLoadingView.STATUS_LOGIN_SUCCESS), 4000);
                loginView.postDelayed(() -> TransitionManager.go(mSceneHome, new ChangeBounds().setDuration(mDuration).setInterpolator(new DecelerateInterpolator())), 6000);
            } else {
                loginView.postDelayed(() -> loginView.setStatus(LoginLoadingView.STATUS_LOGIN_FAIL), 4000);
                loginView.postDelayed(() -> TransitionManager.go(mSceneSignUp, new ChangeBounds().setDuration(mDuration).setInterpolator(new DecelerateInterpolator())), 6000);
            }
        });
        TransitionManager.go(mSceneLogging, new ChangeBounds().setDuration(mDuration).setInterpolator(new DecelerateInterpolator()));
    }

    private void initSceneHome(){
        mSceneHome = Scene.getSceneForLayout(mFrtContent, R.layout.activity_home, this);
        mSceneHome.setEnterAction(() -> {
            ValueAnimator animator = ValueAnimator.ofInt(0, 255);
            animator.setDuration(mDuration);
            animator.setInterpolator(new LinearInterpolator());
//            animator.addUpdateListener(animation -> Log.d("login success",animation.getAnimatedValue().toString()));
            animator.start();
//            ARouter.getInstance().build(RoutePath.HOME.toString()).navigation();
            finish();
        });
    }

    private void startSignAnim(View v){
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
    }

    @Override
    protected void onDestroy() {
        mSceneSignUp.exit();
        super.onDestroy();
    }

    private void setSize(int width, int height) {
        mTvSighUpWidth = width;
        mTvSighUpHeight = height;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
