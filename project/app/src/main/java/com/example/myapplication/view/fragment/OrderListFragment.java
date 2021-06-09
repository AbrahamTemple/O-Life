package com.example.myapplication.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.data.model.ReserverResponse;
import com.example.myapplication.domain.Intro;
import com.example.myapplication.domain.Order;
import com.example.myapplication.router.RoutePath;
import com.example.myapplication.view.adapter.OrderAdpater;
import com.example.myapplication.view.layout.SpruceRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends Fragment {

    private ReserverResponse data;
    private RecyclerView orders;

    public OrderListFragment(ReserverResponse data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orders = view.findViewById(R.id.order_list);
        initRecycler(data);
    }

    private void initRecycler(ReserverResponse hospitalResponse){
        List<Order> lists = new ArrayList<>();
        hospitalResponse.getData().forEach(d->{
            Order r = new Order(d.getTitle(),d.getTime(),d.getAddress(),d.getState(),"护工："+d.getServer());
            lists.add(r);
        });
        OrderAdpater orderAdapter = new OrderAdpater(lists);
        orderAdapter.setOnItemClickListener((order, position) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ARouter.getInstance().build(RoutePath.DETAIL.toString())
                    .withString("title",order.getTitle())
                    .withString("time",sdf.format(order.getTime()))
                    .withString("address",order.getAddress())
                    .withString("state",order.getState())
                    .withString("server",order.getServer()).navigation();
        });
        new SpruceRecyclerView(getContext(), orders, orderAdapter, true).init();
    }
}
