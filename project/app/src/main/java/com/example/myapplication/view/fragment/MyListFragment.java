package com.example.myapplication.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.model.HospitalResponse;
import com.example.myapplication.data.network.block.Contract;
import com.example.myapplication.util.GsonUtils;
import com.example.myapplication.view.adapter.RecommendAdapter;
import com.example.myapplication.view.items.HomeRecommend;
import com.example.myapplication.view.layout.SpruceRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;

public class MyListFragment extends Fragment{

    private RecyclerView recommend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recommend = view.findViewById(R.id.home_recommend);
        System.out.println("列表："+recommend);
        return view;
    }

    public void initRecycler(HospitalResponse hospitalResponse){
        System.out.println("配置列表："+recommend);
        List<HomeRecommend> lists = new ArrayList<>();
        hospitalResponse.getData().forEach(d->{
            HomeRecommend r = new HomeRecommend(R.drawable.ramain_register,d.getName(),d.getAddress(),"联系电话："+d.getPhone());
            lists.add(r);
        });
        RecommendAdapter recommendAdapter = new RecommendAdapter(lists);
        new SpruceRecyclerView(getContext(), recommend, recommendAdapter, false).init();
    }
}
