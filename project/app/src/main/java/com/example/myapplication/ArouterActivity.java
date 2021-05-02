package com.example.myapplication;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.databinding.ActivityArouterBinding;
import com.example.myapplication.entity.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.util.Log;

@Route(path = "/test/activity")
public class ArouterActivity extends AppCompatActivity {

    @Autowired
    public String name;

    @Autowired
    public Long codeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
        init();
        Log.d("ARouter", name+codeName);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    public void init(){
        ARouter.getInstance().inject(this);
        //绑定配置
        ActivityArouterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_arouter);
        binding.setUsers(new Users(name,codeName));
    }

}