package com.example.myapplication.view.fragment.compose;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.view.model.LocalImageHolderView;
import com.skydoves.elasticviews.ElasticAnimation;
import com.skydoves.elasticviews.ElasticImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class HomeFragment extends Fragment implements View.OnClickListener, OnItemClickListener {

    private AppCompatActivity activity;

    private ConvenientBanner banner;

    private ElasticImageView staff_logo,callout_logo,register_logo,healthy_chat_logo;
    private LinearLayout staff_index,callout_index;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    public HomeFragment(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initOnClick();
        initBanner();
    }

    private void init(View view){
        banner = view.findViewById(R.id.convenientBanner);
        staff_logo = view.findViewById(R.id.staff_logo);
        callout_logo = view.findViewById(R.id.callout_logo);
        register_logo = view.findViewById(R.id.register_logo);
        healthy_chat_logo = view.findViewById(R.id.healthy_chat_logo);
        staff_index = view.findViewById(R.id.staff_index);
        callout_index = view.findViewById(R.id.callout_index);
    }

    private void initOnClick(){
        staff_logo.setOnClickListener(this);
        callout_logo.setOnClickListener(this);
        register_logo.setOnClickListener(this);
        healthy_chat_logo.setOnClickListener(this);
        staff_index.setOnClickListener(this);
        callout_index.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.staff_index:
            case R.id.staff_logo:
                new ElasticAnimation(staff_logo).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                        .setOnFinishListener(() -> ARouter.getInstance().build("/olife/escort").navigation()).doAction();
                break;
            case R.id.callout_index:
            case R.id.callout_logo:
                new ElasticAnimation(callout_logo).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                        .setOnFinishListener(() -> ARouter.getInstance().build("/olife/call")
                                .withString("tag", UUID.randomUUID().toString())
                                .navigation()).doAction();
                break;
            case R.id.register_index:
            case R.id.register_logo:
                new ElasticAnimation(register_logo).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                        .setOnFinishListener(() -> Log.d("register","挂号链接")).doAction();
                break;
            case R.id.healthy_chat_index:
            case R.id.healthy_chat_logo:
                new ElasticAnimation(healthy_chat_logo).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                        .setOnFinishListener(() -> ARouter.getInstance().build("/olife/chat").navigation()).doAction();
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.in_frag_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initBanner(){
        loadTestDatas();
        //本地图片例子
        banner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public Holder createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_localimage;
                    }
                }, localImages)
                .setPageIndicator(new int[]{R.drawable.point_unfocused_small, R.drawable.point_focused_big})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this);
    }

    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 4; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    private static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(),"点击了第"+position+"个", Toast.LENGTH_SHORT).show();
        ARouter.getInstance().build("/olife/login").navigation();
    }

    public ConvenientBanner getBanner() {
        return banner;
    }
}
