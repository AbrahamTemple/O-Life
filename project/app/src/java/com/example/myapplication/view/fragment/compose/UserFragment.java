package com.example.myapplication.view.fragment.compose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;

public class UserFragment extends Fragment {

    private RelativeLayout loginModel;
    private AppCompatActivity activity;

    public UserFragment(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginModel = view.findViewById(R.id.user_login);
        loginModel.setOnClickListener(v -> ARouter.getInstance().build("/olife/login").navigation());
//        ViewDataBinding binding = DataBindingUtil.setContentView(activity, R.layout.fragment_user);
//        binding.setVariable(BR.user,new UserInfo("登录验证",1,8,2,32));
    }
}
