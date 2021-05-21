package com.example.myapplication.ui;

import android.os.Bundle;
import android.view.View;

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

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/test/activity")
public class ArouterActivity extends AppCompatActivity implements View.OnClickListener{

    @Autowired
    public String name;

    @Autowired
    public int codeName;

    @Autowired
    public String url;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
        ButterKnife.bind(this);
        init();
    }

    public void init(){
        ARouter.getInstance().inject(this);
        //绑定数据
        ActivityArouterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_arouter);
        binding.setUsers(new Users(name,codeName,url));
        fab.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
        }
    }


}
