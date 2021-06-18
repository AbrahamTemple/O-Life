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
import com.example.myapplication.domain.Order;
import com.example.myapplication.domain.Reserver;
import com.example.myapplication.router.RoutePath;
import com.example.myapplication.view.adapter.OrderAdpater;
import com.example.myapplication.view.layout.SpruceRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends Fragment {

    private RecyclerView orders;

    private List<Reserver> data;

    public OrderListFragment(List<Reserver> data) {
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

    private void initRecycler(List<Reserver> data){
        List<Order> lists = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.forEach( d->{
            Order or = new Order(d.getTitle(),sdf.format(d.getTime()),d.getInfo(),d.getAddress(),d.getState(),d.getServer());
            lists.add(or);
        });
        OrderAdpater orderAdapter = new OrderAdpater(lists);
        orderAdapter.setOnItemClickListener((order, position) -> {
            ARouter.getInstance().build(RoutePath.DETAIL.toString())
                    .withString("title",order.getTitle())
                    .withString("time",order.getTime())
                    .withString("address",order.getAddress())
                    .withString("info",order.getInfo())
                    .withString("state",order.getState())
                    .withString("server",order.getServer())
                    .withInt("action",data.get(0).getAction())
                    .navigation();
        });
        new SpruceRecyclerView(getContext(), orders, orderAdapter, true).init();
    }
}
