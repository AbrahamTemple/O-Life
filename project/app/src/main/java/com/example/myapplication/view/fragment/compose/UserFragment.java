package com.example.myapplication.view.fragment.compose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.data.model.UserResponse;
import com.example.myapplication.router.LoginCallbackImpl;
import com.example.myapplication.router.RoutePath;
import com.skydoves.elasticviews.ElasticAnimation;
import com.skydoves.elasticviews.ElasticImageView;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.util.UUID;

public class UserFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout loginModel;
    private AppCompatActivity activity;

    private RadiusImageView userPs;
    private TextView userName;

    private String name;

    private LinearLayout mod1,mod2,mod3,mod4;
    private ElasticImageView eiv1,eiv2,eiv3,eiv4;

    public UserFragment(AppCompatActivity activity, String name) {
        this.activity = activity;
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initOnClick();
        loginModel.setOnClickListener(v -> ARouter.getInstance().build(RoutePath.LOGIN.toString()).navigation());

        if (name!=null && !name.isEmpty()){
            userPs.setImageResource(R.drawable.abraham_ps);
            userName.setText(name);
        }

//        ViewDataBinding binding = DataBindingUtil.setContentView(activity, R.layout.fragment_user);
//        binding.setVariable(BR.user,new UserInfo("登录验证",1,8,2,32));
    }

    private void init(View v){
        loginModel = v.findViewById(R.id.user_login);
        userPs = v.findViewById(R.id.user_ps);
        userName = v.findViewById(R.id.user_name);
        mod1 = v.findViewById(R.id.escort_req_index);
        eiv1 = v.findViewById(R.id.escort_req_logo);
        mod2 = v.findViewById(R.id.escort_ok_index);
        eiv2 = v.findViewById(R.id.escort_ok_logo);
        mod3 = v.findViewById(R.id.register_req_index);
        eiv3 = v.findViewById(R.id.register_req_logo);
        mod4 = v.findViewById(R.id.register_ok_index);
        eiv4 = v.findViewById(R.id.register_ok_logo);
    }

    private void initOnClick(){
        mod1.setOnClickListener(this);
        eiv1.setOnClickListener(this);
        mod2.setOnClickListener(this);
        eiv2.setOnClickListener(this);
        mod3.setOnClickListener(this);
        eiv3.setOnClickListener(this);
        mod4.setOnClickListener(this);
        eiv4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.escort_req_index:
            case R.id.escort_req_logo:
                new ElasticAnimation(eiv1).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                        .setOnFinishListener(() -> ARouter.getInstance().build(RoutePath.ORDER.toString()).navigation(activity,new LoginCallbackImpl())).doAction();
                break;
            case R.id.escort_ok_index:
            case R.id.escort_ok_logo:
                new ElasticAnimation(eiv2).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                        .setOnFinishListener(() -> ARouter.getInstance().build(RoutePath.ORDER.toString()).navigation(activity,new LoginCallbackImpl())).doAction();
                break;
            case R.id.register_req_index:
            case R.id.register_req_logo:
                new ElasticAnimation(eiv3).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                        .setOnFinishListener(() -> ARouter.getInstance().build(RoutePath.ORDER.toString()).navigation(activity,new LoginCallbackImpl())).doAction();
                break;
            case R.id.register_ok_index:
            case R.id.register_ok_logo:
                new ElasticAnimation(eiv4).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                        .setOnFinishListener(() -> ARouter.getInstance().build(RoutePath.ORDER.toString()).navigation(activity,new LoginCallbackImpl())).doAction();
                break;
        }
    }
}
