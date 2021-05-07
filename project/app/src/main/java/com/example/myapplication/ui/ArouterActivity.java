package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityArouterBinding;
import com.example.myapplication.domain.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

@Route(path = "/test/activity")
public class ArouterActivity extends AppCompatActivity {

    @Autowired
    public String name;

    @Autowired
    public int codeName;

    @Autowired
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
        init();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    public void init(){
        ARouter.getInstance().inject(this);
        //绑定数据
        ActivityArouterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_arouter);
        binding.setUsers(new Users(name,codeName,url));
    }

}
